package com.coremedia.labs.plugins.feedbackhub.wordcounter.items;

import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 *
 */
public class CursiveTextFeedbackItem implements FeedbackItem {

  private String text;
  private String collection;

  public CursiveTextFeedbackItem(String collection, String text) {
    this.collection = collection;
    this.text = text;
  }

  public CursiveTextFeedbackItem(String text) {
    this(null, text);
  }

  public String getText() {
    return text;
  }

  @Override
  public String getCollection() {
    return collection;
  }

  @Override
  public String getType() {
    return "cursiveText";
  }
}
