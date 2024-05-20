package org.jweaver.chroma.internal.api.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record CollectionUpdateRequest(
    @JsonProperty("new_name") String name,
    @JsonProperty("new_metadata") Map<String, Object> metadata) {}
