package org.jweaver.chroma;

import java.util.List;
import org.jweaver.chroma.internal.api.client.ChromaClientBuilder;
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

public interface ChromaClient {

  static ChromaClientBuilder builder() {
    return new ChromaClientBuilder();
  }

  /**
   * Empties and completely resets the database. This is destructive and not reversible. Note: To
   * enable this functionality, set `allow_reset` to `True` in your chroma settings or include
   * `ALLOW_RESET=TRUE` in your database environment variables
   */
  void reset();

  /**
   * Retrieves the current server time in nanoseconds to check if the server is alive.
   *
   * @return The current server time in nanoseconds
   */
  long getHeartbeat();

  /**
   * Retrieves the version of the Chroma db instance.
   *
   * @return The version of the Chroma db instance
   */
  String getVersion();

  /**
   * Retrieves the maximum supported batch size.
   *
   * @return The maximum supported batch size
   */
  long getMaxBatchSize();

  /**
   * Retrieves a list of collections.
   *
   * @return A list of collections
   */
  List<CollectionInfoResponse> getCollections();

  /**
   * Creates a new collection.
   *
   * @param request The collection details for the new collection
   * @return The created Collection
   */
  CollectionInfoResponse createCollection(CollectionCreateRequest request);

  /**
   * Retrieves the number of existing collections.
   *
   * @return The number of existing collections
   */
  int countCollections();

  /**
   * Inserts embeddings into the specified collection.
   *
   * @param collectionId The collection to insert the embeddings
   * @param embeddingsInsertType The type to insert the embeddings (ADD, UPDATE, UPSERT)
   * @param request The embeddings request model to add to the collection
   */
  void insertEmbeddings(
      String collectionId, EmbeddingsInsertType embeddingsInsertType, EmbeddingsAddRequest request);

  /**
   * Retrieves the number of embeddings in the specified collection.
   *
   * @param collectionId The collection to retrieve the embeddings
   * @return The number of embeddings in the collection
   */
  int collectionEmbeddingsCount(String collectionId);

  /**
   * Retrieves embeddings data from the specified collection.
   *
   * @param collectionId The collection from which to retrieve the embeddings data
   * @param request The embeddings get request
   * @return The embeddings data response
   */
  CollectionDataResponse getEmbeddingsData(String collectionId, EmbeddingsGetRequest request);

  /**
   * Deletes embeddings data from the specified collection.
   *
   * @param collectionId The collection to delete the embeddings data from
   * @param request The embeddings delete request
   */
  void deleteEmbeddingsData(String collectionId, EmbeddingsDeleteRequest request);

  /**
   * Executes a query on the specified collection.
   *
   * @param collectionId The collection to execute the query on
   * @param request The embeddings query request
   * @return The embeddings query response
   */
  EmbeddingsQueryResponse query(String collectionId, EmbeddingsQueryRequest request);

  /**
   * Retrieves collection information by name.
   *
   * @param collectionName The name of the collection
   * @return The collection information
   */
  CollectionInfoResponse getCollectionByName(String collectionName);

  /**
   * Deletes a collection by name.
   *
   * @param collectionName The name of the collection to delete
   */
  void deleteCollectionByName(String collectionName);

  /**
   * Updates collection data.
   *
   * @param collectionId The ID of the collection to update
   * @param request The collection update request
   */
  void updateCollectionData(String collectionId, CollectionUpdateRequest request);
}
