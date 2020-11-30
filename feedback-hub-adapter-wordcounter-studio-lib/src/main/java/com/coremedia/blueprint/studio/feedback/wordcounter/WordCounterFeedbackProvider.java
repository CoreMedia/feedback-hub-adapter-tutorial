package com.coremedia.blueprint.studio.feedback.wordcounter;

import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.feedbackhub.items.GaugeFeedbackItem;
import com.coremedia.feedbackhub.items.PercentageBarFeedbackItem;
import com.coremedia.feedbackhub.provider.FeedbackProvider;
import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupUtil;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 *
 */
@DefaultAnnotation(NonNull.class)
public class WordCounterFeedbackProvider implements FeedbackProvider {
  private final WordCounterSettings settings;

  WordCounterFeedbackProvider(WordCounterSettings settings) {
    this.settings = settings;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> provideFeedback(FeedbackContext feedbackContext) {
    Content content = (Content) feedbackContext.getEntity();

    //read text
    Markup markup = content.getMarkup("detailText");
    String plainText = MarkupUtil.asPlainText(markup);

    //calculate values
    List<String> exclusions = Arrays.asList(settings.getIgnoreList().split(","));
    long wordCount = Stream.of(plainText.split(" ")).filter(exclusions::contains).count();
    long percentage = wordCount * 100 / settings.getTarget();

    //create feedback
    GaugeFeedbackItem gauge = GaugeFeedbackItem.builder()
            .withValue(percentage, settings.getTarget())
            .withGaugeTitle("My Word Counter")
            .build();

    PercentageBarFeedbackItem scoreBar = PercentageBarFeedbackItem.builder()
            .withValue(percentage)
            .withLabel("Percentage Score")
            .build();

    return CompletableFuture.completedFuture(Arrays.asList(gauge, scoreBar));
  }

}
