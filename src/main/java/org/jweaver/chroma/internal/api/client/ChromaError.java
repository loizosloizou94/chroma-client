package org.jweaver.chroma.internal.api.client;

public record ChromaError(String error) {

  private static final String ERROR_PREFIX = "ValueError('";

  public String errorMessage() {
    if (error != null && error.startsWith(ERROR_PREFIX)) {
      var lastIndex = error.lastIndexOf("'");
      var startIndex = error.indexOf("'");
      return error.substring(startIndex + 1, lastIndex - 1);
    }
    return error;
  }
}
