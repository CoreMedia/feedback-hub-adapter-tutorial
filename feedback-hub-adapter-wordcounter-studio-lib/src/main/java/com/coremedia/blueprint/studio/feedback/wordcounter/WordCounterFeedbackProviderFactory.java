package com.coremedia.blueprint.studio.feedback.wordcounter;

import com.coremedia.feedbackhub.provider.FeedbackProvider;
import com.coremedia.feedbackhub.provider.FeedbackProviderFactory;

public class WordCounterFeedbackProviderFactory implements FeedbackProviderFactory<WordCounterSettings> {

  @Override
  public String getId() {
    return "wordcounter";
  }

  @Override
  public FeedbackProvider create(WordCounterSettings settings) {
    return new WordCounterFeedbackProvider(settings);
  }
}
