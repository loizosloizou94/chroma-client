package org.jweaver.chroma.internal.api.embeddings;

import java.util.List;
import java.util.Map;

public record EmbeddingsAddRequest(
    List<float[]> embeddings,
    List<String> ids,
    List<String> documents,
    List<String> uris,
    Map<String, String> metadata) {}
