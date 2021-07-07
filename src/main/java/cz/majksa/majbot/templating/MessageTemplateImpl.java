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
import net.dv8tion.jda.api.EmbedBuilder;

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

    private final List<EmbedBuilder> embedBuilders = new LinkedList<>();
    private final EmbedBuilder defaultEmbedBuilder = new EmbedBuilder();
    private final StringBuilder contentBuilder = new StringBuilder();

}
