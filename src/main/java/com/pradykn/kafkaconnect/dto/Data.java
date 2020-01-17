package com.pradykn.kafkaconnect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
      @JsonProperty("data")
    VaultData DataObject;
    Metadata MetadataObject;
  
  
   // Getter Methods 
  
    public VaultData getData() {
      return DataObject;
    }
  
    public Metadata getMetadata() {
      return MetadataObject;
    }
  
   // Setter Methods 
  
    public void setData( VaultData dataObject ) {
      this.DataObject = dataObject;
    }
  
    public void setMetadata( Metadata metadataObject ) {
      this.MetadataObject = metadataObject;
    }
  }