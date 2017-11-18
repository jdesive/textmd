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

import com.desive.nodes.editor.EditorPane;
import com.google.common.collect.Lists;
import org.fxmisc.richtext.CodeArea;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 Created by Jack DeSive on 10/9/2017 at 6:52 PM
*/
public class MarkdownHighligher {

    private static final Logger logger = LoggerFactory.getLogger(MarkdownHighligher.class);

    private static final String HEADING_1_PATTERN = "(^# (.*))";
    private static final String HEADING_2_PATTERN = "(^## (.*))";
    private static final String HEADING_3_PATTERN = "(^### (.*))";
    private static final String HEADING_4_PATTERN = "(^#### (.*))";
    private static final String HEADING_5_PATTERN = "(^##### (.*))";
    private static final String HEADING_6_PATTERN = "(^###### (.*))";

    private static final String BOLD_PATTERN = "(\\*{2})\\S.*?(\\*{2})|(__)\\S.*?(__)";
    private static final String ITALICS_PATTERN = "((\\*)\\S(.*?)(\\*)|(_)\\S(.*?)(_))";
    private static final String STRIKETHROUGH_PATTERN = "((~~)\\S(.*?)(~~))";
    private static final String INCLINE_CODE_PATTERN = "(`\\S(.*?)`)";
    private static final String IMAGE_PATTERN = "(!\\[\\S.*\\S]\\(\\S.*\\S\\))";
    private static final String LINK_PATTERN = "(\\[\\S.*\\S]\\(\\S.*\\S\\))";
    private static final String HRULE_PATTERN = "($-{3,}|\\*{3,}|_{3,}^)";
    private static final String CODE_PATTERN = "(```[a-z]*\\n(?:(?!```)[\\s\\S])+```)";

    private static final Pattern HIGHLIGHTING_PATTERN = Pattern.compile(
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

    public static void computeHighlighting(String text, CodeArea parent) {
        Matcher matcher = HIGHLIGHTING_PATTERN.matcher(text);

        List<StyleIndex> styleChanges = Lists.newArrayList();

        while(matcher.find()) {

            String styleClass =
                    matcher.group("HRULE") != null ? "hrule" :
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
            parent.setStyle(matcher.start(), matcher.end(), Collections.singleton(styleClass));

            /*StyleIndex index = new StyleIndex(matcher.start(), matcher.end());
            index.addStyle(styleClass);
            styleChanges.add(index);*/

        }
    }

    public static void computeSpellcheckHighlighting(List<RuleMatch> matches, EditorPane parent) {

    }

    static class StyleIndex {
        int start;
        int end;

        List<String> styleClasses = Lists.newArrayList();

        StyleIndex(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public void addStyle(String clazz) {
            styleClasses.add(clazz);
        }

    }

}
