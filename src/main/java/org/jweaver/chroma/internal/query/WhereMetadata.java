package org.jweaver.chroma.internal.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhereMetadata {

  private Map<String, Object> whereMap = new HashMap<>();

  public WhereMetadata(String key, WhereMetadataOperator operator, float value) {
    whereMap.put(key, WhereQueryItem.create(operator, value));
  }

  public WhereMetadata(String key, WhereMetadataOperator operator, String value) {
    whereMap.put(key, WhereQueryItem.create(operator, value));
  }

  public WhereMetadata(String key, WhereMetadataOperator operator, List<?> objectList) {
    whereMap.put(key, WhereQueryItem.createList(operator, objectList));
  }

  public WhereMetadata(String key, WhereMetadata... metadataList) {
    var innerList = new ArrayList<>();
    for (WhereMetadata metadata : metadataList) {
      innerList.add(metadata.getWhereMap());
    }
    whereMap.put(key, innerList);
  }

  public static WhereMetadata create(String key, WhereMetadataOperator operator, float value) {
    return new WhereMetadata(key, operator, value);
  }

  public static WhereMetadata create(String key, WhereMetadataOperator operator, String value) {
    return new WhereMetadata(key, operator, value);
  }

  public static WhereMetadata create(
      String key, WhereMetadataOperator operator, List<?> itemsList) {
    return new WhereMetadata(key, operator, itemsList);
  }

  public static WhereMetadata and(WhereMetadata... whereMetadataList) {
    return new WhereMetadata("$and", whereMetadataList);
  }

  public static WhereMetadata or(WhereMetadata... whereMetadataList) {
    return new WhereMetadata("$or", whereMetadataList);
  }

  public Map<String, Object> getWhereMap() {
    return whereMap;
  }

  public void setWhereMap(Map<String, Object> whereMap) {
    this.whereMap = whereMap;
  }
}
