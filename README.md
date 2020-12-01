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

## How To Read This Tutorial

There are two possibilities to implement your Feedback Hub extension, depending
on your use case. This tutorial covers both cases in separate sections: 

**FeedbackHubAdapter**
 
The Feedback Hub has predefined _FeedbackHubAdapters_. They offer some prefabrication
to make the integration of similar services easier. One example for this
is the _BlobKeywordsFeedbackHubAdapter_ which already gives us the blob the keywords 
should be extracted from. Please check the documentation for the list of existing
_FeedbackHubAdapters_. 


Tutorial Link: **[Implementing a FeedbackAdapter](example_adapter.md)**



**FeedbackProvider**

The _FeedbackProvider_ interface is the general interface to implement for any
kind of feedback. It does not offer any kind of prefabrication and can be used
if none of the existing _FeedbackHubAdapters_ are applicable for your usecase.

Tutorial Link: **[Implementing a FeedbackProvider](example_provider.md)**

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

