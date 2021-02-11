package com.coremedia.labs.plugins.feedbackhub.wordcounter;

/**
 *
 */
public interface WordCounterSettings {

  /**
   * Returns a comma separated list o words that are exluded from the word count.
   */
  String getIgnoreList();

  /**
   * Returns the amount of words the text should have.
   */
  Integer getTarget();
}