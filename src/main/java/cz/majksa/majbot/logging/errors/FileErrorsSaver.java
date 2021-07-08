/*
 *  majbot - cz.majksa.majbot.logging.errors.FileErrorsSaver
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

import cz.majksa.majbot.utils.FileUtils;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Class {@link cz.majksa.majbot.logging.errors.FileErrorsSaver}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileErrorsSaver implements ErrorsSaver {

    public static final String SUFFIX = ".ser";

    private final @NonNull File folder;

    private final Map<String, SerializableThrowable> throwables = new HashMap<>();

    public FileErrorsSaver(@NonNull File folder) throws NotDirectoryException {
        this.folder = folder;
        if (!folder.isDirectory()) {
            throw new NotDirectoryException(folder.getAbsolutePath());
        }
    }

    @Override
    public void init() {
        final File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(SUFFIX));
        if (files == null) {
            return;
        }
        for (File file : files) {
            final String id = file.getName().substring(0, file.getName().length() - SUFFIX.length());
            final SerializableThrowable throwable = FileUtils.read(file, SerializableThrowable.class);
            throwables.put(id, throwable);
        }
    }

    @Override
    public void save(@NonNull SerializableThrowable throwable, @NonNull String id) {
        FileUtils.write(getFile(id), throwable);
    }

    @Override
    public @NotNull SerializableThrowable delete(@NonNull String id) {
        final SerializableThrowable throwable = throwables.remove(id);
        final File file = getFile(id);
        if (!file.delete()) {
            if (file.exists()) {
                file.deleteOnExit();
            }
        }
        return throwable;
    }

    @Override
    public @NonNull Map<String, SerializableThrowable> map() {
        return new HashMap<>(throwables);
    }

    private @NonNull File getFile(@NonNull String id) {
        return new File(folder, id + SUFFIX);
    }

}
