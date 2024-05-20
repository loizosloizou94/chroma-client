package org.jweaver.chroma.internal.api.embeddings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.jweaver.chroma.internal.api.helper.IncludeType;
import org.jweaver.chroma.internal.query.WhereDocument;

public record EmbeddingsGetRequest(
    List<String> ids,
    Map<String, Object> where, // filter by metadata
    @JsonProperty("where_documents") WhereDocument whereDocuments, // filter by documents
    int limit,
    int offset,
    List<IncludeType> include) {}
