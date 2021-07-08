/*
 *  majbot - cz.majksa.majbot.logging.MajLogBuilder
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

import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.internal.DefaultLogBuilder;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LambdaUtil;
import org.apache.logging.log4j.util.StackLocatorUtil;
import org.apache.logging.log4j.util.Supplier;

/**
 * <p><b>Class {@link LogBuilderImpl}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @see org.apache.logging.log4j.internal.DefaultLogBuilder
 * @see org.apache.logging.log4j.LogBuilder
 * @since 1.0.0
 */
@Getter
public class LogBuilderImpl implements LogBuilder {

    static final String FQCN = DefaultLogBuilder.class.getName();
    private static final Message EMPTY_MESSAGE = new SimpleMessage("");
    private static final Logger LOGGER = StatusLogger.getLogger();

    private final cz.majksa.majbot.logging.Logger logger;
    private final Logger loggerApi;
    private final long threadId = Thread.currentThread().getId();
    private Level level;
    private Marker marker;
    private Throwable throwable;
    private StackTraceElement location;
    private volatile boolean inUse;

    public LogBuilderImpl(cz.majksa.majbot.logging.Logger logger, Level level) {
        this.logger = logger;
        loggerApi = logger.getLogger();
        this.level = level;
        this.inUse = true;
    }

    /**
     * This method should be considered internal. It is used to reset the LogBuilder for a new log message.
     *
     * @param level The logging level for this event.
     * @return This LogBuilder instance.
     */
    public LogBuilder reset(Level level) {
        this.inUse = true;
        this.level = level;
        this.marker = null;
        this.throwable = null;
        this.location = null;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param marker The Marker to log.
     * @return The LogBuilder.
     */
    @Override
    public LogBuilder withMarker(Marker marker) {
        this.marker = marker;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param throwable The Throwable to log.
     * @return the LogBuilder.
     */
    @Override
    public LogBuilder withThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The LogBuilder.
     */
    @Override
    public LogBuilder withLocation() {
        location = StackLocatorUtil.getStackTraceElement(2);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param location The stack trace element to include in the log event.
     * @return The LogBuilder.
     */
    @Override
    public LogBuilder withLocation(StackTraceElement location) {
        this.location = location;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param message The message to log.
     */
    @Override
    public void log(Message message) {
        if (isValid()) {
            logMessage(message);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message The message to log.
     */
    @Override
    public void log(CharSequence message) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message The message to log.
     */
    @Override
    public void log(String message) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message The message.
     * @param params  Parameters to the message.
     * @see org.apache.logging.log4j.util.Unbox
     */
    @Override
    public void log(String message, Object... params) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, params));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message The message.
     * @param params  Parameters to the message.
     */
    @Override
    public void log(String message, Supplier<?>... params) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, LambdaUtil.getAll(params)));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param messageSupplier The supplier of the message to log.
     */
    @Override
    public void log(Supplier<Message> messageSupplier) {
        if (isValid()) {
            logMessage(messageSupplier.get());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message The message to log.
     */
    @Override
    public void log(Object message) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     */
    @Override
    public void log(String message, Object p0) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     * @param p4      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3, p4));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     * @param p4      parameter to the message.
     * @param p5      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3, p4, p5));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     * @param p4      parameter to the message.
     * @param p5      parameter to the message.
     * @param p6      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3, p4, p5, p6));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     * @param p4      parameter to the message.
     * @param p5      parameter to the message.
     * @param p6      parameter to the message.
     * @param p7      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
                    Object p7) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     * @param p4      parameter to the message.
     * @param p5      parameter to the message.
     * @param p6      parameter to the message.
     * @param p7      parameter to the message.
     * @param p8      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
                    Object p7, Object p8) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param message the message to log; the format depends on the message factory.
     * @param p0      parameter to the message.
     * @param p1      parameter to the message.
     * @param p2      parameter to the message.
     * @param p3      parameter to the message.
     * @param p4      parameter to the message.
     * @param p5      parameter to the message.
     * @param p6      parameter to the message.
     * @param p7      parameter to the message.
     * @param p8      parameter to the message.
     * @param p9      parameter to the message.
     */
    @Override
    public void log(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
                    Object p7, Object p8, Object p9) {
        if (isValid()) {
            logMessage(loggerApi.getMessageFactory().newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log() {
        if (isValid()) {
            logMessage(EMPTY_MESSAGE);
        }
    }

    /**
     * Logs the message in {@link #logger}
     *
     * @param message the message to log
     */
    private void logMessage(Message message) {
        try {
            logger.logMessage(level, marker, location, message, throwable);
        } finally {
            inUse = false;
        }
    }

    /**
     * If the log is valid
     *
     * @return true if the log is valid
     */
    private boolean isValid() {
        if (!inUse) {
            LOGGER.warn("Attempt to reuse LogBuilder was ignored. {}",
                    StackLocatorUtil.getCallerClass(2));
            return false;
        }
        if (this.threadId != Thread.currentThread().getId()) {
            LOGGER.warn("LogBuilder can only be used on the owning thread. {}",
                    StackLocatorUtil.getCallerClass(2));
            return false;
        }
        return true;
    }

}
