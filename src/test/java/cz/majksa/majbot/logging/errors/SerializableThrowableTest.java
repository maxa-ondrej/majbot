/*
 *  majbot - cz.majksa.majbot.logging.errors.SerializableThrowableTest
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

package cz.majksa.majbot.logging.errors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerializableThrowableTest {

    final RuntimeException throwable1 = exception1(); final RuntimeException throwable2 = exception1(); final RuntimeException throwable3 = exception2();

    @Test
    void from() {
        final SerializableThrowable serializableThrowable1 = SerializableThrowable.from(throwable1);
        final SerializableThrowable serializableThrowable2 = SerializableThrowable.from(throwable2);
        final SerializableThrowable serializableThrowable3 = SerializableThrowable.from(throwable3);
        assertEquals(serializableThrowable1, serializableThrowable2);
        assertNotEquals(serializableThrowable3, serializableThrowable2);
    }

    @Test
    void compare() {
        final SerializableThrowable serializableThrowable = SerializableThrowable.from(throwable1);
        assertTrue(serializableThrowable.compare(throwable1));
        assertTrue(serializableThrowable.compare(throwable2));
        assertFalse(serializableThrowable.compare(throwable3));
    }

    private RuntimeException exception1() {
        return new RuntimeException();
    }

    private RuntimeException exception2() {
        return new RuntimeException();
    }

}