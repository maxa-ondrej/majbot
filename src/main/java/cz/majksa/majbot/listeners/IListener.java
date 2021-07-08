/*
 *  majbot - cz.majksa.majbot.listeners.AbstractListener
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

package cz.majksa.majbot.listeners;

import lombok.NonNull;
import net.dv8tion.jda.api.events.GenericEvent;

/**
 * <p><b>Class {@link IListener}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IListener<T extends GenericEvent> {

    /**
     * Runs the event.
     *
     * @param event the actual event {@link T}
     */
    void run(@NonNull T event);

    /**
     * Checks if the event is the one we want to run.
     *
     * @param event the actual event {@link T}
     * @return <code>true</code> if this event is the one we want
     */
    default boolean check(@NonNull T event) {
        return true;
    }

    /**
     * Gets the {@link cz.majksa.majbot.listeners.Listener} annotation
     *
     * @return the {@link cz.majksa.majbot.listeners.Listener} annotation
     */
    default Listener getAnnotation() {
        return getClass().getAnnotation(Listener.class);
    }

}
