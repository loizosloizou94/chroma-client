package org.jweaver.chroma.internal.api.auth;

public record TokenAuthentication(AuthHeaderType type, String token)
    implements ChromaAuthentication {
  @Override
  public String header() {
    return switch (type) {
      case AUTHORIZATION -> "Authorization";
      case CHROMA_TOKEN_HEADER -> "X-Chroma-Token";
    };
  }

  @Override
  public String value() {
    return switch (type) {
      case AUTHORIZATION -> String.format("Bearer {%s}", token);
      case CHROMA_TOKEN_HEADER -> token;
    };
  }
}
