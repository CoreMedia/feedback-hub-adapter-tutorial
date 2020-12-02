package com.coremedia.blueprint.studio.feedback.wordcounter;

import com.coremedia.blueprint.studio.feedback.wordcounter.adapter.WordCounterFeedbackAdapterFactory;
import com.coremedia.blueprint.studio.feedback.wordcounter.provider.WordCounterFeedbackProviderFactory;
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
