package com.coremedia.blueprint.studio.feedback.wordcounter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordCounterConfiguration {

  @Bean
  public WordCounterFeedbackProviderFactory wordCounterFeedbackProviderFactory() {
    return new WordCounterFeedbackProviderFactory();
  }
}
