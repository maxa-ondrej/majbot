/*
 *  majbot - cz.majksa.majbot.listeners.EntryPointList
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

import cz.majksa.majbot.MajBot;
import cz.majksa.majbot.logging.Logger;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p><b>Class {@link cz.majksa.majbot.listeners.EntryPointList}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class EntryPointList<T extends GenericEvent> {

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private final @NonNull List<EntryPoint<T>> entryPoints = new ArrayList<>();
    private final @NonNull Logger logger;

    public EntryPointList(@NonNull MajBot majBot) {
        logger = majBot.getLogger();
    }

    public @NonNull EntryPoint<T> register(@NonNull Class<T> clazz, @NonNull Consumer<T> callback, @NonNull Predicate<T> predicate) {
        return register(new EntryPoint<>(clazz, callback, predicate, this));
    }

    public @NonNull EntryPoint<T> register(@NonNull EntryPoint<T> entryPoint) {
        entryPoints.add(entryPoint);
        logger
                .atDebug()
                .log("Registering new listener: {}", entryPoint.getType().toString());
        return entryPoint;
    }

    public void unregister(@NonNull EntryPoint<T> entryPoint) {
        logger
                .atDebug()
                .log("Unregistering listener: {}", entryPoint.getType().toString());
        entryPoints.remove(entryPoint);
    }

    public void run(@NonNull T event) {
        logger
                .atDebug()
                .log("Running: {}", event.toString());
        entryPoints.stream()
                .filter(entryPoint -> entryPoint.getFilter().test(event))
                .forEach(entryPoint -> EXECUTOR.execute(() -> {
                    try {
                        entryPoint.getCallback().accept(event);
                    } catch (Throwable throwable) {
                        logger
                                .atError()
                                .withThrowable(throwable)
                                .log(throwable.getMessage());
                    }
                }));
    }

}
