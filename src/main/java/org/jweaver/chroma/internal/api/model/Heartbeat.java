package org.jweaver.chroma.internal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Heartbeat(@JsonProperty("nanosecond heartbeat") long value) {}
