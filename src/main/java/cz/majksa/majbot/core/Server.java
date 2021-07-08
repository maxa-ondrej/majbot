/*
 *  majbot - cz.majksa.majbot.core.Server
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

package cz.majksa.majbot.core;

import cz.majksa.majbot.listeners.EntryPoint;
import lombok.NonNull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p><b>Interface {@link cz.majksa.majbot.core.Server}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Server {

    /**
     * The discord guild object
     *
     * @return the {@link net.dv8tion.jda.api.entities.Guild} object
     */
    @NonNull Guild getGuild();

    @NonNull <T extends GenericGuildEvent> EntryPoint<T> listen(@NonNull Class<T> clazz, @NonNull Consumer<T> callback, @NonNull Predicate<T> predicate);

}
