/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  extName: "com.coremedia.labs.plugins__studio-client.feedback-hub-adapter-wordcounter",
  extNamespace: "com.coremedia.labs.plugins.feedbackhub.wordcounter",
  sencha: {
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.feedbackhub.wordcounter.FeedbackHubWordCounterStudioPlugin",
        name: "Word Counter Plugin",
      },
    ],
  },
};
