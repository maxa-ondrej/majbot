/*
 *  majbot - cz.majksa.majbot.utils.FileUtils
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

package cz.majksa.majbot.utils;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * <p><b>Class {@link cz.majksa.majbot.utils.FileUtils}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileUtils {

    /**
     * Reads a serializable file
     *
     * @param file  the file to be read
     * @param clazz the serializable class
     * @param <T>   the type of serializable
     * @return the read serializable object
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static  <T extends Serializable> T read(@NonNull File file, @NonNull Class<T> clazz) {
        final FileInputStream fileInputStream = new FileInputStream(file);
        final ObjectInputStream objectOutputStream = new ObjectInputStream(fileInputStream);
        final Object o = objectOutputStream.readObject();
        final T object = (T) o;
        objectOutputStream.close();
        fileInputStream.close();
        return object;
    }

    /**
     * Writes a serializable file
     *
     * @param file the file to be written
     * @param o    the object to be written
     * @param <T>  the type of serializable
     */
    @SneakyThrows
    public static <T extends Serializable> void write(@NonNull File file, @NonNull T o) {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(o);
            }
        }
    }

    /**
     * Creates or truncates a file
     *
     * @param file the file to create
     * @return the file itself
     */
    @SneakyThrows
    public static @NonNull File create(@NonNull File file) {
        return create(file, true);
    }

    /**
     * Creates or truncates a file
     *
     * @param file     the file to create
     * @param truncate whether the file should be created if it already exists
     * @return the file itself
     */
    @SneakyThrows
    public static @NonNull File create(@NonNull File file, boolean truncate) {
        if (file.exists()) {
            if (truncate) {
                final PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
        } else {
            if (!file.createNewFile()) {
                throw new IOException("File " + file.getAbsolutePath() + " could not have been created!");
            }
        }
        return file;
    }

}
