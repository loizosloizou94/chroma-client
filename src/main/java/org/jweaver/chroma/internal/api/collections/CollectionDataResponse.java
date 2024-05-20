package org.jweaver.chroma.internal.api.collections;

import java.util.List;
import java.util.Map;

public record CollectionDataResponse(
    List<String> ids,
    List<float[]> embeddings,
    Map<String, Object> metadata,
    List<String> documents,
    List<String> uris) {}
