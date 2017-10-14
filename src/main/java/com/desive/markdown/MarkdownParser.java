package com.desive.markdown;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;

/*
 Created by Jack DeSive on 10/7/2017 at 9:30 PM
*/
public class MarkdownParser {

    static final MutableDataHolder options = new MutableDataSet()
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
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
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
                    WikiLinkExtension.create()));

    static Parser parser = Parser.builder(options).build();
    static HtmlRenderer renderer = HtmlRenderer.builder(options).build();


    public static String convertMarkdownToHTML(String markdown){
        return renderer.render(parser.parse(markdown));
    }

}
