package org.jweaver.chroma.internal.api.helper;

public record ChromaConnection(String host) {

  public static ChromaConnection create(String host) {
    return new ChromaConnection(host);
  }
}
