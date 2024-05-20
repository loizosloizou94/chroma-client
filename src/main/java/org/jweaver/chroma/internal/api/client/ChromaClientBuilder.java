package org.jweaver.chroma.internal.api.client;

import java.net.http.HttpClient;
import java.util.Objects;
import org.jweaver.chroma.internal.api.auth.ChromaAuthentication;

public class ChromaClientBuilder {

  private String host;
  private HttpClient httpClient;
  private ChromaAuthentication chromaAuthentication;

  public ChromaClientBuilder authentication(ChromaAuthentication authentication) {
    this.chromaAuthentication = authentication;
    return this;
  }

  public ChromaClientBuilder httpClient(HttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  public ChromaClientBuilder host(String host) {
    this.host = host;
    return this;
  }

  public ChromaClientImpl build() {
    Objects.requireNonNull(host, "No Chroma host specified");
    return new ChromaClientImpl(this.host, this.chromaAuthentication, this.httpClient);
  }
}
