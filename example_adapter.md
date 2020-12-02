# Implementing a FeedbackAdapter 

In this tutorial we are gonna explain which steps are are required to 
implement you own `FeedbackAdapter`. The adapter simply counts the amount of words
you have used inside your articles `detailText` field. 
For this we will implement the `TextFeedbackHubAdapter` which already extracts
text from content. 


![Feedback Rendering](images/feedback_example_2.png "Feedback Rendering")

You can either clone this project and rename/refactor
the corresponding classes and methods or you can start from scratch with your own modules.
In any case, we assume that the `studio` and `studio-lib` modules have been setup properly.

## 1. Spring Configuration

Assuming you haven't already done it, create a `resource` folder inside your
`studio-lib` maven module with the corresponding `META-INF/spring.factories` file
which points to you Spring configuration class. In our example, this class is 
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

The Spring configuration only created the `WordCounterFeedbackAdapterFactory`
which is responsible for creating the actual `FeedbackAdapter` instance.

## 2. FeedbackHubAdapterFactory Implementation

Next, we take a closer look on the `WordCounterFeedbackAdapterFactory`.

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
the implementation of the methods 

- `String getId()`: this methods return the unique id of this adapter and is used
to match the content based configuration against the actual implementation
- `FeedbackHubAdapter create(T settings)`: this factory method creates the actual adapter instance.
The settings interface that is passed here contains additional fields that may have been set
inside the `settings` struct of the adapter configuration. Usually credentials are passed
to the adapter this way. In our example, we use this interface to pass additional 
configuration parameters:

```java
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
```


## 3. FeedbackAdapter Implementation

We can now care about the actual feedback implementation.

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


We use the `TextFeedbackHubAdapter` which extracts the markup as plaintext from the content for us.
We then split the plaintext using whitespaces, but also
exclude the words that are inside the ignore list that has been passed 
as settings value.

The settings value `target` determines
the amount of words our article should have and therefore can be used to 
calculate a percentage value how far the writing is progressed.


## 4. Configuration

We finally have to create a new `CMSettings` document
in the site local or global "Feedback Hub" configuration folder. Below, you see
an example of a matching configuration created for the the Blueprint Site "Chef Corp.". 

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

Note that in this implementation, the source property that is used to 
extract the markup is not hard coded as in the `FeedbackProvider` example,
but can be set via settings parameter `sourceProperties`.

## 4. Feedback Grouping

The Feedback Hub supports the tabbed rendering of `FeedbackItems`.
All you have to do is use the `withCollection` method which is supported 
by most `FeedbackItemBuilders`.
For example, we could render the percentage based `FeedbackItems` on one tab
and the count based scores on another by adding `.withCollection("tab1")` and
`.withCollection("tab2")` to the builders. The result would look like this:

## 5. Configuration

We finally have to create a new `CMSettings` document
in the site local or global "Feedback Hub" configuration folder. Below, you see
an example of a matching configuration created for the the Blueprint Site "Chef Corp.". 

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

## 6. Localization

The localization of the Feedback Hub does not differ between
implementations of `FeedbackAdapter` and `FeedbackProvider`. 
It is described in detail in section **[Localization](feedback_localization.md)**.

## 7. Exception Handling

The overall exception handling inside the Feedback Hub does not differ between
implementations of `FeedbackAdapter` and `FeedbackProvider`. 
It is described in detail in section **[Exception Handling](exception_handling.md)**.

## 8. Custom FeedbackItems

If the existing `FeedbackItems` are not sufficient to render the desired feedback,
we can implement custom `FeedbackItems` with custom components.
An example for this is described in section **[Custom FeedbackItems](custom_feedback.md)**.
