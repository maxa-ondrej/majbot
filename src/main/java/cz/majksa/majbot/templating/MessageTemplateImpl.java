/*
 *  majbot - cz.majksa.majbot.templating.MessageTemplateImpl
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

import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p><b>Class {@link cz.majksa.majbot.templating.MessageTemplateImpl}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class MessageTemplateImpl implements MessageTemplate {

    private final @NonNull List<EmbedBuilder> embedBuilders;
    private final @NonNull EmbedBuilder defaultEmbedBuilder;
    private final @NonNull StringBuilder contentBuilder;

    /**
     * A constructor
     */
    public MessageTemplateImpl() {
        this(new EmbedBuilder());
    }

    /**
     *
     * @param defaultEmbedBuilder {@link #defaultEmbedBuilder}
     */
    public MessageTemplateImpl(@NonNull EmbedBuilder defaultEmbedBuilder) {
        this(new LinkedList<>(), defaultEmbedBuilder, new StringBuilder());
    }

    /**
     * A constructor
     *
     * @param template the template to copy
     */
    public MessageTemplateImpl(@NonNull MessageTemplate template) {
        this(template.getEmbedBuilders(), template.getDefaultEmbedBuilder(), template.getContentBuilder());
    }

    /**
     * A constructor
     *
     * @param embedBuilders       {@link #embedBuilders}
     * @param defaultEmbedBuilder {@link #defaultEmbedBuilder}
     * @param contentBuilder      {@link #contentBuilder}
     */
    public MessageTemplateImpl(@NonNull List<EmbedBuilder> embedBuilders, @NonNull EmbedBuilder defaultEmbedBuilder, @NonNull StringBuilder contentBuilder) {
        this.embedBuilders = embedBuilders;
        this.defaultEmbedBuilder = defaultEmbedBuilder;
        this.contentBuilder = contentBuilder;
    }

}
