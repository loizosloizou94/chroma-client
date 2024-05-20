package org.jweaver.chroma.internal.api.client;

public enum APIVersion {
  V1("v1");

  private final String value;

  APIVersion(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
