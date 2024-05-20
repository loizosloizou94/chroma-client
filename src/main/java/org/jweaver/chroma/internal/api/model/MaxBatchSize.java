package org.jweaver.chroma.internal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MaxBatchSize(@JsonProperty("max_batch_size") long value) {}
