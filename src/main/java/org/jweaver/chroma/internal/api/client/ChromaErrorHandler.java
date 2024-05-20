package org.jweaver.chroma.internal.api.client;

import org.jweaver.chroma.internal.api.helper.ChromaJsonHelper;

public class ChromaErrorHandler {

  private ChromaErrorHandler() {}

  public static String getChromaException(String body) {
    var error = ChromaJsonHelper.parseJson(body, ChromaError.class);
    return error.errorMessage();
  }
}
