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

package com.pradykn.kafkaconnect.dto;
import java.util.ArrayList;

public class VaultLoginResponse {
  private String request_id;
  private String lease_id;
  private boolean renewable;
  private float lease_duration;
  private String data = null;
  private String wrap_info = null;
  ArrayList<Object> warnings = new ArrayList<Object>();
  Auth AuthObject;

  // Getter Methods

  public String getRequest_id() {
    return request_id;
  }

  public String getLease_id() {
    return lease_id;
  }

  public boolean getRenewable() {
    return renewable;
  }

  public float getLease_duration() {
    return lease_duration;
  }

  public String getData() {
    return data;
  }

  public String getWrap_info() {
    return wrap_info;
  }

  public Auth getAuth() {
    return AuthObject;
  }

  // Setter Methods

  public void setRequest_id(final String request_id) {
    this.request_id = request_id;
  }

  public void setLease_id(final String lease_id) {
    this.lease_id = lease_id;
  }

  public void setRenewable(final boolean renewable) {
    this.renewable = renewable;
  }

  public void setLease_duration(final float lease_duration) {
    this.lease_duration = lease_duration;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public void setWrap_info(final String wrap_info) {
    this.wrap_info = wrap_info;
  }

  public void setAuth(final Auth authObject) {
    this.AuthObject = authObject;
  }
}

class LoginMetadata {
  private String role_name;

  // Getter Methods

  public String getRole_name() {
    return role_name;
  }

  // Setter Methods

  public void setRole_name(final String role_name) {
    this.role_name = role_name;
  }
}