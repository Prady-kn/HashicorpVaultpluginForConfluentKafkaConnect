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

package com.pradykn.kafkaconnect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.pradykn.kafkaconnect.core.VaultConfigProvider;

import org.apache.kafka.common.config.ConfigData;

/**
 *  Hashicorp Vault plugin for Kakfka connect!
 *
 */
public class App {
	public static void main(String[] args) {

		//Use for testing purpose.

		VaultConfigProvider valprov = new VaultConfigProvider();
		Map<String, String> param = new HashMap<String, String>();
		param.put("roleid", "c33f5084-ec88-433d-a238-d19b8cf498c9");
		param.put("secid", "4798b72b-b3a3-f241-4b02-cc45dfff4240");
		param.put("vaulturl", "http://localhost:8201/v1");
		valprov.configure(param);
		String secPath = "/secrets/data/kg/sqlserver";
		ConfigData resp = valprov.get(secPath);
		resp = valprov.get(secPath);
		HashSet<String> testData = new HashSet<String>();
		testData.add("userid");
		resp = valprov.get(secPath, testData);
		System.out.println("Hello World!");
	}
}
