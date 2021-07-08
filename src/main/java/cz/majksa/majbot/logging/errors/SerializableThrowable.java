/*
 *  majbot - cz.majksa.majbot.logging.errors.SerializableThrowable
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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Class {@link cz.majksa.majbot.logging.errors.SerializableThrowable}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializableThrowable implements Serializable {

    public static final String NO_MESSAGE = "No message";

    private static final long serialVersionUID = 8451803073914264980L;
    private static final Map<Throwable, SerializableThrowable> throwables = new HashMap<>();

    private String message;
    private String className;
    private StackTraceElement[] stackTrace;

    public static SerializableThrowable from(Throwable throwable) {
        if (!throwables.containsKey(throwable)) {
            throwables.computeIfAbsent(throwable, unused -> new SerializableThrowable(
                    throwable.getMessage() == null ? NO_MESSAGE : throwable.getMessage(),
                    throwable.getClass().getName(),
                    throwable.getStackTrace()
            ));
        }
        return throwables.get(throwable);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(message);
        stream.writeObject(className);
        stream.writeObject(stackTrace);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        message = (String) stream.readObject();
        className = (String) stream.readObject();
        stackTrace = (StackTraceElement[]) stream.readObject();
    }

    public boolean compare(Throwable throwable) {
        if (!message.equals(throwable.getMessage())) {
            return false;
        }
        if (!className.equals(throwable.getClass().getName())) {
            return false;
        }
        return Arrays.equals(throwable.getStackTrace(), stackTrace);
    }

}
