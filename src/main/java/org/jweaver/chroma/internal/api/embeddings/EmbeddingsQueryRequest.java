package org.jweaver.chroma.internal.api.embeddings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.jweaver.chroma.internal.api.helper.IncludeType;
import org.jweaver.chroma.internal.query.WhereDocument;

public record EmbeddingsQueryRequest(
    @JsonProperty("where_document") WhereDocument whereDocument,
    @JsonProperty("where") Map<String, Object> whereMetadata,
    @JsonProperty("query_embeddings") List<float[]> embeddings,
    @JsonProperty("n_results") int numOfResults,
    @JsonProperty("include") List<IncludeType> includeTypes) {}
