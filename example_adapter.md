# Implementing a FeedbackAdapter 

In this tutorial you will learn which steps are required to 
implement your own `FeedbackAdapter`. This example adapter will count the amount of words
you have used inside your articles `detailText` field. 

The implementation will use the `TextFeedbackHubAdapter` which already extracts
text from content. 

You can either clone this project and rename/refactor
the corresponding classes and methods or you can start from scratch with your own modules.
In any case, the `studio` and `studio-lib` modules must have been set up properly.

## 1. Spring Configuration

Assuming you haven't already done it, create a `resource` folder inside your
`studio-lib` maven module with the corresponding `META-INF/spring.factories` file
which points to you Spring configuration class. In the example, this class is 
named `WordCounterConfiguration` and looks like this:

```java
@Configuration
public class WordCounterConfiguration {

  @Bean
  public WordCounterFeedbackAdapterFactory wordCounterFeedbackAdapterFactory() {
    return new WordCounterFeedbackAdapterFactory();
  }
}
``` 

The Spring configuration creates the `WordCounterFeedbackAdapterFactory`
which is responsible for creating the actual `FeedbackAdapter` instance.

## 2. FeedbackHubAdapterFactory Implementation

Next, take a closer look at the `WordCounterFeedbackAdapterFactory`.

```java
public class WordCounterFeedbackAdapterFactory implements FeedbackHubAdapterFactory<WordCounterSettings> {

  @Override
  public String getId() {
    return "wordCountAdapter";
  }

  @Override
  public FeedbackHubAdapter create(WordCounterSettings settings) {
    return new WordCounterFeedbackAdapter(settings);
  }
}
```

The class implements the interface `FeedbackHubAdapterFactory` which requires
the implementation of the methods:

- `String getId()`: this method returns the unique ID of this adapter and is used
to match the content based configuration against the actual implementation.
- `FeedbackHubAdapter create(T settings)`: this factory method creates the actual adapter instance.
The settings interface that is passed here, contains additional fields that may have been set
inside the `settings` struct of the adapter configuration. Usually credentials are passed
to the adapter this way. 
In the example, this interface is used to pass additional configuration parameters:

```java
public interface WordCounterSettings {

  /**
   * Returns a comma separated list of words that are excluded from the word count.
   */
  String getIgnoreList();

  /**
   * Returns the amount of words the text should have.
   */
  Integer getTarget();
}
```


## 3. FeedbackAdapter Implementation

Have a look at the actual feedback implementation:

```java
@DefaultAnnotation(NonNull.class)
public class WordCounterFeedbackAdapter implements TextFeedbackHubAdapter {
  private final WordCounterSettings settings;

  WordCounterFeedbackAdapter(WordCounterSettings settings) {
    this.settings = settings;
  }

  @Override
  public CompletionStage<Collection<FeedbackItem>> analyzeText(FeedbackContext context, Map<String, String> textProperties, @Nullable Locale locale) {
    String plainText = textProperties.values().stream().collect(Collectors.joining(" "));
    List<String> exclusions = Arrays.asList(settings.getIgnoreList().split(","));

    //count words, exclude words from the ignore list
    long wordCount = Stream.of(plainText.split(" ")).filter(f -> !exclusions.contains(f)).count();
    long percentage = wordCount * 100 / settings.getTarget();

    //create the external link button for the upper right corner
    FeedbackLinkFeedbackItem feedbackLink = FeedbackItemFactory.createFeedbackLink("https://github.com/CoreMedia/feedback-hub-adapter-tutorial");

    //create feedback
    GaugeFeedbackItem gauge = GaugeFeedbackItem.builder()
            .withValue(percentage)
            .withTitle("My Word Counter")
            .build();

    //check {@link WordCounterFeedbackProvider} for more examples

    //the items are rendered in the order they are passed here (except the feedbackLink which is always rendered at the top)
    return CompletableFuture.completedFuture(Arrays.asList(feedbackLink, gauge));
  }
}
```


The `TextFeedbackHubAdapter` extracts the markup as plaintext from the content for you.
Then the plaintext is splitted using whitespaces, but also
excludes the words that are inside the ignore list that has been passed 
as settings value.

The settings value `target` determines
the amount of words your article should have and therefore can be used to 
calculate a percentage value of how far the writing is progressed.

Note that in this implementation, the source property that is used to 
extract the markup from, is not a hard coded value as in the `FeedbackProvider` example,
but can be configured via the settings parameter `sourceProperties`.


## 4. Configuration

You finally have to create a new `CMSettings` document
within a site or within the global "Feedback Hub" configuration folder. Below, you see
an example configuration created for the Blueprint Site "Chef Corp.". 

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<CMSettings folder="/Sites/Chef Corp./United States/English/Options/Settings/Feedback Hub" name="Wordcounter Adapter" xmlns:cmexport="http://www.coremedia.com/2012/cmexport">
  <externalRefId></externalRefId>
  <locale></locale>
  <master/>
  <settings><Struct xmlns="http://www.coremedia.com/2008/struct" xmlns:xlink="http://www.w3.org/1999/xlink">
    <StringProperty Name="observedProperties">detailText</StringProperty>
    <StringProperty Name="factoryId">wordCountAdapter</StringProperty>
    <StringProperty Name="groupId">wordCountAdapter</StringProperty>
    <StringProperty Name="contentType">CMArticle</StringProperty>
    <BooleanProperty Name="enabled">true</BooleanProperty>
    <StructProperty Name="settings">
      <Struct>
        <StringProperty Name="sourceProperties">detailText</StringProperty>
        <StringProperty Name="ignoreList">and,with</StringProperty>
        <IntProperty Name="target">1000</IntProperty>
      </Struct>
    </StructProperty>
  </Struct></settings>
  <identifier></identifier>
</CMSettings>
```

If everything is configured properly, the Feedback Hub window will have
an additional tab with your Feedback Hub adapter:

![Feedback Rendering](images/feedback_example_2.png "Feedback Rendering")

## 5. Localization

The localization of the Feedback Hub does not differ between
implementations of `FeedbackAdapter` and `FeedbackProvider`. 
It is described in detail in section **[Localization](feedback_localization.md)**.

## 6. Exception Handling

The overall exception handling inside the Feedback Hub does not differ between
implementations of `FeedbackAdapter` and `FeedbackProvider`. 
It is described in detail in section **[Error Handling](error_handling.md)**.

## 7. Custom FeedbackItems

If the existing `FeedbackItems` are not sufficient to render the desired feedback,
you can implement custom `FeedbackItems`.
An example for this is described in section **[Custom FeedbackItems](custom_feedback.md)**.
