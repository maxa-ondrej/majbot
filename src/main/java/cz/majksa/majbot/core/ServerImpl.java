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
import cz.majksa.majbot.permissions.Permissions;
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

    /**
     * The {@link net.dv8tion.jda.api.entities.Guild} object
     */
    private final @NonNull Guild guild;
    /**
     * The {@link cz.majksa.majbot.listeners.Listeners}
     */
    private final @NonNull Listeners listeners;
    /**
     * The {@link cz.majksa.majbot.permissions.Permissions}
     */
    private final @NonNull Permissions permissions = new Permissions();

    /**
     * The constructor
     *
     * @param majBot the bot application
     * @param guild {@link #guild}
     */
    public ServerImpl(@NonNull MajBot majBot, @NonNull Guild guild) {
        this.guild = guild;
        listeners = majBot.getListeners();
    }

    /**
     * {@inheritDoc}
     *
     * @param clazz     the event type class, has to extend {@link net.dv8tion.jda.api.events.guild.GenericGuildEvent}
     * @param callback  the function called on event
     * @param predicate the condition whether the callback should be executed
     * @param <T> the event type, has to extend {@link net.dv8tion.jda.api.events.guild.GenericGuildEvent}
     * @return the generated {@link cz.majksa.majbot.listeners.EntryPoint}
     */
    @Override
    public @NonNull <T extends GenericGuildEvent> EntryPoint<T> listen(@NonNull Class<T> clazz, @NonNull Consumer<T> callback, @NonNull Predicate<T> predicate) {
        final Predicate<T> predicate2 = event -> guild.equals(event.getGuild());
        return listeners
                .get(clazz)
                .register(clazz, callback, predicate2.and(predicate));
    }

}
