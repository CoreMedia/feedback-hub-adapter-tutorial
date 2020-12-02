package com.coremedia.blueprint.studio.feedback.wordcounter.items;

import com.coremedia.feedbackhub.items.FeedbackItem;

/**
 *
 */
public class RecursiveTextFeedbackItem implements FeedbackItem {

  private String text;
  private String collection;

  public RecursiveTextFeedbackItem(String collection, String text) {
    this.collection = collection;
    this.text = text;
  }

  public RecursiveTextFeedbackItem(String text) {
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
    return "recursiveText";
  }
}
