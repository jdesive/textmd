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
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.convert.html.FlexmarkHtmlParser;
import com.vladsch.flexmark.docx.converter.internal.DocxRenderer;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.jira.converter.JiraConverterExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterExtension;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.util.Arrays;

/*
 Created by Jack DeSive on 10/7/2017 at 9:30 PM
*/
public class MarkdownParser {

    public static final MutableDataHolder options = new MutableDataSet()
            .set(Parser.REFERENCES_KEEP, KeepType.LAST)
            .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
            .set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, false)

            // Github Parser options
            .set(Parser.LISTS_AUTO_LOOSE, false)
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
            .set(HtmlRenderer.SOFT_BREAK, "<br />\n")
            .set(HtmlRenderer.HARD_BREAK, "<br />\n<br />\n")
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "prettyprint lang-")
            .set(HtmlRenderer.RENDER_HEADER_ID, true)

            // Docx options
            .set(DocxRenderer.SUPPRESS_HTML, true)

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

            .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), EscapedCharacterExtension.create(),
                    AbbreviationExtension.create(), AutolinkExtension.create(), TaskListExtension.create(),
                    WikiLinkExtension.create()));

    static MutableDataHolder jiraOptions = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(JiraConverterExtension.create()));

    static MutableDataHolder youtrackOptions = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(YouTrackConverterExtension.create()));

    static Parser markdownParser = Parser.builder(options).build();
    static FlexmarkHtmlParser htmlParser = FlexmarkHtmlParser.build();
    static HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();
    static DocxRenderer docxRender = DocxRenderer.builder(options).build();
    static DefaultWysiwygConverter confluenceConverter = new DefaultWysiwygConverter();


    public static String convertMarkdownToHTML(String markdown){
        return htmlRenderer.render(markdownParser.parse(markdown));
    }

    public static WordprocessingMLPackage convertMarkdownToDocx(String markdown){
        WordprocessingMLPackage template = DocxRenderer.getDefaultTemplate();
        docxRender.render(markdownParser.parse(markdown), template);
        return template;
    }

    public static String convertMarkdownToJira(String markdown){
        return htmlRenderer.withOptions(jiraOptions).render(markdownParser.parse(markdown));
    }

    public static String convertMarkdownToYoutrack(String markdown){
        return htmlRenderer.withOptions(youtrackOptions).render(markdownParser.parse(markdown));
    }

    public static String convertMarkdownToText(String markdown){
        TextCollectingVisitor textCollectingVisitor = new TextCollectingVisitor();
        return textCollectingVisitor.collectAndGetText(markdownParser.parse(markdown));
    }

    public static String markdownToConfluenceMarkup(String markdown) {
        return confluenceConverter.convertXHtmlToWikiMarkup(convertMarkdownToHTML(markdown));
    }

}
