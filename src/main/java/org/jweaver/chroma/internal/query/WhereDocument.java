package org.jweaver.chroma.internal.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;

public class WhereDocument {

  @JsonProperty("$contains")
  String contains;

  @JsonProperty("$not_contains")
  String notContains;

  @JsonProperty("$and")
  List<WhereDocument> and;

  @JsonProperty("$or")
  List<WhereDocument> or;

  private WhereDocument(WhereDocumentOperator operator, String value) {
    switch (operator) {
      case CONTAINS -> this.contains = value;
      case NOT_CONTAINS -> this.notContains = value;
      case AND, OR -> throw new UnsupportedOperationException();
    }
  }

  private WhereDocument(WhereDocumentOperator operator, List<WhereDocument> whereDocuments) {
    switch (operator) {
      case AND -> this.and = whereDocuments;
      case OR -> this.or = whereDocuments;
      case CONTAINS, NOT_CONTAINS -> throw new UnsupportedOperationException();
    }
  }

  public static WhereDocument and(WhereDocument... whereDocuments) {
    if (whereDocuments.length < 2) {
      throw new QueryBuilderException("AND operator must contain at least 2 conditions");
    }
    return new WhereDocument(WhereDocumentOperator.AND, Arrays.asList(whereDocuments));
  }

  public static WhereDocument or(WhereDocument... whereDocuments) {
    if (whereDocuments.length < 2) {
      throw new QueryBuilderException("OR operator must contain at least 2 conditions");
    }
    return new WhereDocument(WhereDocumentOperator.OR, Arrays.asList(whereDocuments));
  }

  public static WhereDocument contain(String value) {
    return new WhereDocument(WhereDocumentOperator.CONTAINS, value);
  }

  public static WhereDocument notContain(String value) {
    return new WhereDocument(WhereDocumentOperator.NOT_CONTAINS, value);
  }
}
