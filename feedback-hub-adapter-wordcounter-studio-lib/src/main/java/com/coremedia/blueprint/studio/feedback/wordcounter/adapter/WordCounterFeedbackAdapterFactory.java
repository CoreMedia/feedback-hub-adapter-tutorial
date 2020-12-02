package com.coremedia.blueprint.studio.feedback.wordcounter.adapter;

import com.coremedia.blueprint.studio.feedback.wordcounter.WordCounterFeedbackHubErrorCode;
import com.coremedia.blueprint.studio.feedback.wordcounter.WordCounterSettings;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapter;
import com.coremedia.feedbackhub.adapter.FeedbackHubAdapterFactory;
import com.coremedia.feedbackhub.adapter.FeedbackHubException;

public class WordCounterFeedbackAdapterFactory implements FeedbackHubAdapterFactory<WordCounterSettings> {

  @Override
  public String getId() {
    return "wordCountAdapter";
  }

  @Override
  public FeedbackHubAdapter create(WordCounterSettings settings) {
    Integer target = settings.getTarget();
    if (target == null || target <= 0) {
      throw new FeedbackHubException("settings must provide a target", WordCounterFeedbackHubErrorCode.TARGET_NOT_SET);
    }

    return new WordCounterFeedbackAdapter(settings);
  }
}
