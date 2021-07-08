/*
 *  majbot - cz.majksa.majbot.DiscordUtilsTest
 *  Copyright (C) 2021  Majksa
 *
 * This program is free software: you can redistribute it and/or modify
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cz.majksa.majbot;

import cz.majksa.majbot.utils.DiscordUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscordUtilsTest {

    @Test
    void escapeStars() {
        assertEquals("test\\*\\*a\\*\\*test", DiscordUtils.escapeStars("test**a**test"));
    }

    @Test
    void escapeUnderscores() {
        assertEquals("test\\_\\_a\\_\\_test", DiscordUtils.escapeUnderscores("test__a__test"));
    }

    @Test
    void escapeStrike() {
        assertEquals("test\\~\\~a\\~\\~test", DiscordUtils.escapeStrike("test~~a~~test"));
        assertEquals("test~a~test", DiscordUtils.escapeStrike("test~a~test"));
    }

    @Test
    void escapeSpoiler() {
        assertEquals("test\\|\\|a\\|\\|test", DiscordUtils.escapeSpoiler("test||a||test"));
        assertEquals("test|a|test", DiscordUtils.escapeSpoiler("test|a|test"));
    }

    @Test
    void escapeQuotes() {
        assertEquals("\\> test>a", DiscordUtils.escapeQuotes("> test>a"));
    }

    @Test
    void escapeLinks() {
        assertEquals("test <https://google.com> test [named link]\u200E(test)", DiscordUtils.escapeLinks("test https://google.com test [named link](test)"));
    }

    @Test
    void escapeApostrophes() {
        assertEquals("test'''test", DiscordUtils.escapeApostrophes("test```test"));
    }

    @Test
    void escapeNewLines() {
        assertEquals("test\\n\\n\\ntest", DiscordUtils.escapeNewLines("test\u000b\u000c\rtest"));
    }

    @Test
    void italic() {
        assertEquals("*italic*", DiscordUtils.italic("italic"));
    }

    @Test
    void bold() {
        assertEquals("**bold**", DiscordUtils.bold("bold"));
    }

    @Test
    void underline() {
        assertEquals("__aaa__", DiscordUtils.underline("aaa"));
    }

    @Test
    void code() {
        assertEquals("`test`", DiscordUtils.code("test"));
    }

    @Test
    void codeblock() {
        assertEquals("```\nhallo\n```", DiscordUtils.codeblock("hallo"));
        assertEquals("```java\nhallo\n```", DiscordUtils.codeblock("hallo", "java"));
    }

    @Test
    void strike() {
        assertEquals("~~aaaa~~", DiscordUtils.strike("aaaa"));
    }

    @Test
    void spoiler() {
        assertEquals("||abc||", DiscordUtils.spoiler("abc"));
    }

    @Test
    void quote() {
        assertEquals("> quote", DiscordUtils.quote("quote"));
    }

    @Test
    void link() {
        assertEquals("[abc](https://www.google.com)", DiscordUtils.link("abc", "https://www.google.com"));
    }

    @Test
    void testFormat() {
        assertEquals(
                "test **'hi'hi** 2 [troll link]\u200E(test) \\n '''codeblock''' ```real code block'''a```",
                DiscordUtils.format("test **%s** %s %s %s %s ```%s```", "`hi`hi", "2", "[troll link](test)", "\n", "```codeblock```", "real code block```a")
        );
    }

}