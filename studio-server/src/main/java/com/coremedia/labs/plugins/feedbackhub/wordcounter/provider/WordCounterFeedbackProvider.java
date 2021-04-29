package com.coremedia.labs.plugins.feedbackhub.wordcounter.provider;

import com.coremedia.labs.plugins.feedbackhub.wordcounter.WordCounterSettings;
import com.coremedia.labs.plugins.feedbackhub.wordcounter.items.CursiveTextFeedbackItem;
import com.coremedia.cap.content.Content;
import com.coremedia.feedbackhub.FeedbackItemDefaultCollections;
import com.coremedia.feedbackhub.adapter.FeedbackContext;
import com.coremedia.feedbackhub.items.FeedbackItem;
import com.coremedia.feedbackhub.items.FeedbackItemFactory;
import com.coremedia.feedbackhub.items.FeedbackLinkFeedbackItem;
import com.coremedia.feedbackhub.items.GaugeFeedbackItem;
import com.coremedia.feedbackhub.items.LabelFeedbackItem;
import com.coremedia.feedbackhub.items.PercentageBarFeedbackItem;
import com.coremedia.feedbackhub.items.RatingBarFeedbackItem;
import com.coremedia.feedbackhub.items.ScoreBarFeedbackItem;
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
    long wordCount = Stream.of(plainText.split(" ")).filter(f -> !exclusions.contains(f)).count();
    long percentage = wordCount * 100 / settings.getTarget();

    //create the external link button for the upper right corner
    FeedbackLinkFeedbackItem feedbackLink = FeedbackItemFactory.createFeedbackLink("https://github.com/CoreMedia/feedback-hub-adapter-tutorial");

    //create feedback
    GaugeFeedbackItem gauge = GaugeFeedbackItem.builder()
            .withValue(percentage)
            .withTitle("My Word Counter")
            .withCollection("tab1")
            .build();

    //labels can be rendered bold and be therefore used as headline too
    LabelFeedbackItem label = LabelFeedbackItem.builder()
            .withBold()
            .withLabel("You have entered " + wordCount + " of " + settings.getTarget() + " words.")
            .build();

    //since we have a target value, we can also render a percentage bar
    PercentageBarFeedbackItem percentageScore = PercentageBarFeedbackItem.builder()
            .withValue(percentage)
            .withLabel("Percentage Score")
            .withCollection("tab1")
            .build();

    //a regular score bar with the target value as end value
    ScoreBarFeedbackItem scoreBar = ScoreBarFeedbackItem.builder()
            .withValue(wordCount, settings.getTarget())
            .withLabel("Word Count")
            .withCollection("tab2")
            .build();

    //we use the percentage as rating value here, just to show how this bar is used
    RatingBarFeedbackItem ratingBar = RatingBarFeedbackItem.builder()
            .withValue(Math.round(percentage) /10, 10)
            .withTitle("Rated Content")
            .withHelp("Put some additional help text <b>here</b>!")
            .withLabel("Rated Word Count")
            .withMinLabel("0%")
            .withMaxLabel("100%")
            .withReversedColors()
            .withCollection("tab2")
            .build();

    CursiveTextFeedbackItem myItem = new CursiveTextFeedbackItem(FeedbackItemDefaultCollections.header.name(),
            "The number of words is counted here");

    //the items are rendered in the order they are passed here (except the feedbackLink which is always rendered at the top)
    return CompletableFuture.completedFuture(Arrays.asList(feedbackLink, myItem, gauge, label, percentageScore, scoreBar, ratingBar));
  }

}
