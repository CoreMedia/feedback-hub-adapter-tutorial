import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
import Config from "@jangaroo/runtime/Config";
import ConfigUtils from "@jangaroo/runtime/ConfigUtils";

interface CursiveTextFeedbackItemConfig extends Config<FeedbackItemPanel> {
}

class CursiveTextFeedbackItem extends FeedbackItemPanel {
  declare Config: CursiveTextFeedbackItemConfig;

  static override readonly xtype: string = "com.coremedia.cms.studio.feedbackhub.config.cursiveTextFeedbackItem";

  constructor(config: Config<CursiveTextFeedbackItem> = null) {
    super(ConfigUtils.apply(Config(CursiveTextFeedbackItem, {

      items: [
        Config(DisplayField, {
          ui: DisplayFieldSkin.ITALIC.getSkin(),
          value: config.feedbackItem["text"],
        }),
      ],
    }), config));
  }
}

export default CursiveTextFeedbackItem;
