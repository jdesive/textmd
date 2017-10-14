package com.desive.markdown;

import org.fxmisc.richtext.CodeArea;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 Created by Jack DeSive on 10/9/2017 at 6:52 PM
*/
public class MarkdownHighligher {

    private static final String HEADING_1_PATTERN = "(^# (.*))";
    private static final String HEADING_2_PATTERN = "(^## (.*))";
    private static final String HEADING_3_PATTERN = "(^### (.*))";
    private static final String HEADING_4_PATTERN = "(^#### (.*))";
    private static final String HEADING_5_PATTERN = "(^##### (.*))";
    private static final String HEADING_6_PATTERN = "(^###### (.*))";

    private static final String BOLD_PATTERN = "(\\*{2}|__)\\S.*?\\S(\\*{2}|__)";
    private static final String ITALICS_PATTERN = "((\\*|_)\\S(.*?)\\S(\\*|_))";
    private static final String INCLINE_CODE_PATTERN = "(`\\S(.*?)\\S`)";
    private static final String IMAGE_PATTERN = "(!\\[\\S.*\\S\\]\\(\\S.*\\S\\))";
    private static final String LINK_PATTERN = "(\\[\\S.*\\S\\]\\(\\S.*\\S\\))";
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
                    + "|(?<CODE>" + CODE_PATTERN + ")"
                    + "|(?<INLINECODE>" + INCLINE_CODE_PATTERN + ")"
                    + "|(?<IMAGE>" + IMAGE_PATTERN + ")"
                    + "|(?<LINK>" + LINK_PATTERN + ")"
            , Pattern.MULTILINE
    );

    public static void computeHighlighting(String text, CodeArea parent) {
        Matcher matcher = HIGHLIGHTING_PATTERN.matcher(text);
        while(matcher.find()) {
            Set<String> classes = Collections.EMPTY_SET;

/*                if(matcher.group("HRULE") != null) {
                    classes.add("hrule");
                }
                if(matcher.group( "HEADING1") != null) {
                    classes.add("heading1");
                }
                if(matcher.group( "HEADING2") != null) {
                    classes.add("heading2");
                }
                if(matcher.group( "HEADING3") != null) {
                    classes.add("heading3");
                }
                if(matcher.group( "HEADING4") != null) {
                    classes.add("heading4");
                }
                if(matcher.group( "HEADING5") != null) {
                    classes.add("heading5");
                }
                if(matcher.group( "HEADING6") != null) {
                    classes.add("heading6");
                }
                if(matcher.group( "BOLD") != null) {
                    classes.add("bold");
                }
                if(matcher.group( "ITALICS") != null) {
                    classes.add("italics");
                }
                if(matcher.group( "INLINECODE") != null) {
                    classes.add("inline-code");
                }
                if(matcher.group( "IMAGE") != null) {
                    classes.add("image");
                }
                if(matcher.group( "LINK") != null) {
                    classes.add("link");
                }
                if(matcher.group( "CODE") != null) {
                    classes.add("code");
                }*/

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
                    matcher.group("INLINECODE") != null ? "inline-code" :
                    matcher.group("IMAGE") != null ? "image" :
                    matcher.group("LINK") != null ? "link" :
                    matcher.group("CODE") != null ? "code" :
                    null;
            if(classes != null) {
                parent.setStyle(matcher.start(), matcher.end(), Collections.singleton(styleClass));
                System.out.println("Found " + styleClass + ": " + matcher.group() + " at " + matcher.start() + " to " + matcher.end());
            }
        }
    }

}
