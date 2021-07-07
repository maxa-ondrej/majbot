/*
 *  majbot - cz.majksa.majbot.templating.MessageTemplateImplTest
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

package cz.majksa.majbot.templating;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTemplateTest {

    private MessageTemplate template;

    @BeforeEach
    void setUp() {
        template = new MessageTemplateImpl();
    }

    @Test
    void isValid() {
        for (int i = 0; i < MessageEmbed.EMBED_MAX_LENGTH_CLIENT / 10; i++) {
            template.appendDescription("1234567890");
        }
        template.addEmbedBuilder();
        assertTrue(template.isValid());
        template.addEmbedBuilder();
        template.addEmbedBuilder();
        assertTrue(template.isValid());
        template.addEmbedBuilder();
        assertFalse(template.isValid());
    }

    @Test
    void isContentEmpty() {
        assertTrue(template.isContentEmpty());
        template.append("Message");
        assertFalse(template.isContentEmpty());
        template.clearContent();
        assertTrue(template.isContentEmpty());
    }

    @Test
    void areEmbedsEmpty() {
        assertFalse(template.areEmbedsEmpty());
        template.addEmbedBuilder();
        assertTrue(template.areEmbedsEmpty());
        template.getEmbedBuilders().clear();
        assertFalse(template.areEmbedsEmpty());
    }

    @Test
    void isAmountOfEmbedsValid() {
        template.setDescription("test");
        assertTrue(template.isAmountOfEmbedsValid());
        for (int i = 0; i < 11; i++) {
            template.addEmbedBuilder();
        }
        assertFalse(template.isAmountOfEmbedsValid());
    }

    @Test
    void getContentLength() {
        assertEquals(0, template.getContentLength());
        template.append("Message");
        assertEquals(7, template.getContentLength());
        template.clearContent();
        assertEquals(0, template.getContentLength());
    }

    @Test
    void isValidEmbedsLength() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < MessageEmbed.TEXT_MAX_LENGTH / 20; i++) {
            text.append("1234567890");
        }
        template.addEmbedBuilder();
        assertTrue(template.isEmbedsLengthValid());
        for (int i = 0; i < 20; i++) {
            template.addField("1234567890", text.toString(), false);
        }
        template.setDescription(text.toString() + text);
        assertFalse(template.isEmbedsLengthValid());
    }

    @Test
    void isValidContentLength() {
        assertTrue(template.isContentLengthValid());
        for (int i = 1; i < Message.MAX_CONTENT_LENGTH / 10; i++) {
            template.append("1234567890");
        }
        assertTrue(template.isContentLengthValid());
        template.append("12345678901234567890");
        assertFalse(template.isContentLengthValid());
        template.clearContent();
        assertTrue(template.isContentLengthValid());
    }

}