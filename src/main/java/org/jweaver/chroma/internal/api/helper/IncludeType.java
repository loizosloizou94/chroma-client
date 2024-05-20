package org.jweaver.chroma.internal.api.helper;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IncludeType {
  DOCUMENTS("documents"),
  EMBEDDINGS("embeddings"),
  METADATA("metadatas"),
  DISTANCES("distances"),
  URIS("uris"),
  DATA("data");

  private final String value;

  IncludeType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return this.value;
  }
}
