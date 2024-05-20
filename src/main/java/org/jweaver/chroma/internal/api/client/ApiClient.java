package org.jweaver.chroma.internal.api.client;

import static org.jweaver.chroma.internal.api.client.ChromaClientImpl.API_PATH;
import static org.jweaver.chroma.internal.api.client.ChromaClientImpl.API_VERSION;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Optional;
import org.apache.hc.core5.net.URIBuilder;
import org.jweaver.chroma.internal.api.auth.ChromaAuthentication;
import org.jweaver.chroma.internal.api.helper.ChromaJsonHelper;
import org.jweaver.chroma.internal.api.helper.ChromaResponse;
import org.jweaver.chroma.internal.api.helper.HttpMethodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

  private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
  private final HttpClient httpClient;
  private final ChromaAuthentication chromaAuthentication;
  private final String baseUri;

  ApiClient(HttpClient httpClient, ChromaAuthentication chromaAuthentication, String baseUri) {
    this.httpClient = httpClient;
    this.chromaAuthentication = chromaAuthentication;
    this.baseUri = baseUri;
  }

  void validateConnection() {
    ChromaResponse response = null;
    try {
      response = sendRequest(HttpMethodType.GET, null);
    } catch (Exception e) {
      throw new ChromaClientConnectionException(
          "Cannot connect to Chroma DB [" + this.baseUri + "]");
    }
    if (response == null) {
      throw new ChromaClientConnectionException("Unable to connect to Chroma DB");
    }
  }

  <T> ChromaResponse sendRequest(HttpMethodType method, T body, String... urlParams) {
    var req =
        HttpRequest.newBuilder()
            .uri(buildUri(urlParams))
            .header("Content-Type", "application/json");
    if (this.chromaAuthentication != null) {
      req.header(chromaAuthentication.header(), chromaAuthentication.value());
    }
    switch (method) {
      case GET -> req.GET();
      case DELETE -> req.DELETE();
      case POST -> req.POST(body(body));
      case PUT -> req.PUT(body(body));
    }
    var request = req.build();
    log.debug(
        "Sending {} request to {}",
        request.method() != null ? request.method() : "GET",
        request.uri());
    try {
      var httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      log.debug("Chroma DB responded with status {}", httpResponse.statusCode());
      return ChromaResponse.of(httpResponse.body(), httpResponse.statusCode());
    } catch (IOException e) {
      throw new ChromaClientConnectionException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ChromaClientConnectionException(e);
    }
  }

  <T> HttpRequest.BodyPublisher body(T body) {
    return Optional.ofNullable(body)
        .map(t -> HttpRequest.BodyPublishers.ofString(json(t)))
        .orElseGet(HttpRequest.BodyPublishers::noBody);
  }

  <T> String json(T body) {
    try {
      return ChromaJsonHelper.getMapper().writeValueAsString(body);
    } catch (JsonProcessingException e) {
      throw new ChromaClientConnectionException(e);
    }
  }

  URI buildUri(String... params) {
    try {
      URIBuilder uriBuilder = new URIBuilder(baseUri).appendPath(API_PATH).appendPath(API_VERSION);
      Arrays.stream(params).forEach(uriBuilder::appendPath);
      return uriBuilder.build();
    } catch (URISyntaxException e) {
      throw new ChromaClientConnectionException(e);
    }
  }
}
