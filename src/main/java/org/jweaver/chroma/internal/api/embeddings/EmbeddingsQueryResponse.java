package org.jweaver.chroma.internal.api.embeddings;

import java.util.List;
import java.util.Map;

public record EmbeddingsQueryResponse(
    List<String[]> ids,
    List<float[]> distances,
    List<float[][]> embeddings,
    List<Map<String, Object>> metadatas,
    List<String[]> documents,
    List<Object> uris) {}
