# Exception Handling

No matter if you choose to implement a _FeedbackAdapter_ or 
_FeedbackProvider_, the exception handling works the same way for both types.

### Feedback Hub Error Codes

First, you need to create an `enum` which implements `FeedbackHubErrorCode`.
Inside this enum, you can declare error codes for all errors that might raise
inside your provider or adapter. We stick with one common error code in our example:  

```java
enum WordCounterFeedbackHubErrorCode implements FeedbackHubErrorCode {
  WORD_COUNTER_COMMON_ERROR_CODE
}
```

Inside our feedback calculation we then can use the error code by
throwing a new `RuntimeException` of type `FeedbackHubException`.
The exception does also take a collection of arguments as last argument.
Here we can pass additional information that is later used in the localization of 
the error message.

```java
  @Override
  public CompletionStage<Collection<FeedbackItem>> provideFeedback(FeedbackContext feedbackContext) {
    try {
      ...
    }
    catch (Exception e) {
      throw new FeedbackHubException("Error in WordCounter", WORD_COUNTER_COMMON_ERROR_CODE, 
        Arrays.asList(e.getMessage()));
    }
  }
```

### Error Localization

When thrown, `FeedbackHubExceptions` are serialized and sent to the client.
The error code is used to localize the error code with it's arguments.

Error message have the common format `<factoryId_ERROR_CODE>=...`.
In order to localize our `WORD_COUNTER_COMMON_ERROR_CODE` error, we add
the following line to the `FeedbackHubWordCounterStudioPlugin.properties` of
the `studio-client` module:

```
wordcounter_WORD_COUNTER_COMMON_ERROR_CODE = An error occured during the execution of {0}: {1}
```

Note that the message contains two placeholder, although we only did pass
the error message as argument to the exception. **When errors are localized, 
the first parameter always contains the _factoryId_ of the provider/adapter.**
