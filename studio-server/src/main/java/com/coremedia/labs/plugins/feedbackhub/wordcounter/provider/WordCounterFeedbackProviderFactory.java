package com.coremedia.labs.plugins.feedbackhub.wordcounter.provider;

import com.coremedia.labs.plugins.feedbackhub.wordcounter.WordCounterFeedbackHubErrorCode;
import com.coremedia.labs.plugins.feedbackhub.wordcounter.WordCounterSettings;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;
import com.coremedia.feedbackhub.provider.FeedbackProvider;
import com.coremedia.feedbackhub.provider.FeedbackProviderFactory;

public class WordCounterFeedbackProviderFactory implements FeedbackProviderFactory<WordCounterSettings> {

  @Override
  public String getId() {
    return "wordCountProvider";
  }

  @Override
  public FeedbackProvider create(WordCounterSettings settings) {
    Integer target = settings.getTarget();
    if (target == null || target <= 0) {
      throw new FeedbackHubException("settings must provide a target", WordCounterFeedbackHubErrorCode.TARGET_NOT_SET);
    }

    return new WordCounterFeedbackProvider(settings);
  }
}
