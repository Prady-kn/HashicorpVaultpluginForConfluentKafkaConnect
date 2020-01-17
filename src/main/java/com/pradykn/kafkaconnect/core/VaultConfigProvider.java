/* 
 *  
 *  Copyright 2020 Prady-kn (https://github.com/prady-kn)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 */

package com.pradykn.kafkaconnect.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.pradykn.kafkaconnect.dto.VaultDataResponse;
import com.pradykn.kafkaconnect.dto.VaultLoginRequest;
import com.pradykn.kafkaconnect.dto.VaultLoginResponse;

import org.apache.kafka.common.config.ConfigData;
import org.apache.kafka.common.config.provider.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import net.jodah.expiringmap.ExpiringMap;

public class VaultConfigProvider implements ConfigProvider {

    private static Logger logger = LoggerFactory.getLogger(VaultConfigProvider.class);

    VaultLoginResponse vaultLoginRes;
    String role_id;
    String secret_id;
    String vault_baseurl;
    String token;
    Calendar token_expiration = Calendar.getInstance();
    ExpiringMap<String, Map<String, String>> cache = ExpiringMap.builder().expiration(5, TimeUnit.MINUTES).build();

    @Override
    public void configure(Map<String, ?> configValues) {

        role_id = (String) configValues.get("roleid");
        secret_id = (String) configValues.get("secid");
        vault_baseurl = (String) configValues.get("vaulturl");
        token_expiration = Calendar.getInstance();
        token_expiration.add(Calendar.HOUR, -1);

        logger.debug("Inst ID: {} Configured Vault provider-Role id: {} Secid: {} Vault url: {}",
                System.identityHashCode(this), role_id, secret_id.substring(0, 5), vault_baseurl);

        try {
            RefreshToken();
        } catch (HttpClientErrorException e) {
            String errResp = e.getResponseBodyAsString();

            logger.error("Error refreshing token. Got http exception. Body:{}", errResp);
        } catch (URISyntaxException e) {
            // e.printStackTrace();
            logger.error(e.toString());
        }
    }

    private void RefreshToken() throws URISyntaxException {

        int timeOffset = 100;
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.SECOND, -timeOffset);
        if (token_expiration.compareTo(currentTime) > 0) {
            logger.trace("Token not yet expired, expiring at {}.", token_expiration.getTime());
            return;
        }

        RestTemplate rest = new RestTemplate();
        String url = vault_baseurl + "/auth/approle/login";
        VaultLoginRequest loginReq = new VaultLoginRequest();
        loginReq.setRoleId(role_id);
        loginReq.setSecretId(secret_id);
        URI uri = new URI(url);
        logger.trace("Token refreshing. Vault url-{}", url);
        ResponseEntity<VaultLoginResponse> loginresp = rest.postForEntity(uri, loginReq, VaultLoginResponse.class);
        int status = loginresp.getStatusCodeValue();
        logger.trace("Token refreshing. Vault url-{} Response code:{}", url, status);
        if (status >= 200 && status < 210) {
            vaultLoginRes = loginresp.getBody();
            token = vaultLoginRes.getAuth().getClient_token();
            token_expiration = Calendar.getInstance();
            token_expiration.add(Calendar.SECOND, (int) vaultLoginRes.getAuth().getLease_duration() - timeOffset);
        }
    }

    VaultDataResponse getVaultSecret(String path) throws Exception {
        RestTemplate restTemp = new RestTemplate();
        String url = vault_baseurl + path;
        URI uri = new URI(url);
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Vault-Token", token);
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        int status = 0;
        ResponseEntity<VaultDataResponse> loginresp = restTemp.exchange(uri, HttpMethod.GET, entity,
                VaultDataResponse.class);
        status = loginresp.getStatusCodeValue();

        logger.trace("Obtaining vault secret Vault url-{}, Response code:{}", url, status);
        if (status >= 200 && status < 210) {
            return loginresp.getBody();
        }
        throw new Exception("Invalid response code :" + status);
    }

    @Override
    public void close() throws IOException {

    }

    private Map<String, String> readVaultData(String path) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        RefreshToken();
        Map<String, String> secrets = cache.get(path);

        if (secrets == null) {
            logger.trace("Didnt found data in cache for key:{} checking in vault.", path);
            VaultDataResponse vaultsecret = getVaultSecret(path);
            secrets = vaultsecret.getData().getData().getAdditionalProperties();
            cache.put(path, secrets);
        } else {
            logger.trace("Found data in cache for key:{}", path);
        }

        for (Map.Entry<String, String> prop : secrets.entrySet()) {
            data.put(prop.getKey(), prop.getValue());
        }

        return data;
    }

    @Override
    public ConfigData get(String path) {
        Map<String, String> data = new HashMap<String, String>();
        if (path == null || path.isEmpty()) {
            return new ConfigData(data);
        }

        try {
            data = readVaultData(path);
            return new ConfigData(data);

        } catch (HttpClientErrorException e) {
            String errResp = e.getResponseBodyAsString();

            logger.error("Error obtaining data from vault, got http exception. Body:{}", errResp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
        }

        return null;
    }

    @Override
    public ConfigData get(String path, Set<String> keys) {

        Map<String, String> data = new HashMap<String, String>();
        if (path == null || path.isEmpty()) {
            return new ConfigData(data);
        }

        try {
            Map<String, String> tempdata = readVaultData(path);
            for (String key : keys) {
                if (tempdata.containsKey(key)) {
                    data.put(key, tempdata.get(key));
                }
            }
            return new ConfigData(data);

        } catch (HttpClientErrorException e) {
            String errResp = e.getResponseBodyAsString();
            logger.error("Error obtaining data from vault, got http exception. Body:{}", errResp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(e.toString());
        }

        return null;
    }

}