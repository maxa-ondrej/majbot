/*
 *  majbot - cz.majksa.majbot.DiscordUtils
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

import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><b>Class {@link cz.majksa.majbot.DiscordUtils}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class DiscordUtils {

    /**
     * The characted used in embed footer to separate the footer itself and timestamp
     */
    public static final String BULLET = "\u2022";

    /**
     * The minimal length of a discord snowflake
     *
     * @see net.dv8tion.jda.api.entities.ISnowflake
     */
    public static final int SNOWFLAKE_LENGTH_MIN = 17;
    /**
     * The maximal length of a discord snowflake
     *
     * @see net.dv8tion.jda.api.entities.ISnowflake
     */
    public static final int SNOWFLAKE_LENGTH_MAX = 18;
    /**
     * The pattern to convert a user mention to their id.
     *
     * @see java.util.regex.Pattern
     * @see net.dv8tion.jda.api.entities.ISnowflake
     */
    public static final Pattern MENTION_TO_ID_PATTERN = Pattern.compile(String.format("<(?:@!?)?(?:@&?)?#?(?<!<)(\\d{%d,%d})>", SNOWFLAKE_LENGTH_MIN, SNOWFLAKE_LENGTH_MAX), Pattern.CASE_INSENSITIVE);
    public static final Pattern EMOJI_MENTION_TO_ID_PATTERN = Pattern.compile(String.format("<:[^:]+:(\\d{%d,%d})>", SNOWFLAKE_LENGTH_MIN, SNOWFLAKE_LENGTH_MAX), Pattern.CASE_INSENSITIVE);
    public static final Pattern TAG_PATTERN = Pattern.compile("^(.{3,32})#([0-9]{4})$", Pattern.CASE_INSENSITIVE);
    public static final Pattern QUOTE_PATTER = Pattern.compile("^> ", Pattern.MULTILINE);
    public static final Pattern URL_PATTER = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);
    public static final String LINE_BREAKS_REGEX = "\r\n|\n\r|\n|\r|\u000b|\u000c|\u0085|\u2028|\u2029";
    // Regex: CR+LF || LF+CR || LF || CR || VT (Vertical Tab) || FF (Form Feed) || NEL (Next Line) || LS (Line Sep.) || PS (Paragraph Sep.)
    // Source: https://en.wikipedia.org/wiki/Newline#Unicode
    public static final EscapingLocale DEFAULT_ESCAPING_LOCALE = new EscapingLocale(" ", "'");

    /**
     * Removes discord formatting from String
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     */
    public static @NonNull String escapeString(@NonNull String subject, Object... args) {
        return escapeString(DEFAULT_ESCAPING_LOCALE, subject, args);
    }

    /**
     * Removes discord formatting from String
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     */
    public static @NonNull String escapeString(@NonNull EscapingLocale locale, @NonNull String subject, Object... args) {
        String escaped = subject.replaceAll("\\\\", "\\\\\\\\");
        escaped = escapeStars(escaped);
        escaped = escapeUnderscores(escaped);
        escaped = escapeQuotes(escaped);
        escaped = escapeApostrophes(escaped, locale.getApostropheReplacement());
        escaped = escapeNewLines(escaped, locale.getNewLineReplacement());
        escaped = escapeLinks(escaped);
        return String.format(escaped, args);
    }

    /**
     * Escapes stars
     * <br>bold/italic
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeStars(@NonNull String subject) {
        return subject.replaceAll("\\*", "\\\\*");
    }

    /**
     * Escapes underscores
     * <br>underline/italic
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeUnderscores(@NonNull String subject) {
        return subject.replaceAll("_", "\\\\_");
    }

    /**
     * Escapes underscores
     * <br>underline/italic
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeStrike(@NonNull String subject) {
        return subject.replaceAll("~~", "\\\\~\\\\~");
    }

    /**
     * Escapes underscores
     * <br>underline/italic
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeSpoiler(@NonNull String subject) {
        return subject.replaceAll("\\|\\|", "\\\\|\\\\|");
    }

    /**
     * Escapes quotes
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeQuotes(@NonNull String subject) {
        return QUOTE_PATTER
                .matcher(subject)
                .replaceAll("\\\\> "); // quotes, making sure not to replace mentions
    }

    /**
     * Escapes quotes
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeLinks(@NonNull String subject) {
        return URL_PATTER
                .matcher(subject)
                .replaceAll("<$0>");
    }

    /**
     * Part of the escaping process
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeApostrophes(@NonNull String subject) {
        return escapeApostrophes(subject, DEFAULT_ESCAPING_LOCALE.getApostropheReplacement());
    }

    /**
     * Part of the escaping process
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeApostrophes(@NonNull String subject, @NonNull String replacement) {
        return subject.replaceAll("`", replacement);
    }

    /**
     * Replace linebreaks
     *
     * @param subject the {@link java.lang.String} to escape
     * @return the escaped string
     * @author Your Nightmare
     */
    public static @NonNull String escapeNewLines(@NonNull String subject) {
        return escapeNewLines(subject, DEFAULT_ESCAPING_LOCALE.getNewLineReplacement());
    }

    /**
     * Replace linebreaks
     *
     * @param subject     the {@link java.lang.String} to escape
     * @param replacement the string to replace linebreaks with
     * @return the escaped string
     * @author majksa
     * @author Your Nightmare
     */
    public static @NonNull String escapeNewLines(@NonNull String subject, @NonNull String replacement) {
        return subject.replaceAll(LINE_BREAKS_REGEX, replacement);
    }

    public static @NonNull String italic(@NonNull String subject) {
        return "*" + subject + "*";
    }

    public static @NonNull String bold(@NonNull String subject) {
        return "**" + subject + "**";
    }

    public static @NonNull String underline(@NonNull String subject) {
        return "__" + subject + "__";
    }

    public static @NonNull String code(@NonNull String subject) {
        return "`" + subject + "`";
    }

    public static @NonNull String codeblock(@NonNull String subject) {
        return "```" + subject + "```";
    }

    public static @NonNull String strike(@NonNull String subject) {
        return "~~" + subject + "~~";
    }

    public static @NonNull String spoiler(@NonNull String subject) {
        return "||" + subject + "||";
    }

    public static @NonNull String quote(@NonNull String subject) {
        return "> " + subject;
    }

    public static @NonNull String link(@NonNull String display, @NonNull String url) {
        return String.format("[%s](%s)", display, url);
    }

    /**
     * Takes a discord user mention or an id and converts it to a discord id.
     *
     * @param string the string to get id from
     * @return the resolved id
     * @throws NumberFormatException if the provided string is not a valid id or a valid mention
     */
    public static long resolveId(@NotNull String string) throws NumberFormatException {
        if (string.length() >= SNOWFLAKE_LENGTH_MIN && string.length() <= SNOWFLAKE_LENGTH_MAX) {
            long id = Long.parseLong(string);
            if (String.valueOf(id).equals(string)) {
                return id;
            }
        }
        Matcher matcher = MENTION_TO_ID_PATTERN.matcher(string);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        throw new NumberFormatException(string + " is not a valid id or a valid mention.");
    }

    /**
     * Takes a discord user mention or an id and converts it to a discord id.
     *
     * @param string the string to get id from
     * @return the resolved id
     * @throws NumberFormatException if the provided string is not a valid id or a valid mention
     */
    public static @NotNull Map.Entry<String, String> resolveTag(@NotNull String string) throws NumberFormatException {
        Matcher matcher = MENTION_TO_ID_PATTERN.matcher(string);
        if (matcher.find()) {
            return new AbstractMap.SimpleEntry<>(matcher.group(1), matcher.group(2));
        }
        throw new NumberFormatException(string + " is not a valid tag.");
    }

    public static @NotNull Member getMember(Guild guild, String query) {
        try {
            final long id = resolveId(query);
            final Member member = guild.getMemberById(id);
            if (member != null) {
                return member;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        throw new IllegalArgumentException(String.format("Member was not found by the specified query: **%s**!", escapeString(query)));
    }

    public static @NotNull TextChannel getTextChannel(Guild guild, String query) {
        try {
            final long id = resolveId(query);
            final TextChannel channel = guild.getTextChannelById(id);
            if (channel != null) {
                return channel;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        throw new IllegalArgumentException(String.format("Text Channel was not found by the specified query: **%s**!", escapeString(query)));
    }

    public static @NotNull Role getRole(Guild guild, String query) {
        if (query.equals(guild.getPublicRole().getAsMention())) {
            return guild.getPublicRole();
        }
        try {
            final long id = resolveId(query);
            final Role role = guild.getRoleById(id);
            if (role != null) {
                return role;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        throw new IllegalArgumentException(String.format("Role was not found by the specified query: **%s**!", escapeString(query)));
    }

    public static @NotNull Emote getEmote(JDA jda, String query) {
        Emote emote;
        try {
            emote = jda.getEmoteById(query);
            if (emote != null) {
                return emote;
            }
        } catch (NumberFormatException e) {
            Matcher matcher = EMOJI_MENTION_TO_ID_PATTERN.matcher(query);
            if (matcher.find()) {
                try {
                    final Emote emoteById = jda.getEmoteById(Long.parseLong(matcher.group(1)));
                    if (emoteById != null) {
                        return emoteById;
                    }
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException(exception.getMessage());
                }
            }
        }
        throw new IllegalArgumentException(String.format("Emote was not found by the specified query: **%s**!", escapeString(query)));
    }


    public static void addFooter(@NotNull JDA jda, @NotNull EmbedBuilder embedBuilder) {
        final SelfUser bot = jda.getSelfUser();
        embedBuilder
                .setTimestamp(new Date().toInstant())
                .setFooter(bot.getName(), bot.getAvatarUrl());
    }

    public static void addPageFooter(@NotNull JDA jda, @NotNull EmbedBuilder embedBuilder, int current, int total) {
        final SelfUser bot = jda.getSelfUser();
        embedBuilder
                .setFooter(
                        String.format("%s %s Page %d/%d", bot.getName(), BULLET, current, total),
                        bot.getAvatarUrl()
                )
                .setTimestamp(new Date().toInstant());
    }

}
