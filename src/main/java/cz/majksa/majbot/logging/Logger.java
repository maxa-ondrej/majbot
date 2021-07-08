/*
 *  majbot - cz.majksa.majbot.logging.Logger
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

package cz.majksa.majbot.logging;

import cz.majksa.majbot.logging.errors.ErrorsSaver;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p><b>Class {@link cz.majksa.majbot.logging.Logger}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class Logger {

    @Getter(AccessLevel.MODULE)
    private final org.apache.logging.log4j.Logger logger;
    private final Map<Level, List<Consumer<LogEvent>>> listeners = new HashMap<>();
    private final Collection<ErrorsSaver> errorsSavers = new HashSet<>();

    public void listen(Level level, Consumer<LogEvent> consumer) {
        getListeners(level).add(consumer);
    }

    public void remove(Level level, Consumer<LogEvent> consumer) {
        getListeners(level).remove(consumer);
    }

    public List<Consumer<LogEvent>> getListeners(Level level) {
        if (!listeners.containsKey(level)) {
            listeners.put(level, new ArrayList<>());
        }
        return listeners.get(level);
    }

    public void addErrorsSaver(@NonNull ErrorsSaver errorsSaver) {
        errorsSavers.add(errorsSaver);
        errorsSaver.init();
    }

    public void removeErrorsSaver(@NonNull ErrorsSaver errorsSaver) {
        errorsSavers.remove(errorsSaver);
    }

    public LogBuilder atDebug() {
        return atLevel(Level.DEBUG);
    }

    public LogBuilder atTrace() {
        return atLevel(Level.TRACE);
    }

    public LogBuilder atInfo() {
        return atLevel(Level.INFO);
    }

    public LogBuilder atWarn() {
        return atLevel(Level.WARN);
    }

    public LogBuilder atError() {
        return atLevel(Level.ERROR);
    }

    public LogBuilder atFatal() {
        return atLevel(Level.FATAL);
    }

    public LogBuilder always() {
        return atLevel(Level.ALL);
    }

    public LogBuilder atLevel(Level level) {
        return new LogBuilderImpl(this, level);
    }

    void logMessage(Level level, Marker marker, StackTraceElement location, Message message, Throwable throwable) {
        final LogEvent event = Log4jLogEvent.newBuilder()
                .setMessage(message)
                .setMarker(marker)
                .setLevel(level)
                .setLoggerName(logger.getName())
                .setLoggerFqcn(LogBuilderImpl.FQCN)
                .setThrown(throwable)
                .build();
        if (throwable != null) {
            errorsSavers.forEach(errorsSaver -> errorsSaver.save(throwable));
        }
        getListeners(level).forEach(logEventConsumer -> logEventConsumer.accept(event));
        logger.logMessage(level, marker, LogBuilderImpl.FQCN, location, message, throwable);
    }

}
