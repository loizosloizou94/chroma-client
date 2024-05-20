package org.jweaver.chroma.internal.api.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public record BasicAuthentication(String username, String password)
    implements ChromaAuthentication {

  @Override
  public String header() {
    return "Authorization";
  }

  @Override
  public String value() {
    var credentials =
        Base64.getEncoder()
            .encodeToString(
                String.format("{%s}:{%s}", username, password).getBytes(StandardCharsets.UTF_8));
    return String.format("Basic {%s}", credentials);
  }
}
