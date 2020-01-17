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

import com.fasterxml.jackson.annotation.JsonProperty;

public class VaultDataResponse {
    private String request_id;
    private String lease_id;
    private boolean renewable;
    private float lease_duration;
    @JsonProperty("data")
    Data DataObject;
    private String wrap_info = null;
    private String warnings = null;
    private String auth = null;
  
  
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
  
    public Data getData() {
      return DataObject;
    }
  
    public String getWrap_info() {
      return wrap_info;
    }
  
    public String getWarnings() {
      return warnings;
    }
  
    public String getAuth() {
      return auth;
    }
  
   // Setter Methods 
  
    public void setRequest_id( String request_id ) {
      this.request_id = request_id;
    }
  
    public void setLease_id( String lease_id ) {
      this.lease_id = lease_id;
    }
  
    public void setRenewable( boolean renewable ) {
      this.renewable = renewable;
    }
  
    public void setLease_duration( float lease_duration ) {
      this.lease_duration = lease_duration;
    }
  
    public void setData( Data dataObject ) {
      this.DataObject = dataObject;
    }
  
    public void setWrap_info( String wrap_info ) {
      this.wrap_info = wrap_info;
    }
  
    public void setWarnings( String warnings ) {
      this.warnings = warnings;
    }
  
    public void setAuth( String auth ) {
      this.auth = auth;
    }
  }