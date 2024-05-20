package org.jweaver.chroma.internal.api.client;

public class ChromaClientConnectionException extends ChromaException {
  public ChromaClientConnectionException(String errorMessage) {
    super(errorMessage);
  }

  public ChromaClientConnectionException(Exception ex) {
    super(ex);
  }
}
