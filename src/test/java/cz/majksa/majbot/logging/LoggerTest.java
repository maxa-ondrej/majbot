/*
 *  majbot - cz.majksa.majbot.logging.LoggerTest
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

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    public static final Logger LOGGER = new Logger(LogManager.getLogger());

    @Test
    void shouldCallListener() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        LOGGER.listen(Level.ERROR, logEvent -> atomicBoolean.set(true));
        LOGGER.atError()
                .withLocation()
                .log("test error");
        assertTrue(atomicBoolean.get());
    }

    @Test
    void shouldNotCallListener() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        LOGGER.listen(Level.ERROR, logEvent -> atomicBoolean.set(true));
        LOGGER.atDebug()
                .withLocation()
                .log("test debug");
        assertFalse(atomicBoolean.get());
    }

}