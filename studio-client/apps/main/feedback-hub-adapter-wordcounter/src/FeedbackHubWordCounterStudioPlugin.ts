import CopyResourceBundleProperties from "@coremedia/studio-client.main.editor-components/configuration/CopyResourceBundleProperties";
import StudioPlugin from "@coremedia/studio-client.main.editor-components/configuration/StudioPlugin";
import FeedbackHub_properties from "@coremedia/studio-client.main.feedback-hub-editor-components/FeedbackHub_properties";
import feedbackService from "@coremedia/studio-client.main.feedback-hub-editor-components/feedbackService";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";
import resourceManager from "@jangaroo/runtime/l10n/resourceManager";
import CursiveTextFeedbackItem from "./CursiveTextFeedbackItem";
import FeedbackHubWordCounterStudioPlugin_properties from "./FeedbackHubWordCounterStudioPlugin_properties";

interface FeedbackHubWordCounterStudioPluginConfig extends Config<StudioPlugin> {
}

class FeedbackHubWordCounterStudioPlugin extends StudioPlugin {
  declare Config: FeedbackHubWordCounterStudioPluginConfig;

  constructor(config: Config<FeedbackHubWordCounterStudioPlugin> = null) {
    super((()=>{
      this.#__initialize__(config);
      return ConfigUtils.apply(Config(FeedbackHubWordCounterStudioPlugin, {

        rules: [
        ],

        configuration: [
          new CopyResourceBundleProperties({
            destination: resourceManager.getResourceBundle(null, FeedbackHub_properties),
            source: resourceManager.getResourceBundle(null, FeedbackHubWordCounterStudioPlugin_properties),
          }),
        ],

      }), config);
    })());
  }

  #__initialize__(config: Config<FeedbackHubWordCounterStudioPlugin>): void {
    feedbackService._.registerFeedbackItemPanel("cursiveText", Config(CursiveTextFeedbackItem));
  }
}

export default FeedbackHubWordCounterStudioPlugin;
