# TextMd Markdown Editor
A quick and simple markdown editor written in Java with a dark theme to reduce eye strain. 

## Features
* Simple Markdown highlighting (a little buggy...)
* Tab view for opening multiple files
* Open from url
* Export Markdown to HTML
* Split view with live rendering 
* Hotkeys

## Maven Dependencies
* [Flexmark](https://github.com/vsch/flexmark-java) 0.27.0
* [RichTextFX](https://github.com/FXMisc/RichTextFX) v0.7-M3 
* [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8

## How to install
* Clone this repo to your computer
* Run `mvn package` from the root directory
* The final jar is located at `target/textmd-X.X.X-jar-with-dependencies.jar`

TextMd uses the [Courier Primal](https://github.com/localredhead/courier-primal) font by [@localredhead](https://github.com/localredhead) and is mostly styled after [Intellij](https://www.jetbrains.com/idea/)'s Darcula theme.