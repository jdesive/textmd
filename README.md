# TextMd Markdown Editor
A quick and simple markdown editor written in Java with a dark theme to reduce eye strain. TextMd also supports exporting to
multiple different file types, see *Markdown Export Types* for more info.

## Features
* Simple Markdown highlighting (a little buggy...)
* Tab view for opening multiple files
* Open from url
* Export Markdown to different file types
* Split view with live rendering 
* Hotkeys
* Source-code snippet formatter 

![Main Page](https://i.imgur.com/Qfr9PoP.png)

## Markdown Export Types
* Docx
* PDF or PDF/CSS (With editors style)
* HTML or HTML/CSS (With editors style)
* Plain Text
* JIRA Formatted Text
* Confluence Markup Text
* YouTrack Formatted Text

## Dependencies
* [Flexmark](https://github.com/vsch/flexmark-java) 0.27.0
* [RichTextFX](https://github.com/FXMisc/RichTextFX) v0.7-M3 
* [Code-Prettify](https://github.com/google/code-prettify) 2013-03-04^
* [Maven](https://maven.apache.org/download.cgi) 3.5.2^
* [Git](https://git-scm.com/downloads) 2.14.3^
* [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8

## How to install
Must have *Java*, *Maven* and *Git* installed to continue.

* Clone this repo to your computer
* Run `mvn package` from the root directory
* The final jar is located at `target/textmd-jar-with-dependencies.jar`

TextMd uses the [Courier Primal](https://github.com/localredhead/courier-primal) font by [@localredhead](https://github.com/localredhead) and is mostly styled after [Intellij](https://www.jetbrains.com/idea/)'s Darcula theme.