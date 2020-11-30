![CoreMedia Labs Logo](https://documentation.coremedia.com/badges/banner_coremedia_labs_wide.png "CoreMedia Labs Logo")

![CoreMedia Content Cloud Version](https://img.shields.io/static/v1?message=2101&label=CoreMedia%20Content%20Cloud&style=for-the-badge&labelColor=666666&color=672779 
"This badge shows the CoreMedia version this project is compatible with. 
Please read the versioning section of the project to see what other CoreMedia versions are supported and how to find them."
)
![Status](https://img.shields.io/static/v1?message=active&label=Status&style=for-the-badge&labelColor=666666&color=2FAC66 
"The status badge describes if the project is maintained. Possible values are active and inactive. 
If a project is inactive it means that the development has been discontinued and won't support future CoreMedia versions."
)


# Displaying Feedback with the CoreMedia Feedback Hub 

## Overview 

In this tutorial we are going to learn about how to use the CoreMedia Feedback Hub API.
It explains step-by-step how to set up a new CoreMedia Extension for the Feedback Hub
and how to build a new Feedback Hub adapter with the corresponding classes.

## Versioning

To find out which CoreMedia versions are supported by this project, 
please take look at the releases section or on the existing branches. 
To find the matching version of your CoreMedia system, please checkout the branch 
with the corresponding name. For example, 
if your CoreMedia version is 2001.2, checkout the branch 2001.2.


## Project Setup

The project is an extension for the CoreMedia Blueprint workspace.

- From the project's root folder, clone this repository as a submodule of the extensions folder. 
Make sure to use the branch name that matches your workspace version. 
```
git submodule add  -b 1907.1 https://github.com/CoreMedia/feedback-hub-adapter-tutorial modules/extensions/feedback-hub-adapter-tutorial
```

- Use the extension tool in the root folder of the project to link the modules to your workspace.
 ```
mvn -f workspace-configuration/extensions com.coremedia.maven:extensions-maven-plugin:LATEST:sync -Denable=feedback-hub-adapter-tutorial
```

- Rebuild the workspace

## Implementation Steps

In the next section we are gonna explain which steps are are required to 
implement you own Feedback Adapter. This adapter simply counts the amount of words
you have used inside your articles `detailText` field. 

You can either clone this project and rename/refactor
the corresponding classes and methods or you can start from scratch with your own modules.
In any case, we assume that the `studio` and `studio-lib` modules have been setup properly.

#### 1. Spring Configuration

Assuming you haven't already done it, create a `resource` folder inside your
`studio-lib` maven module with the corresponding `META-INF/spring.factories` file
which points to you Spring configuration class. In our example, this class is 
named `WordCounterConfiguration` and looks like this:

```java
@Configuration
public class WordCounterConfiguration {

  @Bean
  public WordCounterFeedbackProviderFactory wordCounterFeedbackProviderFactory() {
    return new WordCounterFeedbackProviderFactory();
  }
}
``` 

The Spring configuration only created the `WordCounterFeedbackProviderFactory`
which is responsible for creating the actual `FeedbackProvider` instance.

#### 2. FeedbackProviderFactory Implementation

Next, we take a closer look on the `WordCounterFeedbackProviderFactory`.

```java
public class WordCounterFeedbackProviderFactory implements FeedbackProviderFactory<WordCounterSettings> {

  @Override
  public String getId() {
    return "wordcounter";
  }

  @Override
  public FeedbackProvider create(WordCounterSettings settings) {
    return new WordCounterFeedbackProvider(settings);
  }
}
```

The class implements the interface `FeedbackProviderFactory` which requires
the implementation of the methods 

- `String getId()`: this methods return the unique id of this adapter and is used
to match the content based configuration against the actual implementation
- `FeedbackProvider create(T settings)`: this factory method creates the actual provider instance.
The settings interface that is passed here contains additional fields that may have been set
inside the `settings` struct of the adapter configuration. Usually credentials are passed
to the provider this way. In our example, we use this interface to pass additional 
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


#### 3. FeedbackProvider Implementation

We now can care about the actual feedback implementation.



## CoreMedia Labs

Welcome to [CoreMedia Labs](https://blog.coremedia.com/labs/)! This repository
is part of a platform for developers who want to have a look under the hood or
get some hands-on understanding of the vast and compelling capabilities of
CoreMedia. Whatever your experience level with CoreMedia is, we've got something
for you.

Each project in our Labs platform is an extra feature to be used with CoreMedia,
including extensions, tools and 3rd party integrations. We provide some test
data and explanatory videos for non-customers and for insiders there is
open-source code and instructions on integrating the feature into your
CoreMedia workspace. 

The code we provide is meant to be example code, illustrating a set of features
that could be used to enhance your CoreMedia experience. We'd love to hear your
feedback on use-cases and further developments! If you're having problems with
our code, please refer to our issues section. If you already have a solution to 
an issue, we love to review and integrate your pull requests. 

