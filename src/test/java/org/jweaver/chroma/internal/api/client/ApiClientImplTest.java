package org.jweaver.chroma.internal.api.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jweaver.chroma.internal.api.auth.ChromaAuthentication;
import org.mockito.Mock;

class ApiClientImplTest {

  @Mock private HttpClient httpClient = mock(HttpClient.class);

  @Test
  void testValidateConnectionWithChromaOfflineFailure() throws IOException, InterruptedException {

    var authentication = ChromaAuthentication.basicAuthentication("username", "password");
    ApiClient apiClient = new ApiClient(httpClient, authentication, "http://localhost:8090");
    var ex =
        assertThrows(ChromaClientConnectionException.class, () -> apiClient.validateConnection());
    Assertions.assertEquals("Cannot connect to Chroma DB [http://localhost:8090]", ex.getMessage());
    var req =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8090/api/v1"))
            .header("Content-Type", "application/json")
            .header(authentication.header(), authentication.value())
            .GET()
            .build();
    verify(httpClient, times(1)).send(req, HttpResponse.BodyHandlers.ofString());
  }
}
