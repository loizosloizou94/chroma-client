package org.jweaver.chroma.internal.api.collections;

import java.util.Map;

public record CollectionInfoResponse(
    String id, String name, String tenant, String database, Map<String, Object> metadata) {}
