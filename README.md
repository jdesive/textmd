# TextMd Markdown Editor
A Markdown editor written in Java with a mix-match of my favorite features from different editors. 

TextMd uses a rich text editor for editing your Markdown documents. This provides you with markdown highlighting while you write and a live rendering 
of your document as you type. The editor supports many extensions to help support any Markdown variate you may need. 

There are many export types supported including PDF, Microsoft Word, and HTML. ( [Full list](#markdown-export-types) ) Please note that not all export types support extensions.

## Features
* Simple Markdown syntax highlighting
* Tabular view
* Open/Import from url or file
* Export Markdown to multiple file types
* Split view with live HTML rendering 
* Source-code snippet formatter ( [Code-Prettify](https://github.com/google/code-prettify) )

![Main Page](https://i.imgur.com/jk2hxeo.png)

## Markdown Export Types
* Microsoft Word Document (Docx)
* PDF or PDF/CSS (With editors style)
* HTML or HTML/CSS (With editors style)
* Plain Text
* JIRA & Confluence Formatted Text
* YouTrack Formatted Text

## How to install

### Dependencies
* [Maven](https://maven.apache.org/download.cgi) 3.5.2^
  * [Flexmark](https://github.com/vsch/flexmark-java) 0.27.0
  * [RichTextFX](https://github.com/FXMisc/RichTextFX) v0.7-M3 
* [Git](https://git-scm.com/downloads) 2.14.3^
* [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8

### Source
Must have *Java*, *Maven* and *Git* installed to continue.

* Clone this repo to your computer
* Run `mvn package` from the root directory
* The final jar is located at `target/textmd-jar-with-dependencies.jar`

### Fonts
TextMd uses the [Courier Primal](https://github.com/localredhead/courier-primal) font by [@localredhead](https://github.com/localredhead).

Alternatively you can use the 'Courier Regular' font installed by default. You can change this setting under 
Help -> Settings -> General -> Use Courier Primal

This will download and load the fonts at runtime. You can also manually install the fonts if you don't want it to load at runtime.  

## Author
*  JDeSive ([GitHub](http://github.com/jdesive) | [LinkedIn](https://www.linkedin.com/in/jack-desive-7aa819149/) | [Donate](https://paypal.me/Jdesive/5))