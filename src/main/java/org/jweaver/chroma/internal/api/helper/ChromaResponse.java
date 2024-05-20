package org.jweaver.chroma.internal.api.helper;

import org.jweaver.chroma.internal.api.client.ChromaErrorHandler;
import org.jweaver.chroma.internal.api.client.ChromaOperationException;

public record ChromaResponse(String body, int statusCode) {
  public static ChromaResponse of(String body, int statusCode) {
    if (statusCode >= 400) {
      throw new ChromaOperationException(ChromaErrorHandler.getChromaException(body));
    }
    return new ChromaResponse(body, statusCode);
  }
}
