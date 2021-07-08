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

    /**
     * Loading errors into memory on startup
     */
    void init();

    /**
     * Saves the throwable, if it isn't already saved
     *
     * @param throwable the {@link java.lang.Throwable} to be saved
     * @return the id to be used in {@link #get(String)} to get the throwable back
     */
    default @NonNull String save(@NonNull Throwable throwable) {
        try {
            return get(throwable);
        } catch (ThrowableNotFoundException e) {
            return save(SerializableThrowable.from(throwable));
        }
    }

    /**
     * Saves the throwable, if it isn't already saved
     *
     * @param throwable the {@link cz.majksa.majbot.logging.errors.SerializableThrowable} to be saved
     * @return the id to be used in {@link #get(String)} to get the throwable back
     */
    default @NonNull String save(@NonNull SerializableThrowable throwable) {
        try {
            return get(throwable);
        } catch (ThrowableNotFoundException e) {
            final String id = UUID.randomUUID().toString();
            save(throwable, id);
            return id;
        }
    }

    /**
     * Saves the throwable with the provided id
     *
     * @param throwable the throwable to be saved
     * @param id        the generated id
     */
    void save(@NonNull SerializableThrowable throwable, @NonNull String id);

    /**
     * Deletes the throwable by the {@link cz.majksa.majbot.logging.errors.SerializableThrowable}
     *
     * @param throwable the throwable to be deleted
     * @return the throwable
     */
    default SerializableThrowable delete(@NonNull SerializableThrowable throwable) {
        return delete(get(throwable));
    }

    /**
     * Deletes the throwable by the id
     *
     * @param id the id
     * @return the throwable
     */
    @NonNull SerializableThrowable delete(@NonNull String id);

    /**
     * Finds the saved throwable by the provided id
     *
     * @param id the id to get the throwable by
     * @return the found throwable
     * @throws cz.majksa.majbot.logging.errors.ThrowableNotFoundException if the id does not exist
     */
    default @NonNull SerializableThrowable get(@NonNull String id) throws ThrowableNotFoundException {
        final Map<String, SerializableThrowable> map = map();
        if (!map.containsKey(id)) {
            throw new ThrowableNotFoundException();
        }
        return map.get(id);
    }

    /**
     * Finds the id by the saved throwable
     *
     * @param throwable the throwable to find id by
     * @return the found id
     * @throws cz.majksa.majbot.logging.errors.ThrowableNotFoundException if the throwable does not exist
     */
    default @NonNull String get(@NonNull Throwable throwable) throws ThrowableNotFoundException {
        for (SerializableThrowable value : map().values()) {
            if (value.compare(throwable)) {
                return get(value);
            }
        }
        throw new ThrowableNotFoundException();
    }

    /**
     * Finds the id by the saved throwable
     *
     * @param throwable the throwable to find id by
     * @return the found id
     * @throws cz.majksa.majbot.logging.errors.ThrowableNotFoundException if the throwable does not exist
     */
    default @NonNull String get(@NonNull SerializableThrowable throwable) throws ThrowableNotFoundException {
        for (Map.Entry<String, SerializableThrowable> entry : map().entrySet()) {
            if (entry.getValue().equals(throwable)) {
                return entry.getKey();
            }
        }
        throw new ThrowableNotFoundException();
    }

    /**
     * Lists the saved ids
     *
     * @return the {@link java.util.Set} of ids
     */
    default @NonNull Set<String> ids() {
        return map().keySet();
    }

    /**
     * Map of all values
     *
     * @return the {@link java.util.Map}
     */
    @NonNull Map<String, SerializableThrowable> map();

    /**
     * Clears the whole database
     */
    default void clear() {
        map().forEach((id, throwable) -> delete(id));
    }

}
