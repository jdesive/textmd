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
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
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
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;

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
                    WikiLinkExtension.create(), StrikethroughExtension.create(), AnchorLinkExtension.create()));

    private static MutableDataHolder jiraOptions = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(JiraConverterExtension.create()));

    private static MutableDataHolder youtrackOptions = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(YouTrackConverterExtension.create()));

    private static Parser markdownParser = Parser.builder(options).build();
    private static HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();
    private static DefaultWysiwygConverter confluenceConverter = new DefaultWysiwygConverter();


    public static String convertMarkdownToHTML(String markdown){
        return htmlRenderer.render(markdownParser.parse(markdown));
    }

    public static void convertMarkdownToDocx(String markdown, File file) throws Docx4JException, JAXBException {
        WordprocessingMLPackage wordMLPackage;
        wordMLPackage = WordprocessingMLPackage.createPackage();
        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
        ndp.unmarshalDefaultNumbering();
        XHTMLImporterImpl xHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
        xHTMLImporter.setHyperlinkStyle("Hyperlink");
        // Must be a properly formatted html doc.. No fragments
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                xHTMLImporter.convert(Utils.wrapWithHtmlDocType(convertMarkdownToHTML(markdown)),
                        null)
        );
        wordMLPackage.save(file);
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
