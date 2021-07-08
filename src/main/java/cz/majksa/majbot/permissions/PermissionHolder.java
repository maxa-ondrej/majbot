/*
 *  majbot - cz.majksa.majbot.permissions.PermissionHolder
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

package cz.majksa.majbot.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.ISnowflake;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <p><b>Interface {@link cz.majksa.majbot.permissions.PermissionHolder}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class PermissionHolder implements ISnowflake, Serializable {

    private static final long serialVersionUID = -8461702559356460582L;

    private Type type;
    private long idLong;

    /**
     * The function for serialization saving
     *
     * @param stream the output stream
     * @throws java.io.IOException if an error occurs
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(type.id);
        stream.writeLong(idLong);
    }

    /**
     * The function for serialization reading
     *
     * @param stream the input stream
     * @throws IOException            if an error occurs
     * @throws ClassNotFoundException if the classes do not match
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        type = Type.findById(stream.readInt());
        idLong = stream.readLong();
    }

    @Getter
    @RequiredArgsConstructor
    enum Type {
        ROLE(1),
        USER(2);

        private final int id;

        public static @NonNull Type findById(int id) {
            switch (id) {
                case 1:
                    return ROLE;
                case 2:
                    return USER;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

}
