package org.jweaver.chroma.internal.api.embeddings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.jweaver.chroma.internal.query.WhereDocument;

public record EmbeddingsDeleteRequest(
    List<String> ids,
    Map<String, Object> where,
    @JsonProperty("where_documents") WhereDocument whereDocuments) {}
