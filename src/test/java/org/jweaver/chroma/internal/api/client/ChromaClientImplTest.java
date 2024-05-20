package org.jweaver.chroma.internal.api.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jweaver.chroma.internal.api.client.ChromaClientImpl.PRE_FLIGHT_CHECKS_ENDPOINT;
import static org.jweaver.chroma.internal.api.client.ChromaClientImpl.RESET_ENDPOINT;
import static org.jweaver.chroma.internal.api.client.ChromaClientImpl.VERSION_ENDPOINT;
import static org.jweaver.chroma.internal.api.helper.HttpMethodType.GET;
import static org.jweaver.chroma.internal.api.helper.HttpMethodType.POST;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jweaver.chroma.internal.api.helper.ChromaResponse;
import org.jweaver.chroma.internal.query.WhereMetadata;
import org.jweaver.chroma.internal.query.WhereMetadataOperator;
import org.mockito.Mock;

class ChromaClientImplTest {

  @Mock private ChromaClientImpl chromaClientImpl;

  @Mock private ApiClient apiClient = mock(ApiClient.class);

  @BeforeEach
  void init() throws NoSuchFieldException, IllegalAccessException {
    chromaClientImpl = mock(ChromaClientImpl.class);

    var writer = ChromaClientImpl.class.getDeclaredField("apiClient");
    writer.setAccessible(true);
    writer.set(chromaClientImpl, apiClient);
  }

  @Test
  void createNewInstanceWithValidConnection_Success() {
    when(apiClient.sendRequest(GET, null)).thenReturn(ChromaResponse.of(null, 200));
    doCallRealMethod().when(chromaClientImpl).validateConnection();
    doCallRealMethod().when(apiClient).validateConnection();
    chromaClientImpl.validateConnection();
    verify(apiClient, times(1)).sendRequest(GET, null);
    verify(chromaClientImpl, times(1)).validateConnection();
  }

  @Test
  void createNewInstanceWithInvalidConnection_Success() {
    doCallRealMethod().when(chromaClientImpl).validateConnection();
    doCallRealMethod().when(apiClient).validateConnection();
    var ex =
        assertThrows(
            ChromaClientConnectionException.class, () -> chromaClientImpl.validateConnection());
    Assertions.assertEquals("Unable to connect to Chroma DB", ex.getMessage());
    verify(apiClient, times(1)).sendRequest(GET, null);
    verify(chromaClientImpl, times(1)).validateConnection();
  }

  @Test
  void resetChromaConnection_returnSuccess() {
    when(apiClient.sendRequest(POST, null, RESET_ENDPOINT))
        .thenReturn(ChromaResponse.of(null, 200));
    doCallRealMethod().when(chromaClientImpl).reset();
    chromaClientImpl.reset();
    verify(apiClient, times(1)).sendRequest(POST, null, "reset");
  }

  @Test
  void getVersion_returnSuccess() {
    when(apiClient.sendRequest(GET, null, VERSION_ENDPOINT))
        .thenReturn(ChromaResponse.of(null, 200));
    doCallRealMethod().when(chromaClientImpl).getVersion();
    chromaClientImpl.getVersion();
    verify(apiClient, times(1)).sendRequest(GET, null, "version");
  }

  @Test
  void getMaxBatchSize_returnSuccess() {
    when(apiClient.sendRequest(GET, null, PRE_FLIGHT_CHECKS_ENDPOINT))
        .thenReturn(ChromaResponse.of("{\"max_batch_size\" : \"4000\"}", 200));
    doCallRealMethod().when(chromaClientImpl).getMaxBatchSize();
    long maxBatchSize = chromaClientImpl.getMaxBatchSize();
    assertEquals(4000, maxBatchSize);
    verify(apiClient, times(1)).sendRequest(GET, null, "pre-flight-checks");
  }

//  @Test
//  void testSendQueryWhereMetadata() {
//
//    var whereMetadata =
//        WhereMetadata.and(
//            WhereMetadata.create("id", WhereMetadataOperator.EQUALS, "100"),
//            WhereMetadata.create("age", WhereMetadataOperator.GREATER_THAN, 20));
//    var map = whereMetadata.getWhereMap();
//    assertNotNull(map);
//    assertTrue(map.containsKey("$and"));
//    @SuppressWarnings("unchecked")
//    var elements = (ArrayList<WhereMetadata>) map.get("$and");
//    assertEquals(2, elements.size());
//  }
}
