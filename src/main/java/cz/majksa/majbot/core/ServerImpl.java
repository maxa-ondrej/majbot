/*
 *  majbot - cz.majksa.majbot.Server
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

import cz.majksa.majbot.MajBot;
import cz.majksa.majbot.listeners.EntryPoint;
import cz.majksa.majbot.listeners.Listeners;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p><b>Class {@link cz.majksa.majbot.core.ServerImpl}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class ServerImpl implements Server {

    private final @NonNull Guild guild;
    private final @NonNull Listeners listeners;

    public ServerImpl(@NonNull MajBot majBot, @NonNull Guild guild) {
        this.guild = guild;
        listeners = majBot.getListeners();
    }

    @Override
    public @NonNull <T extends GenericGuildEvent> EntryPoint<T> listen(@NonNull Class<T> clazz, @NonNull Consumer<T> callback, @NonNull Predicate<T> predicate) {
        final Predicate<T> predicate2 = event -> guild.equals(event.getGuild());
        return listeners
                .get(clazz)
                .register(clazz, callback, predicate2.and(predicate));
    }

}
