# Creating Plugins Using Archetypes

For the creation of new Studio client and server plugins, CoreMedia supports
archetypes to generate the overall structure for these new modules.

## Creating a Studio Client Plugin

For creating a new Studio plugin, you can use the following command:

```sh
mvn archetype:generate \
-DarchetypeGroupId=com.coremedia.maven \
-DarchetypeArtifactId=studio-client-plugin-archetype \
-DarchetypeVersion=1.0.0 \
-DgroupId=com.acme.plugins \
-DartifactId=myplugin-studio-client \
-Dversion=0.1.0-SNAPSHOT \
-Dpackage=com.acme.plugins.myplugin.studio_client \
-Dcms-version=2107.3
```

You may want to change the `groupId`, `articfactId` and `version` here according
to your plugin. The archetype generates the required files for a new Studio plugin,
including the overall plugin definition with its own resource bundle.

## Creating a Plugin for Java Applications

For creating a new Java Plugin for one of the servers, 
you can use the following command: 

```sh
mvn archetype:generate \
-DarchetypeGroupId=com.coremedia.maven \
-DarchetypeArtifactId=plugin-archetype \
-DarchetypeVersion=1.0.0 \
-DgroupId=com.acme.plugins \
-DartifactId=myplugin-studio-server \
-Dversion=0.1.0-SNAPSHOT \
-Dpackage=com.acme.plugins.myplugin.studio_server \
-Dcms-version=2104.1
```
 
You may want to change the `groupId`, `articfactId` and `version` here according
to your plugin. The archetype generates a Spring configuration class with an example
extension bean as a starting point.
 
## Creating Root pom.xml

Finally, a root `pom.xml` is needed which wraps the newly generated plugin modules.
You can use the root `pom.xml` of this project and rename the `groupId`, `artifactId`
and included `modules` for this.
