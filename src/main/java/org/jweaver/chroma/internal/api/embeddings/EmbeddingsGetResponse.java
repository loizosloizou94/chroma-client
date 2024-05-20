package org.jweaver.chroma.internal.api.embeddings;

import java.util.List;
import java.util.Map;

public record EmbeddingsGetResponse(
    List<String> ids,
    List<float[]> embeddings,
    List<Map<String, Object>> metadatas,
    List<String> documents,
    List<String> uris) {}
