# Custom FeedbackItems

This sections describes how to implement a custom `FeedbackItem`.
To keep the example simple, we create a new `FeedbackItem` that allows us 
to render text with a recursive font.

## Java Implementation

All we have to do is implement the interface `FeedbackItem` for this:

```java
public class RecursiveTextFeedbackItem implements FeedbackItem {

  private String text;

  public RecursiveTextFeedbackItem(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String getType() {
    return "recursiveText";
  }
}
```

The interface does not require the implementation of any method, but we
override the `getType` method to have a simpler type name than the actual class name.

Within our provider or adapter, we can create a new `RecursiveTextFeedbackItem` 
with the text we wont to render:

```java
RecursiveTextFeedbackItem myItem = new RecursiveTextFeedbackItem("Hello World!");
```

## AS/MXML Implementation

On the client site, we have to implement the component that should be rendered
for this new `FeedbackItem` type.

In a first step, we have to implement a sub-component of class `FeedbackItemPanel`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<feedbackhub:FeedbackItemPanel xmlns:fx="http://ns.adobe.com/mxml/2009"
                               xmlns="exml:ext.config"
                               xmlns:exml="http://www.jangaroo.net/exml/0.8"
                               xmlns:feedbackhub="exml:com.coremedia.cms.studio.feedbackhub.config">
  <fx:Metadata>
    [ResourceBundle('com.coremedia.cms.studio.feedbackhub.FeedbackHub')]
  </fx:Metadata>
  <fx:Script><![CDATA[
    import com.coremedia.ui.skins.DisplayFieldSkin;

    public static const xtype:String = "com.coremedia.cms.studio.feedbackhub.config.recursiveTextFeedbackItem";

    private var config:RecursiveTextFeedbackItem;

    public native function RecursiveTextFeedbackItem(config:RecursiveTextFeedbackItem = null);
    ]]></fx:Script>

  <feedbackhub:items>
    <DisplayField ui="{DisplayFieldSkin.ITALIC.getSkin()}" value="{config.feedbackItem['text']}" />
  </feedbackhub:items>
</feedbackhub:FeedbackItemPanel>
```

Our example only contains a `DisplayField` which renders the `RecursiveTextFeedbackItem` property `text`.
The `ITALIC` `DisplayFieldSkin` is applied to the label in order to render the text in _italic_.

We finally have to tell the Feedback Hub about this panel by using the `feedbackService` instance.
For this, we use the initialization of our Studio plugin:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<editor:StudioPlugin
        xmlns:fx="http://ns.adobe.com/mxml/2009"
        xmlns:exml="http://www.jangaroo.net/exml/0.8"
        xmlns="exml:ext.config"
        xmlns:editor="exml:com.coremedia.cms.editor.sdk.config">
  <fx:Metadata>
    [ResourceBundle('com.coremedia.blueprint.studio.feedback.wordcounter.FeedbackHubWordCounterStudioPlugin')]
  </fx:Metadata>
  <fx:Script><![CDATA[
    import com.coremedia.cms.studio.feedbackhub.feedbackService;

    import mx.resources.ResourceManager;

    private var config:FeedbackHubWordCounterStudioPlugin;

    public native function FeedbackHubWordCounterStudioPlugin(config:FeedbackHubWordCounterStudioPlugin = null);

    private function __initialize__(config:FeedbackHubWordCounterStudioPlugin):void {
      feedbackService.registerFeedbackItemPanel("recursiveText", RecursiveTextFeedbackItem({}));
    }
    ]]></fx:Script>

  <editor:rules>
  ...
```

Note that the value `recursiveText` matches the `getType` methods return value of
our `RecursiveTextFeedbackItem.java`. Within our provide example, the label 
would be rendered like this:


