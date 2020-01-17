package com.pradykn.kafkaconnect.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth {
  private String client_token;
  private String accessor;
  ArrayList<Object> policies = new ArrayList<Object>();
  ArrayList<Object> token_policies = new ArrayList<Object>();
  @JsonProperty("Metadata")
  LoginMetadata MetadataObject;
  private float lease_duration;
  private boolean renewable;
  private String entity_id;
  private String token_type;
  private boolean orphan;

  // Getter Methods

  public String getClient_token() {
    return client_token;
  }

  public String getAccessor() {
    return accessor;
  }

  public LoginMetadata getMetadata() {
    return MetadataObject;
  }

  public float getLease_duration() {
    return lease_duration;
  }

  public boolean getRenewable() {
    return renewable;
  }

  public String getEntity_id() {
    return entity_id;
  }

  public String getToken_type() {
    return token_type;
  }

  public boolean getOrphan() {
    return orphan;
  }

  // Setter Methods

  public void setClient_token(final String client_token) {
    this.client_token = client_token;
  }

  public void setAccessor(final String accessor) {
    this.accessor = accessor;
  }

  public void setMetadata(final LoginMetadata metadataObject) {
    this.MetadataObject = metadataObject;
  }

  public void setLease_duration(final float lease_duration) {
    this.lease_duration = lease_duration;
  }

  public void setRenewable(final boolean renewable) {
    this.renewable = renewable;
  }

  public void setEntity_id(final String entity_id) {
    this.entity_id = entity_id;
  }

  public void setToken_type(final String token_type) {
    this.token_type = token_type;
  }

  public void setOrphan(final boolean orphan) {
    this.orphan = orphan;
  }
}