package org.jweaver.chroma.internal.api.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.jweaver.chroma.internal.api.client.ChromaClientMapperException;

public class ChromaJsonHelper {

  private static ObjectMapper objectMapper;

  private ChromaJsonHelper() {}

  public static ObjectMapper getMapper() {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }
    return objectMapper;
  }

  public static <V> V parseJson(String value, Class<V> clazz) {
    try {
      return getMapper().readValue(value, clazz);
    } catch (JsonProcessingException e) {
      throw new ChromaClientMapperException(e);
    }
  }

  public static <V> List<V> parseJsonCollection(String value, Class<V> clazz) {
    try {
      return getMapper()
          .readValue(
              value, getMapper().getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (JsonProcessingException e) {
      throw new ChromaClientMapperException(e);
    }
  }
}
