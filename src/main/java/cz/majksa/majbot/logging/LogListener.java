/*
 *  majbot - cz.majksa.majbot.logging.LogListener
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;

/**
 * <p><b>Interface {@link LogListener}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface LogListener {

    /**
     * Called whenever a message is logged
     *
     * @param level     The logging Level to check.
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onEvent(Level level, Marker marker, StackTraceElement location, Message message, Throwable throwable) {
        if (Level.DEBUG.equals(level)) {
            onDebugLevel(marker, location, message, throwable);
        } else if(Level.TRACE.equals(level)) {
            onTraceLevel(marker, location, message, throwable);
        } else if(Level.INFO.equals(level)) {
            onInfoLevel(marker, location, message, throwable);
        } else if(Level.WARN.equals(level)) {
            onWarnLevel(marker, location, message, throwable);
        } else if(Level.ERROR.equals(level)) {
            onErrorLevel(marker, location, message, throwable);
        } else if(Level.FATAL.equals(level)) {
            onFatalLevel(marker, location, message, throwable);
        } else if(Level.ALL.equals(level)) {
            onAlwaysLevel(marker, location, message, throwable);
        } else {
            onCustomLevel(level, marker, location, message, throwable);
        }
    }

    /**
     * Called whenever a message is logged on the debug level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onDebugLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on the trace level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onTraceLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on the info level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onInfoLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on the warn level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onWarnLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on the error level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onErrorLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on the fatal level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onFatalLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on the all level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onAlwaysLevel(Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

    /**
     * Called whenever a message is logged on a custom level
     *
     * @param marker    A Marker or null.
     * @param location  The location of the caller.
     * @param message   The message format.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    default void onCustomLevel(Level level, Marker marker, StackTraceElement location, Message message, Throwable throwable) {}

}
