/*
 *  majbot - cz.majksa.majbot.templating.Template
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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p><b>Interface {@link cz.majksa.majbot.templating.MessageTemplate}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MessageTemplate extends Appendable {

    @NotNull List<EmbedBuilder> getEmbedBuilders();

    @NotNull EmbedBuilder getDefaultEmbedBuilder();

    @NotNull StringBuilder getContentBuilder();

    default @NotNull EmbedBuilder addEmbedBuilder() {
        EmbedBuilder builder = new EmbedBuilder(getDefaultEmbedBuilder());
        addEmbedBuilder(builder);
        return builder;
    }

    default @NotNull MessageTemplate addEmbedBuilder(@NotNull Consumer<EmbedBuilder> consumer) {
        consumer.accept(addEmbedBuilder());
        return this;
    }

    default @NotNull MessageTemplate addEmbedBuilder(@NotNull EmbedBuilder embedBuilder) {
        getEmbedBuilders().add(embedBuilder);
        return this;
    }

    /**
     * Buils the DefaultResponse into {@link net.dv8tion.jda.api.entities.Message Message}.
     *
     * @return the built {@link net.dv8tion.jda.api.entities.Message Message}
     */
    default @NotNull Message build() {
        return getMessageBuilder().build();
    }

    default @NotNull MessageBuilder getMessageBuilder() {
        final List<EmbedBuilder> embedBuilders = getEmbedBuilders();
        return new MessageBuilder()
                .setContent(getContentBuilder().toString())
                .setEmbeds(embedBuilders.isEmpty() ? Collections.emptySet() : embedBuilders.stream().map(EmbedBuilder::build).collect(Collectors.toSet()));
    }

    @Override
    default @NotNull MessageTemplate append(char c) {
        getContentBuilder().append(c);
        return this;
    }

    @Override
    default @NotNull MessageTemplate append(CharSequence csq) {
        getContentBuilder().append(csq);
        return this;
    }

    @Override
    default @NotNull MessageTemplate append(CharSequence csq, int start, int end) {
        getContentBuilder().append(csq, start, end);
        return this;
    }

    default @NotNull MessageTemplate clearContent() {
        getContentBuilder().setLength(0);
        return this;
    }

    default @NotNull MessageTemplate clearEmbeds() {
        forEachEmbed(EmbedBuilder::clear);
        return this;
    }

    /**
     * Resets this builder to default state.
     * <br>All parts will be either empty or null after this method has returned.
     *
     * @return The current EmbedBuilder with default values
     */
    default @NotNull MessageTemplate clear() {
        clearContent();
        clearEmbeds();
        return this;
    }

    /**
     * Checks if everything inside this message is valid
     *
     * @return true if everything inside this message is valid
     */
    default boolean isValid() {
        return isAmountOfEmbedsValid()
                && isValidLength()
                && !areEmbedsEmpty()
                && !(isContentEmpty() && getEmbedBuilders().size() == 0);
    }

    /**
     * Checks if the given content is empty.
     *
     * @return true if the content is empty
     */
    default boolean isContentEmpty() {
        return getContentLength() == 0;
    }

    /**
     * Checks if at least one of the embeds is empty and cannot be built. Empty embeds will throw an exception if built
     *
     * @return true if at least one of the embeds is empty and cannot be built
     */
    default boolean areEmbedsEmpty() {
        for (EmbedBuilder embedBuilder : getEmbedBuilders()) {
            if (embedBuilder.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    default boolean isAmountOfEmbedsValid() {
        return getEmbedBuilders().size() <= 10;
    }

    /**
     * The overall length of the current message content part.
     *
     * @return length of the current string state
     */
    default int getContentLength() {
        return getContentBuilder().length();
    }

    /**
     * The overall length of the current EmbedBuilder in displayed characters.
     * <br>Represents the {@link net.dv8tion.jda.api.entities.MessageEmbed#getLength() MessageEmbed.getLength()} value.
     *
     * @return length of the current builder state
     */
    default boolean isEmbedsLengthValid() {
        for (EmbedBuilder embedBuilder : getEmbedBuilders()) {
            if (!embedBuilder.isValidLength()) {
                return false;
            }
        }
        return getEmbedBuilders().stream().mapToInt(EmbedBuilder::length).sum() <= MessageEmbed.EMBED_MAX_LENGTH_BOT;
    }

    /**
     * The overall length of the current message content part.
     *
     * @return length of the current string state
     */
    default boolean isContentLengthValid() {
        return getContentLength() <= Message.MAX_CONTENT_LENGTH;
    }

    /**
     * Checks whether the constructed {@link net.dv8tion.jda.api.entities.Message Message}
     * is within the limits for a bot account.
     *
     * @return True, if {@link #isEmbedsLengthValid()} and {@link #isContentLengthValid()} are both true
     * @see net.dv8tion.jda.api.entities.Message#MAX_CONTENT_LENGTH
     * @see net.dv8tion.jda.api.entities.MessageEmbed#EMBED_MAX_LENGTH_BOT
     */
    default boolean isValidLength() {
        return isContentLengthValid() && isEmbedsLengthValid();
    }

    default @NotNull MessageTemplate forEachEmbed(@NotNull Consumer<EmbedBuilder> consumer) {
        getEmbedBuilders().forEach(consumer);
        consumer.accept(getDefaultEmbedBuilder());
        return this;
    }

    /**
     * Sets the Title of the embed.
     * <br>Overload for {@link #setTitle(String, String)} without URL parameter.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/04-setTitle.png">Example</a></b>
     *
     * @param title the title of the embed
     * @return the builder after the title has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the provided {@code title} is an empty String.</li>
     *                                  <li>If the length of {@code title} is greater than {@link net.dv8tion.jda.api.entities.MessageEmbed#TITLE_MAX_LENGTH}.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate setTitle(@javax.annotation.Nullable String title) {
        return setTitle(title, null);
    }

    /**
     * Sets the Title of the embed.
     * <br>You can provide {@code null} as url if no url should be used.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/04-setTitle.png">Example</a></b>
     *
     * @param title the title of the embed
     * @param url   Makes the title into a hyperlink pointed at this url.
     * @return the builder after the title has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the provided {@code title} is an empty String.</li>
     *                                  <li>If the length of {@code title} is greater than {@link net.dv8tion.jda.api.entities.MessageEmbed#TITLE_MAX_LENGTH}.</li>
     *                                  <li>If the length of {@code url} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code url} is not a properly formatted http or https url.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate setTitle(@javax.annotation.Nullable String title, @javax.annotation.Nullable String url) {
        forEachEmbed(embedBuilder -> embedBuilder.setTitle(title, url));
        return this;
    }

    /**
     * Sets the Description of the embed. This is where the main chunk of text for an embed is typically placed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/05-setDescription.png">Example</a></b>
     *
     * @param description the description of the embed, {@code null} to reset
     * @return the builder after the description has been set
     * @throws IllegalArgumentException If the length of {@code description} is greater than {@link net.dv8tion.jda.api.entities.MessageEmbed#TEXT_MAX_LENGTH}
     */
    default @NotNull MessageTemplate setDescription(@javax.annotation.Nullable CharSequence description) {
        forEachEmbed(embedBuilder -> embedBuilder.setDescription(description));
        return this;
    }

    /**
     * Appends to the description of the embed. This is where the main chunk of text for an embed is typically placed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/05-setDescription.png">Example</a></b>
     *
     * @param description the string to append to the description of the embed
     * @return the builder after the description has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the provided {@code description} String is null</li>
     *                                  <li>If the length of {@code description} is greater than {@link net.dv8tion.jda.api.entities.MessageEmbed#TEXT_MAX_LENGTH}.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate appendDescription(@NotNull CharSequence description) {
        forEachEmbed(embedBuilder -> embedBuilder.appendDescription(description));
        return this;
    }

    /**
     * Sets the Timestamp of the embed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/13-setTimestamp.png">Example</a></b>
     *
     * <p><b>Hint:</b> You can get the current time using {@link java.time.Instant#now() Instant.now()} or convert time from a
     * millisecond representation by using {@link java.time.Instant#ofEpochMilli(long) Instant.ofEpochMilli(long)};
     *
     * @param temporal the temporal accessor of the timestamp
     * @return the builder after the timestamp has been set
     */
    default @NotNull MessageTemplate setTimestamp(@javax.annotation.Nullable TemporalAccessor temporal) {
        forEachEmbed(embedBuilder -> embedBuilder.setTimestamp(temporal));
        return this;
    }

    /**
     * Sets the Color of the embed.
     *
     * <a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/02-setColor.png" target="_blank">Example</a>
     *
     * @param color The {@link java.awt.Color Color} of the embed
     *              or {@code null} to use no color
     * @return the builder after the color has been set
     * @see #setColor(int)
     */
    default @NotNull MessageTemplate setColor(@javax.annotation.Nullable Color color) {
        forEachEmbed(embedBuilder -> embedBuilder.setColor(color));
        return this;
    }

    /**
     * Sets the raw RGB color value for the embed.
     *
     * <a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/02-setColor.png" target="_blank">Example</a>
     *
     * @param color The raw rgb value, or {@link net.dv8tion.jda.api.entities.Role#DEFAULT_COLOR_RAW} to use no color
     * @return the builder after the color has been set
     * @see #setColor(java.awt.Color)
     */
    default @NotNull MessageTemplate setColor(int color) {
        forEachEmbed(embedBuilder -> embedBuilder.setColor(color));
        return this;
    }

    /**
     * Sets the Thumbnail of the embed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/06-setThumbnail.png">Example</a></b>
     *
     * <p><b>Uploading images with Embeds</b>
     * <br>When uploading an <u>image</u>
     * (using {@link net.dv8tion.jda.api.entities.MessageChannel#sendFile(java.io.File, net.dv8tion.jda.api.utils.AttachmentOption...) MessageChannel.sendFile(...)})
     * you can reference said image using the specified filename as URI {@code attachment://filename.ext}.
     *
     * <p><u>Example</u>
     * <pre><code>
     * MessageChannel channel; // = reference of a MessageChannel
     * EmbedBuilder embed = new EmbedBuilder();
     * InputStream file = new URL("https://http.cat/500").openStream();
     * embed.setThumbnail("attachment://cat.png") // we specify this in sendFile as "cat.png"
     *      .setDescription("This is a cute cat :3");
     * channel.sendFile(file, "cat.png").embed(embed.build()).queue();
     * </code></pre>
     *
     * @param url the url of the thumbnail of the embed
     * @return the builder after the thumbnail has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the length of {@code url} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code url} is not a properly formatted http or https url.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate setThumbnail(@javax.annotation.Nullable String url) {
        forEachEmbed(embedBuilder -> embedBuilder.setThumbnail(url));
        return this;
    }

    /**
     * Sets the Image of the embed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/11-setImage.png">Example</a></b>
     *
     * <p><b>Uploading images with Embeds</b>
     * <br>When uploading an <u>image</u>
     * (using {@link net.dv8tion.jda.api.entities.MessageChannel#sendFile(java.io.File, net.dv8tion.jda.api.utils.AttachmentOption...) MessageChannel.sendFile(...)})
     * you can reference said image using the specified filename as URI {@code attachment://filename.ext}.
     *
     * <p><u>Example</u>
     * <pre><code>
     * MessageChannel channel; // = reference of a MessageChannel
     * EmbedBuilder embed = new EmbedBuilder();
     * InputStream file = new URL("https://http.cat/500").openStream();
     * embed.setImage("attachment://cat.png") // we specify this in sendFile as "cat.png"
     *      .setDescription("This is a cute cat :3");
     * channel.sendFile(file, "cat.png").embed(embed.build()).queue();
     * </code></pre>
     *
     * @param url the url of the image of the embed
     * @return the builder after the image has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the length of {@code url} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code url} is not a properly formatted http or https url.</li>
     *                                  </ul>
     * @see net.dv8tion.jda.api.entities.MessageChannel#sendFile(java.io.File, String, net.dv8tion.jda.api.utils.AttachmentOption...) MessageChannel.sendFile(...)
     */
    default @NotNull MessageTemplate setImage(@javax.annotation.Nullable String url) {
        forEachEmbed(embedBuilder -> embedBuilder.setImage(url));
        return this;
    }

    /**
     * Sets the Author of the embed. The author appears in the top left of the embed and can have a small
     * image beside it along with the author's name being made clickable by way of providing a url.
     * This convenience method just sets the name.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/03-setAuthor.png">Example</a></b>
     *
     * @param name the name of the author of the embed. If this is not set, the author will not appear in the embed
     * @return the builder after the author has been set
     */
    default @NotNull MessageTemplate setAuthor(@javax.annotation.Nullable String name) {
        return setAuthor(name, null, null);
    }

    /**
     * Sets the Author of the embed. The author appears in the top left of the embed and can have a small
     * image beside it along with the author's name being made clickable by way of providing a url.
     * This convenience method just sets the name and the url.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/03-setAuthor.png">Example</a></b>
     *
     * @param name the name of the author of the embed. If this is not set, the author will not appear in the embed
     * @param url  the url of the author of the embed
     * @return the builder after the author has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the length of {@code url} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code url} is not a properly formatted http or https url.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate setAuthor(@javax.annotation.Nullable String name, @javax.annotation.Nullable String url) {
        return setAuthor(name, url, null);
    }

    /**
     * Sets the Author of the embed. The author appears in the top left of the embed and can have a small
     * image beside it along with the author's name being made clickable by way of providing a url.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/03-setAuthor.png">Example</a></b>
     *
     * <p><b>Uploading images with Embeds</b>
     * <br>When uploading an <u>image</u>
     * (using {@link net.dv8tion.jda.api.entities.MessageChannel#sendFile(java.io.File, net.dv8tion.jda.api.utils.AttachmentOption...) MessageChannel.sendFile(...)})
     * you can reference said image using the specified filename as URI {@code attachment://filename.ext}.
     *
     * <p><u>Example</u>
     * <pre><code>
     * MessageChannel channel; // = reference of a MessageChannel
     * EmbedBuilder embed = new EmbedBuilder();
     * InputStream file = new URL("https://http.cat/500").openStream();
     * embed.setAuthor("Minn", null, "attachment://cat.png") // we specify this in sendFile as "cat.png"
     *      .setDescription("This is a cute cat :3");
     * channel.sendFile(file, "cat.png").embed(embed.build()).queue();
     * </code></pre>
     *
     * @param name    the name of the author of the embed. If this is not set, the author will not appear in the embed
     * @param url     the url of the author of the embed
     * @param iconUrl the url of the icon for the author
     * @return the builder after the author has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the length of {@code url} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code url} is not a properly formatted http or https url.</li>
     *                                  <li>If the length of {@code iconUrl} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code iconUrl} is not a properly formatted http or https url.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate setAuthor(@javax.annotation.Nullable String name, @javax.annotation.Nullable String url, @javax.annotation.Nullable String iconUrl) {
        forEachEmbed(embedBuilder -> embedBuilder.setAuthor(name, url, iconUrl));
        return this;
    }

    /**
     * Sets the Footer of the embed without icon.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/12-setFooter.png">Example</a></b>
     *
     * @param text the text of the footer of the embed. If this is not set or set to null, the footer will not appear in the embed.
     * @return the builder after the footer has been set
     * @throws IllegalArgumentException If the length of {@code text} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#TEXT_MAX_LENGTH}.
     */
    default @NotNull MessageTemplate setFooter(@javax.annotation.Nullable String text) {
        return setFooter(text, null);
    }

    /**
     * Sets the Footer of the embed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/12-setFooter.png">Example</a></b>
     *
     * <p><b>Uploading images with Embeds</b>
     * <br>When uploading an <u>image</u>
     * (using {@link net.dv8tion.jda.api.entities.MessageChannel#sendFile(java.io.File, net.dv8tion.jda.api.utils.AttachmentOption...) MessageChannel.sendFile(...)})
     * you can reference said image using the specified filename as URI {@code attachment://filename.ext}.
     *
     * <p><u>Example</u>
     * <pre><code>
     * MessageChannel channel; // = reference of a MessageChannel
     * EmbedBuilder embed = new EmbedBuilder();
     * InputStream file = new URL("https://http.cat/500").openStream();
     * embed.setFooter("Cool footer!", "attachment://cat.png") // we specify this in sendFile as "cat.png"
     *      .setDescription("This is a cute cat :3");
     * channel.sendFile(file, "cat.png").embed(embed.build()).queue();
     * </code></pre>
     *
     * @param text    the text of the footer of the embed. If this is not set, the footer will not appear in the embed.
     * @param iconUrl the url of the icon for the footer
     * @return the builder after the footer has been set
     * @throws IllegalArgumentException <ul>
     *                                  <li>If the length of {@code text} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#TEXT_MAX_LENGTH}.</li>
     *                                  <li>If the length of {@code iconUrl} is longer than {@link net.dv8tion.jda.api.entities.MessageEmbed#URL_MAX_LENGTH}.</li>
     *                                  <li>If the provided {@code iconUrl} is not a properly formatted http or https url.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate setFooter(@javax.annotation.Nullable String text, @javax.annotation.Nullable String iconUrl) {
        forEachEmbed(embedBuilder -> embedBuilder.setFooter(text, iconUrl));
        return this;
    }

    /**
     * Copies the provided Field into a new Field for this builder.
     * <br>For additional documentation, see {@link #addField(String, String, boolean)}
     *
     * @param field the field object to add
     * @return the builder after the field has been added
     */
    default @NotNull MessageTemplate addField(@Nullable MessageEmbed.Field field) {
        forEachEmbed(embedBuilder -> embedBuilder.addField(field));
        return this;
    }//    }

    /**
     * Adds a Field to the embed.
     *
     * <p>Note: If a blank string is provided to either {@code name} or {@code value}, the blank string is replaced
     * with {@link net.dv8tion.jda.api.EmbedBuilder#ZERO_WIDTH_SPACE}.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/07-addField.png">Example of Inline</a></b>
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/08-addField.png">Example if Non-inline</a></b>
     *
     * @param name   the name of the Field, displayed in bold above the {@code value}.
     * @param value  the contents of the field.
     * @param inline whether or not this field should display inline.
     * @return the builder after the field has been added
     * @throws IllegalArgumentException <ul>
     *                                  <li>If only {@code name} or {@code value} is set. Both must be set.</li>
     *                                  <li>If the length of {@code name} is greater than {@link net.dv8tion.jda.api.entities.MessageEmbed#TITLE_MAX_LENGTH}.</li>
     *                                  <li>If the length of {@code value} is greater than {@link net.dv8tion.jda.api.entities.MessageEmbed#VALUE_MAX_LENGTH}.</li>
     *                                  </ul>
     */
    default @NotNull MessageTemplate addField(@NotNull String name, @NotNull String value, boolean inline) {
        forEachEmbed(embedBuilder -> embedBuilder.addField(name, value, inline));
        return this;
    }

    /**
     * Adds a blank (empty) Field to the embed.
     *
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/07-addField.png">Example of Inline</a></b>
     * <p><b><a href="https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/08-addField.png">Example if Non-inline</a></b>
     *
     * @param inline whether or not this field should display inline
     * @return the builder after the field has been added
     */
    default @NotNull MessageTemplate addBlankField(boolean inline) {
        forEachEmbed(embedBuilder -> embedBuilder.addBlankField(inline));
        return this;
    }

    /**
     * Clears all fields from the embed, such as those created with the
     * {@link net.dv8tion.jda.api.EmbedBuilder#EmbedBuilder(net.dv8tion.jda.api.entities.MessageEmbed) EmbedBuilder(MessageEmbed)}
     * constructor or via the
     * {@link net.dv8tion.jda.api.EmbedBuilder#addField(net.dv8tion.jda.api.entities.MessageEmbed.Field) addField} methods.
     *
     * @return the builder after the field has been added
     */
    default @NotNull MessageTemplate clearFields() {
        forEachEmbed(EmbedBuilder::clearFields);
        return this;
    }
}
