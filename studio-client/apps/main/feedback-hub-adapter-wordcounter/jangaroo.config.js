const { jangarooConfig } = require("@jangaroo/core");

module.exports = {
  type: "code",
  sencha: {
    name: "com.coremedia.labs.plugins__studio-client.feedback-hub-adapter-wordcounter",
    namespace: "com.coremedia.labs.plugins.feedbackhub.wordcounter",
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.feedbackhub.wordcounter.FeedbackHubWordCounterStudioPlugin",
        name: "FeedbackHub WordCounter",
      },
    ],
  },
};
