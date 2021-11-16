import CoreIcons_properties from "@coremedia/studio-client.core-icons/CoreIcons_properties";

/**
 * Interface values for ResourceBundle "FeedbackHubWordCounterStudioPlugin".
 * @see FeedbackHubWordCounterStudioPlugin_properties#INSTANCE
 */
interface FeedbackHubWordCounterStudioPlugin_properties {

/**
 * Provider Example
 *#######################################################################################################################
 */
  wordCountProvider_iconCls: string;
  wordCountProvider_title: string;
  wordCountProvider_tooltip: string;
  wordCountProvider_ariaLabel: string;
  wordCountProvider_tab1_tab_title: string;
  wordCountProvider_tab1_tab_tooltip: string;
  wordCountProvider_tab1_tab_ariaLabel: string;
  wordCountProvider_tab2_tab_title: string;
  wordCountProvider_tab2_tab_tooltip: string;
  wordCountProvider_tab2_tab_ariaLabel: string;
  wordCountProvider_WORD_COUNTER_COMMON_ERROR_CODE: string;
  wordCountProvider_TARGET_NOT_SET: string;
/**
 * Adapter
 *#######################################################################################################################
 */
  wordCountAdapter_iconCls: string;
  wordCountAdapter_title: string;
  wordCountAdapter_tooltip: string;
  wordCountAdapter_ariaLabel: string;
  wordCountAdapter_WORD_COUNTER_COMMON_ERROR_CODE: string;
  wordCountAdapter_TARGET_NOT_SET: string;
}

/**
 * Singleton for the current user Locale's instance of ResourceBundle "FeedbackHubWordCounterStudioPlugin".
 * @see FeedbackHubWordCounterStudioPlugin_properties
 */
const FeedbackHubWordCounterStudioPlugin_properties: FeedbackHubWordCounterStudioPlugin_properties = {
  wordCountProvider_iconCls: CoreIcons_properties.pencil,
  wordCountProvider_title: "Word Counter",
  wordCountProvider_tooltip: "See how many words you have written",
  wordCountProvider_ariaLabel: "Word Counter",
  wordCountProvider_tab1_tab_title: "Percentage",
  wordCountProvider_tab1_tab_tooltip: "Percentage Values",
  wordCountProvider_tab1_tab_ariaLabel: "Percentage Values",
  wordCountProvider_tab2_tab_title: "Count",
  wordCountProvider_tab2_tab_tooltip: "Counted Values",
  wordCountProvider_tab2_tab_ariaLabel: "Counted Values",
  wordCountProvider_WORD_COUNTER_COMMON_ERROR_CODE: "An error occured during the execution of {0}: {1}",
  wordCountProvider_TARGET_NOT_SET: "'target' property not set for adapter {0}",
  wordCountAdapter_iconCls: CoreIcons_properties.pencil,
  wordCountAdapter_title: "Word Counter",
  wordCountAdapter_tooltip: "See how many words you have written",
  wordCountAdapter_ariaLabel: "Word Counter",
  wordCountAdapter_WORD_COUNTER_COMMON_ERROR_CODE: "An error occured during the execution of {0}: {1}",
  wordCountAdapter_TARGET_NOT_SET: "'target' property not set for adapter {0}",
};

export default FeedbackHubWordCounterStudioPlugin_properties;
