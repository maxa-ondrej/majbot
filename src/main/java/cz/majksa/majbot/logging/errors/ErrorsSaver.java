/*
 *  majbot - cz.majksa.majbot.logging.errors.ErrorsSaver
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

import lombok.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * <p><b>Interface {@link ErrorsSaver}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ErrorsSaver {

    void init();

    default @NonNull String save(@NonNull Throwable throwable) {
        try {
            return get(throwable);
        } catch (ThrowableNotFoundException e) {
            return save(SerializableThrowable.from(throwable));
        }
    }

    default @NonNull String save(@NonNull SerializableThrowable throwable) {
        try {
            return get(throwable);
        } catch (ThrowableNotFoundException e) {
            final String id = UUID.randomUUID().toString();
            save(throwable, id);
            return id;
        }
    }

    void save(@NonNull SerializableThrowable throwable, @NonNull String id);

    default SerializableThrowable delete(@NonNull SerializableThrowable throwable) {
        return delete(get(throwable));
    }

    @NonNull SerializableThrowable delete(@NonNull String id);

    default @NonNull SerializableThrowable get(@NonNull String id) {
        return map().get(id);
    }

    default @NonNull String get(@NonNull Throwable throwable) throws ThrowableNotFoundException {
        for (SerializableThrowable value : map().values()) {
            if (value.compare(throwable)) {
                return get(value);
            }
        }
        throw new ThrowableNotFoundException();
    }

    default @NonNull String get(@NonNull SerializableThrowable throwable) throws ThrowableNotFoundException {
        for (Map.Entry<String, SerializableThrowable> entry : map().entrySet()) {
            if (entry.getValue().equals(throwable)) {
                return entry.getKey();
            }
        }
        throw new ThrowableNotFoundException();
    }

    default @NonNull Set<String> keys() {
        return map().keySet();
    }

    @NonNull Map<String, SerializableThrowable> map();

    default void clear() {
        map().forEach((id, throwable) -> delete(id));
    }

}
