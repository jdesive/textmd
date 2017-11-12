/*
 * Copyright (C) 2017  TextMd
 *
 * This file is part of TextMd.
 *
 * TextMd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.markdown;

import com.atlassian.renderer.wysiwyg.converter.DefaultWysiwygConverter;
import com.desive.utilities.Utils;
import com.google.common.collect.Lists;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.jira.converter.JiraConverterExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterExtension;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 Created by Jack DeSive on 10/7/2017 at 9:30 PM
*/
public class MarkdownParser {

    private final Logger logger = LoggerFactory.getLogger(MarkdownParser.class);

    private DataKey<Iterable<Extension>> EXTENSIONS = Parser.EXTENSIONS;

    public final MutableDataHolder options = new MutableDataSet()
            .set(Parser.REFERENCES_KEEP, KeepType.LAST)
            .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
            .set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, false)

            // Github Parser options
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .set(Parser.LISTS_END_ON_DOUBLE_BLANK, true)
            .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false)
            .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
            .set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true)
            .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false)
            .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
            .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, false)
            .set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false)
            .set(HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES, true)

            // Html Render
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, false)
            .set(HtmlRenderer.SUPPRESS_INLINE_HTML, false)
            .set(HtmlRenderer.SOFT_BREAK, "")
            .set(HtmlRenderer.HARD_BREAK, "<br />\n")
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "prettyprint lang-")
            .set(HtmlRenderer.RENDER_HEADER_ID, true)

            // Misc. Extensions
            .set(AbbreviationExtension.ABBREVIATIONS_KEEP, KeepType.LAST)
            .set(TaskListExtension.ITEM_DONE_MARKER, "<span class=\"taskitem\">X</span>")
            .set(TaskListExtension.ITEM_NOT_DONE_MARKER, "<span class=\"taskitem\">O</span>")
            .set(WikiLinkExtension.DISABLE_RENDERING, true)
            .set(AnchorLinkExtension.ANCHORLINKS_SET_ID, true)
            .set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, "anchor")
            .set(AnchorLinkExtension.ANCHORLINKS_SET_NAME, true)
            .set(AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX, "<span class=\"octicon octicon-link\"></span>")

            // Table Extensions
            .set(TablesExtension.COLUMN_SPANS, false)
            .set(TablesExtension.MIN_HEADER_ROWS, 1)
            .set(TablesExtension.MAX_HEADER_ROWS, 1)
            .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
            .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
            .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)

            // Emoji Extensions
            .set(EmojiExtension.ROOT_IMAGE_PATH, "assets/graphics/emojis/")

            .set(EXTENSIONS, Lists.newArrayList(Arrays.asList(
                    EscapedCharacterExtension.create(),
                    TablesExtension.create(),

                    // Temp
                    AbbreviationExtension.create(),
                    AnchorLinkExtension.create(),
                    TaskListExtension.create(),
                    EmojiExtension.create(),
                    WikiLinkExtension.create(),
                    StrikethroughExtension.create()
            )));

    private MutableDataHolder jiraOptions = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(JiraConverterExtension.create()));

    private MutableDataHolder youtrackOptions = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(YouTrackConverterExtension.create()));

/*            (TablesExtension.create(), EscapedCharacterExtension.create(),
            AbbreviationExtension.create(), AutolinkExtension.create(), TaskListExtension.create(),
            WikiLinkExtension.create(), StrikethroughExtension.create(), AnchorLinkExtension.create())*/

    private Parser markdownParser = Parser.builder(options).build();
    private HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();
    private DefaultWysiwygConverter confluenceConverter = new DefaultWysiwygConverter();

    public void setExtensions(List<Extension> extensions) {
        options.remove(EXTENSIONS);
        options.set(EXTENSIONS, extensions);
        logger.debug("Resetting markdown parser extensions: {}", options.get(Parser.EXTENSIONS).toString());
    }

    public List<Extension> getExtensions() {
        return (List<Extension>) options.get(Parser.EXTENSIONS);
    }

    public MutableDataHolder getOptions() {
        return options;
    }

    public void addExtension(Extension extension) {
        List<Extension> extensions = getExtensions();
        if(!containsExtension(extension)) {
            Collections.addAll(extensions, extension);
            setExtensions(extensions);
        }
    }

    private boolean containsExtension(final Extension extension) {
        final AtomicBoolean flag = new AtomicBoolean(false);
        getExtensions().forEach(ext -> {
            if(ext.getClass().getName().equals(extension.getClass().getName())){
                flag.set(true);
            }
        });
        return flag.get();
    }

    public String convertToHTML(String markdown){
        markdownParser = Parser.builder(options).build();
        return htmlRenderer.render(markdownParser.parse(markdown));
    }

    public void convertToDocx(String markdown, File file) throws Docx4JException, JAXBException {
        WordprocessingMLPackage wordMLPackage;
        wordMLPackage = WordprocessingMLPackage.createPackage();
        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
        ndp.unmarshalDefaultNumbering();
        XHTMLImporterImpl xHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
        xHTMLImporter.setHyperlinkStyle("Hyperlink");
        // Must be a properly formatted html doc.. No fragments
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                xHTMLImporter.convert(Utils.wrapWithHtmlDocType(convertToHTML(markdown)),
                        null)
        );
        wordMLPackage.save(file);
    }

    public String convertToJira(String markdown){
        markdownParser = Parser.builder(jiraOptions).build();
        return htmlRenderer.render(markdownParser.parse(markdown));
    }

    public String convertToYoutrack(String markdown){
        markdownParser = Parser.builder(youtrackOptions).build();
        return htmlRenderer.render(markdownParser.parse(markdown));
    }

    public String convertToText(String markdown){
        TextCollectingVisitor textCollectingVisitor = new TextCollectingVisitor();
        return textCollectingVisitor.collectAndGetText(markdownParser.parse(markdown));
    }

    public String markdownToConfluenceMarkup(String markdown) {
        return confluenceConverter.convertXHtmlToWikiMarkup(convertToHTML(markdown));
    }

}
