# Exception Handling

No matter if you choose to implement a `FeedbackAdapter` or 
`FeedbackProvider`, the exception handling works the same way for both types.

### Feedback Hub Error Codes

First, you need to create an `enum` class which implements the interface `FeedbackHubErrorCode`.
Inside this `enum`, you can declare error codes for all errors that might raise
inside your provider or adapter. For example, you could declare one common error code:  

```java
enum WordCounterFeedbackHubErrorCode implements FeedbackHubErrorCode {
  WORD_COUNTER_COMMON_ERROR_CODE
}
```

### Error Codes for Adapters and Providers

Inside your feedback calculation you then can use error codes by
throwing a new `RuntimeException` of type `FeedbackHubException`.
The exception does also take a collection of arguments as last argument.
Here, you can pass additional information that is later used in the localization of 
the error message. For example you could wrap your provider implementation
into one big `try/catch` block:

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
You can also use this to show configuration errors to the user.

### Error Codes for Configuration Errors

A good practice is to use `FeedbackHubException` to validate the
configuration before creating the actual adapter or provider. This allows you
to give a hint to the user, that the configuration is not set up properly. 
The best way of doing this, is to check
the corresponding settings implementation inside the `FeedbackProviderFactory`
or `FeedbackHubAdapterFactory` class. 

For the word counter example, you can check if a `target` value has been 
configured since otherwise, the graph calculation will fail:

```java
@Override
public FeedbackProvider create(WordCounterSettings settings) {
    Integer target = settings.getTarget();
    if (target == null || target <= 0) {
      throw new FeedbackHubException("settings must provide a target", 
        WordCounterFeedbackHubErrorCode.TARGET_NOT_SET);
    }
    
    return new WordCounterFeedbackProvider(settings);
}
```

### Error Localization

When thrown, `FeedbackHubExceptions` are serialized and sent to the client.
The error code is used to localize the error code with its arguments.

Error messages have the format `<factoryId_ERROR_CODE>=...`.
In order to localize your `WORD_COUNTER_COMMON_ERROR_CODE` and `TARGET_NOT_SET` error, add
the following line to the `FeedbackHubWordCounterStudioPlugin.properties` of
the `studio-client` module:

```
wordCountProvider_WORD_COUNTER_COMMON_ERROR_CODE = An error occured during the execution of {0}: {1}
wordCountProvider_TARGET_NOT_SET = 'target' property not set for adapter {0}
```

Note that the first message contains two placeholders, although you only did pass
the error message as argument to the exception. **When errors are localized, 
the first parameter always contains the _factoryId_ of the provider/adapter.**
