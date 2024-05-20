package org.jweaver.chroma.internal.api.client;

import static org.jweaver.chroma.internal.api.helper.ChromaJsonHelper.parseJson;
import static org.jweaver.chroma.internal.api.helper.ChromaJsonHelper.parseJsonCollection;

import java.net.http.HttpClient;
import java.util.List;
import org.jweaver.chroma.ChromaClient;
import org.jweaver.chroma.internal.api.auth.ChromaAuthentication;
import org.jweaver.chroma.internal.api.collections.CollectionCreateRequest;
import org.jweaver.chroma.internal.api.collections.CollectionDataResponse;
import org.jweaver.chroma.internal.api.collections.CollectionInfoResponse;
import org.jweaver.chroma.internal.api.collections.CollectionUpdateRequest;
import org.jweaver.chroma.internal.api.embeddings.EmbeddingsAddRequest;
import org.jweaver.chroma.internal.api.embeddings.EmbeddingsDeleteRequest;
import org.jweaver.chroma.internal.api.embeddings.EmbeddingsGetRequest;
import org.jweaver.chroma.internal.api.embeddings.EmbeddingsInsertType;
import org.jweaver.chroma.internal.api.embeddings.EmbeddingsQueryRequest;
import org.jweaver.chroma.internal.api.embeddings.EmbeddingsQueryResponse;
import org.jweaver.chroma.internal.api.helper.HttpMethodType;
import org.jweaver.chroma.internal.api.model.Heartbeat;
import org.jweaver.chroma.internal.api.model.MaxBatchSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChromaClientImpl implements ChromaClient {

  public static final String API_PATH = "api";
  public static final String API_VERSION = APIVersion.V1.getValue();
  private static final Logger log = LoggerFactory.getLogger(ChromaClientImpl.class);
  static final String HEARTBEAT_ENDPOINT = "heartbeat";
  static final String VERSION_ENDPOINT = "version";
  static final String PRE_FLIGHT_CHECKS_ENDPOINT = "pre-flight-checks";
  static final String COLLECTIONS_ENDPOINT = "collections";
  static final String COUNT_COLLECTIONS_ENDPOINT = "count_collections";
  static final String RESET_ENDPOINT = "reset";
  static final String COUNT_ENDPOINT = "count";
  static final String GET_ENDPOINT = "get";
  static final String DELETE_ENDPOINT = "delete";
  static final String QUERY_ENDPOINT = "query";
  static final String EMBEDDINGS_ACTION_ADD = "add";
  static final String EMBEDDINGS_ACTION_UPDATE = "update";
  static final String EMBEDDINGS_ACTION_UPSERT = "upsert";
  private final ApiClient apiClient;

  ChromaClientImpl(String host, ChromaAuthentication authentication, HttpClient httpClient) {
    log.info("Initializing a new ChromaClient for {}", host);
    if (httpClient == null) {
      log.debug("No http client provided. Using the default client");
      httpClient =
          HttpClient.newBuilder()
              .version(HttpClient.Version.HTTP_1_1)
              .followRedirects(HttpClient.Redirect.NORMAL)
              .build();
    }
    this.apiClient = new ApiClient(httpClient, authentication, host);
  }

  public void validateConnection() {
    this.apiClient.validateConnection();
  }

  @Override
  public void reset() {
    apiClient.sendRequest(HttpMethodType.POST, null, RESET_ENDPOINT);
  }

  @Override
  public long getHeartbeat() {
    var httpResponse = apiClient.sendRequest(HttpMethodType.GET, null, HEARTBEAT_ENDPOINT);
    return parseJson(httpResponse.body(), Heartbeat.class).value();
  }

  @Override
  public String getVersion() {
    var httpResponse = apiClient.sendRequest(HttpMethodType.GET, null, VERSION_ENDPOINT);
    return httpResponse.body();
  }

  @Override
  public long getMaxBatchSize() {
    var httpResponse = apiClient.sendRequest(HttpMethodType.GET, null, PRE_FLIGHT_CHECKS_ENDPOINT);
    return parseJson(httpResponse.body(), MaxBatchSize.class).value();
  }

  @Override
  public List<CollectionInfoResponse> getCollections() {
    var httpResponse = apiClient.sendRequest(HttpMethodType.GET, null, COLLECTIONS_ENDPOINT);
    return parseJsonCollection(httpResponse.body(), CollectionInfoResponse.class);
  }

  @Override
  public CollectionInfoResponse createCollection(CollectionCreateRequest request) {
    var httpResponse = apiClient.sendRequest(HttpMethodType.POST, request, COLLECTIONS_ENDPOINT);
    return parseJson(httpResponse.body(), CollectionInfoResponse.class);
  }

  @Override
  public int countCollections() {
    var httpResponse = apiClient.sendRequest(HttpMethodType.GET, null, COUNT_COLLECTIONS_ENDPOINT);
    return parseJson(httpResponse.body(), Integer.class);
  }

  @Override
  public void insertEmbeddings(
      String collectionId,
      EmbeddingsInsertType embeddingsInsertType,
      EmbeddingsAddRequest request) {
    apiClient.sendRequest(
        HttpMethodType.POST,
        request,
        COLLECTIONS_ENDPOINT,
        collectionId,
        switch (embeddingsInsertType) {
          case ADD -> EMBEDDINGS_ACTION_ADD;
          case UPDATE -> EMBEDDINGS_ACTION_UPDATE;
          case UPSERT -> EMBEDDINGS_ACTION_UPSERT;
        });
  }

  @Override
  public int collectionEmbeddingsCount(String collectionId) {
    var httpResponse =
        apiClient.sendRequest(
            HttpMethodType.GET, null, COLLECTIONS_ENDPOINT, collectionId, COUNT_ENDPOINT);
    return parseJson(httpResponse.body(), Integer.class);
  }

  @Override
  public CollectionDataResponse getEmbeddingsData(
      String collectionId, EmbeddingsGetRequest request) {
    var httpResponse =
        apiClient.sendRequest(
            HttpMethodType.POST, request, COLLECTIONS_ENDPOINT, collectionId, GET_ENDPOINT);
    return parseJson(httpResponse.body(), CollectionDataResponse.class);
  }

  @Override
  public void deleteEmbeddingsData(String collectionId, EmbeddingsDeleteRequest request) {
    apiClient.sendRequest(
        HttpMethodType.POST, request, COLLECTIONS_ENDPOINT, collectionId, DELETE_ENDPOINT);
  }

  @Override
  public EmbeddingsQueryResponse query(String collectionId, EmbeddingsQueryRequest request) {
    var httpResponse =
        apiClient.sendRequest(
            HttpMethodType.POST, request, COLLECTIONS_ENDPOINT, collectionId, QUERY_ENDPOINT);
    return parseJson(httpResponse.body(), EmbeddingsQueryResponse.class);
  }

  @Override
  public CollectionInfoResponse getCollectionByName(String collectionName) {
    var httpResponse =
        apiClient.sendRequest(HttpMethodType.GET, null, COLLECTIONS_ENDPOINT, collectionName);
    return parseJson(httpResponse.body(), CollectionInfoResponse.class);
  }

  @Override
  public void deleteCollectionByName(String collectionName) {
    apiClient.sendRequest(HttpMethodType.DELETE, null, COLLECTIONS_ENDPOINT, collectionName);
  }

  @Override
  public void updateCollectionData(String collectionId, CollectionUpdateRequest request) {
    apiClient.sendRequest(HttpMethodType.PUT, request, COLLECTIONS_ENDPOINT, collectionId);
  }
}
