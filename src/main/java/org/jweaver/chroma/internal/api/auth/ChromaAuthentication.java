package org.jweaver.chroma.internal.api.auth;

public sealed interface ChromaAuthentication permits BasicAuthentication, TokenAuthentication {
  static ChromaAuthentication basicAuthentication(String username, String password) {
    return new BasicAuthentication(username, password);
  }

  String header();

  String value();
}
