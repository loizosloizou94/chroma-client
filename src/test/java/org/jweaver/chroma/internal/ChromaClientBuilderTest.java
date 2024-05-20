package org.jweaver.chroma.internal;

import java.net.http.HttpClient;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jweaver.chroma.ChromaClient;
import org.jweaver.chroma.internal.api.auth.ChromaAuthentication;

class ChromaClientBuilderTest {

  @Test
  void testChromaBuilder_withOnlyHostProvidedSuccess() {
    var chromaClient = ChromaClient.builder().host("http://localhost:1234").build();
    Assertions.assertNotNull(chromaClient);
  }

  @Test
  void testChromaBuilder_withoutHostProvidedNpeException() {
    var builder = ChromaClient.builder();
    Objects.requireNonNull(builder);
    var ex = Assertions.assertThrows(NullPointerException.class, builder::build);
    Assertions.assertEquals("No Chroma host specified", ex.getMessage());
  }

  @Test
  void testChromaBuilder_withFullArgsProvidedSuccess() {

    var chromaClient =
        ChromaClient.builder()
            .httpClient(HttpClient.newHttpClient())
            .authentication(ChromaAuthentication.basicAuthentication("test", "test"))
            .host("http://localhost:1234")
            .build();
    Assertions.assertNotNull(chromaClient);
  }
}
