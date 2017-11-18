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

package com.desive.utilities;

import com.google.common.collect.Lists;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
 Created by Jack DeSive on 11/12/2017 at 1:58 PM
*/
public class Spellcheck {

    /*
    Language Tool handles caching of the parsed text for quick spell checking.
    The first use might be a little slow though.
     */
    private final static Logger logger = LoggerFactory.getLogger(Spellcheck.class);
    private final static JLanguageTool languageTool = new JLanguageTool(new AmericanEnglish());

    private String[] spellcheckExcluded = {
            "``",
            "  ",
            "\""
    };

    private String[] spellcheckExcludedTags = {
            "__",
            "_",
            "**",
            "*",
            "~~"
    };

    public List<RuleMatch> check(String text) throws IOException {
        List<RuleMatch> matches = languageTool.check(text);
        matches = matches.stream().filter(match -> {
            String str = text.substring(match.getFromPos(), match.getToPos());
            if(Lists.newArrayList(spellcheckExcluded).contains(str)){
                return false;
            }
            for(String ex : spellcheckExcludedTags){
                if(str.startsWith(ex) || str.endsWith(ex)){
                    return false;
                }
            }
            logger.debug("Found misspelling \'{}\' at {} to {}", str, match.getFromPos(), match.getToPos());
            return true;
        }).collect(Collectors.toList());
        logger.debug("Found " + matches.size() + " misspellings in current document");
        return matches;
    }

}
