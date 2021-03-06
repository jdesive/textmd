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

package com.desive.markdown.syntax;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpan;
import org.fxmisc.richtext.model.StyleSpans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.desive.markdown.syntax.SyntaxHighlighter.Patterns.*;

/*
 Created by Jack DeSive on 11/16/2017 at 7:53 PM
*/
public class SyntaxHighlighter {

    private static final Logger logger = LoggerFactory.getLogger(SyntaxHighlighter.class);

    public SyntaxHighlighter() {

    }

    public void compute(String text, CodeArea parent) {

        Matcher matcher = getHighlightingPattern().matcher(text);

        while(matcher.find()) {

            String styleClass = getStyleClass(matcher);
            // For now lets setStyles for base style.
            parent.setStyle(matcher.start(), matcher.end(), Collections.singleton(styleClass));

            // Then we can grab the style from the document via subView and overlay
            for(HighlightStyle style : getChildStyles(styleClass, getText(matcher.start(), matcher.end(), parent))){
                StyleSpans spans = parent.getStyleSpans(matcher.start(), matcher.end()).subView(style.start, style.end).overlay(
                        StyleSpans.singleton(new StyleSpan<>(
                                Arrays.asList(style.styleClasses),
                                (style.end - style.start)
                        )),
                        (strings, strings2) -> {
                            Collection<String> coll = Streams.concat(strings.stream(), strings2.stream()).collect(Collectors.toList());
                            logger.debug(coll.toString());
                            return coll;
                        }
                );
                parent.setStyleSpans(matcher.start(), spans); // Reset styles
            }
        }

    }

    private List<HighlightStyle> getChildStyles(String styleClass, String text) {
        List<HighlightStyle> styles = Lists.newArrayList();
        Matcher match;
        switch (styleClass) {
            case "hrule":
                // Do nothing.
                break;
            case "heading1":
            case "heading2":
            case "heading3":
            case "heading4":
            case "heading5":
            case "heading6":
                match = Pattern.compile(HEADING_TAG.getPattern()).matcher(text);
                while (match.find()){
                    styles.add(new HighlightStyle(match.start(), match.end(),"heading-tag"));
                }
                break;
            case "bold":
                /*match = Pattern.compile(BOLD_TAG.getPattern()).matcher(text);
                while (match.find()){
                    styles.add(new HighlightStyle(match.start(), match.end(),"bold-tag"));
                }*/
                break;
            case "italics":
                break;
            case "strikethrough":
                break;
            case "inline-code":
                break;
            case "image":
                break;
            case "link":
                break;
            case "code":
                break;
            default:

        }
        return styles;
    }

    private String getStyleClass(Matcher matcher){
        return matcher.group("HRULE") != null ? "hrule" :
               matcher.group("HEADING1") != null ? "heading1" :
               matcher.group("HEADING2") != null ? "heading2" :
               matcher.group("HEADING3") != null ? "heading3" :
               matcher.group("HEADING4") != null ? "heading4" :
               matcher.group("HEADING5") != null ? "heading5" :
               matcher.group("HEADING6") != null ? "heading6" :
               matcher.group("BOLD") != null ? "bold" :
               matcher.group("ITALICS") != null ? "italics" :
               matcher.group("STRIKE") != null ? "strikethrough" :
               matcher.group("INLINECODE") != null ? "inline-code" :
               matcher.group("IMAGE") != null ? "image" :
               matcher.group("LINK") != null ? "link" :
               matcher.group("CODE") != null ? "code" :
               null;
    }

    private String getText(int start, int end, CodeArea area) {
        return area.getText().substring(start, end);
    }

    private Pattern getHighlightingPattern() {
        return Pattern.compile(
                "(?<HEADING1>" + HEADING_1_PATTERN + ")"
                        + "|(?<HEADING2>" + HEADING_2_PATTERN + ")"
                        + "|(?<HEADING3>" + HEADING_3_PATTERN + ")"
                        + "|(?<HEADING4>" + HEADING_4_PATTERN + ")"
                        + "|(?<HEADING5>" + HEADING_5_PATTERN + ")"
                        + "|(?<HEADING6>" + HEADING_6_PATTERN + ")"
                        + "|(?<HRULE>" + HRULE_PATTERN + ")"
                        + "|(?<BOLD>" + BOLD_PATTERN + ")"
                        + "|(?<ITALICS>" + ITALICS_PATTERN + ")"
                        + "|(?<STRIKE>" + STRIKETHROUGH_PATTERN + ")"
                        + "|(?<CODE>" + CODE_PATTERN + ")"
                        + "|(?<INLINECODE>" + INCLINE_CODE_PATTERN + ")"
                        + "|(?<IMAGE>" + IMAGE_PATTERN + ")"
                        + "|(?<LINK>" + LINK_PATTERN + ")"
                , Pattern.MULTILINE
        );
    }

    class HighlightStyle {

        String[] styleClasses;
        List<HighlightStyle> children = Lists.newArrayList();
        int start, end;

        public HighlightStyle(int start, int end, String... styleClasses) {
            this.styleClasses = styleClasses;
            this.start = start;
            this.end = end;
        }
    }


    enum Patterns {

        // General Patterns
        BRACKET(""),
        HEADING_TAG("#+"),
        BOLD_TAG("(?<BOLDTAG>\\*\\*|__)"),

        // Markdown Heading Patterns
        HEADING_1_PATTERN("(^# (.*))"),
        HEADING_2_PATTERN("(^## (.*))"),
        HEADING_3_PATTERN("(^### (.*))"),
        HEADING_4_PATTERN("(^#### (.*))"),
        HEADING_5_PATTERN("(^##### (.*))"),
        HEADING_6_PATTERN("(^###### (.*))"),

        // Markdown Inline Patterns
        BOLD_PATTERN("(\\*{2})\\S.*?(\\*{2})|(__)\\S.*?(__)"),
        ITALICS_PATTERN("((\\*)\\S(.*?)(\\*)|(_)\\S(.*?)(_))"),
        STRIKETHROUGH_PATTERN("((~~)\\S(.*?)(~~))"),
        INCLINE_CODE_PATTERN("(`\\S(.*?)`)"),
        IMAGE_PATTERN("(!\\[\\S.*\\S]\\(\\S.*\\S\\))"),
        LINK_PATTERN("(\\[\\S.*\\S]\\(\\S.*\\S\\))"),
        HRULE_PATTERN("($-{3,}|\\*{3,}|_{3,}^)"),

        // Markdown Element Patterns
        CODE_PATTERN("(```[a-z]*\\n(?:(?!```)[\\s\\S])+```)");

        private String pattern;

        Patterns(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }

        @Override
        public String toString(){
            return pattern;
        }

    }

}
