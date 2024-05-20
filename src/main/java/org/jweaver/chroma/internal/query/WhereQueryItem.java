package org.jweaver.chroma.internal.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class WhereQueryItem {

  @JsonProperty("$eq")
  private Object equalString;

  @JsonProperty("$ne")
  private Object notEqualString;

  @JsonProperty("$gt")
  private Float greaterThan;

  @JsonProperty("$gte")
  private Float greaterEqual;

  @JsonProperty("$lt")
  private Float lessThan;

  @JsonProperty("$lte")
  private Float lessEqual;

  @JsonProperty("$in")
  private List<Object> inList;

  @JsonProperty("$nin")
  private List<Object> notInList;

  private WhereQueryItem(WhereMetadataOperator operator, List<?> valueList) {
    switch (operator) {
      case IN -> {
        this.inList = new ArrayList<>();
        this.inList.addAll(valueList);
      }
      case NOT_IN -> {
        this.notInList = new ArrayList<>();
        this.notInList.addAll(valueList);
      }
      default -> throw new UnsupportedOperationException();
    }
  }

  private WhereQueryItem(WhereMetadataOperator operator, float value) {
    switch (operator) {
      case GREATER_THAN -> this.greaterThan = value;
      case GREATER_AND_EQUAL -> this.greaterEqual = value;
      case LESS_THAN -> this.lessThan = value;
      case LESS_AND_EQUAL -> this.lessEqual = value;
      case NOT_EQUALS -> this.notEqualString = value;
      case EQUALS -> this.equalString = value;
      default -> throw new UnsupportedOperationException();
    }
  }

  private WhereQueryItem(WhereMetadataOperator operator, String value) {
    switch (operator) {
      case NOT_EQUALS -> this.notEqualString = value;
      case EQUALS -> this.equalString = value;
      default -> throw new UnsupportedOperationException();
    }
  }

  public static WhereQueryItem createList(WhereMetadataOperator operator, List<?> values) {
    return new WhereQueryItem(operator, values);
  }

  public static WhereQueryItem create(WhereMetadataOperator operator, float value) {
    return new WhereQueryItem(operator, value);
  }

  public static WhereQueryItem create(WhereMetadataOperator operator, String value) {
    return new WhereQueryItem(operator, value);
  }
}
