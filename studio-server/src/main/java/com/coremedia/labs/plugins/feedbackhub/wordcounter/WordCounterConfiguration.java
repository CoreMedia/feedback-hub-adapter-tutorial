package com.coremedia.labs.plugins.feedbackhub.wordcounter;

import com.coremedia.labs.plugins.feedbackhub.wordcounter.adapter.WordCounterFeedbackAdapterFactory;
import com.coremedia.labs.plugins.feedbackhub.wordcounter.provider.WordCounterFeedbackProviderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordCounterConfiguration {

  @Bean
  public WordCounterFeedbackProviderFactory wordCounterFeedbackProviderFactory() {
    return new WordCounterFeedbackProviderFactory();
  }

  @Bean
  public WordCounterFeedbackAdapterFactory wordCounterFeedbackAdapterFactory() {
    return new WordCounterFeedbackAdapterFactory();
  }
}
