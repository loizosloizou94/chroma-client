package org.jweaver.chroma.internal.api.client;

public abstract class ChromaException extends RuntimeException {

  protected ChromaException(String errorMessage) {
    super(errorMessage);
  }

  protected ChromaException(Exception ex) {
    super(ex);
  }
}
