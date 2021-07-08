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
    @Getter
    private final List<LogListener> listeners = new ArrayList<>();
    private final Collection<ErrorsSaver> errorsSavers = new HashSet<>();

    /**
     * Adds a listener
     *
     * @param listener the listener
     */
    public void registerListener(@NonNull LogListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener
     *
     * @param listener the listener
     */
    public void removeListener(@NonNull LogListener listener) {
        listeners.remove(listener);
    }

    /**
     * Adds an error saver
     *
     * @param errorsSaver the error saver to be added
     */
    public void addErrorsSaver(@NonNull ErrorsSaver errorsSaver) {
        errorsSavers.add(errorsSaver);
        errorsSaver.init();
    }

    /**
     * Removes an error saver
     *
     * @param errorsSaver the error saver to be removed
     */
    public void removeErrorsSaver(@NonNull ErrorsSaver errorsSaver) {
        errorsSavers.remove(errorsSaver);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on Debug leve;
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atDebug() {
        return atLevel(Level.DEBUG);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on Trace level
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atTrace() {
        return atLevel(Level.TRACE);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on Info level
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atInfo() {
        return atLevel(Level.INFO);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on Warn level
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atWarn() {
        return atLevel(Level.WARN);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on Error level
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atError() {
        return atLevel(Level.ERROR);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on Fatal level
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atFatal() {
        return atLevel(Level.FATAL);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on all levels
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder always() {
        return atLevel(Level.ALL);
    }

    /**
     * Creates a new {@link org.apache.logging.log4j.LogBuilder} on a level
     *
     * @return the {@link org.apache.logging.log4j.LogBuilder}
     */
    public LogBuilder atLevel(Level level) {
        return new LogBuilderImpl(this, level);
    }

    /**
     * Logs the message in {@link #logger}
     *
     * @param level     The logging Level to check.
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void logMessage(Level level, Marker marker, StackTraceElement location, Message message, Throwable throwable) {
        logger.logMessage(level, marker, LogBuilderImpl.FQCN, location, message, throwable);
        if (throwable != null) {
            errorsSavers.forEach(errorsSaver -> errorsSaver.save(throwable));
        }
        listeners.forEach(listener -> listener.onEvent(level, marker, location, message, throwable));
    }

}
