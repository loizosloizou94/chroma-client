package org.jweaver.chroma.internal.api.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record CollectionCreateRequest(
    String name,
    Map<String, Object> metadata,
    @JsonProperty("get_or_create") boolean getOrCreate) {}
