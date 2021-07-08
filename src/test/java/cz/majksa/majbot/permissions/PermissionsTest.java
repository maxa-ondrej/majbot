/*
 *  majbot - cz.majksa.majbot.permissions.PermissionsTest
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

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionsTest {

    private Permissions permissions;

    @BeforeEach
    void setUp() {
        permissions = new Permissions();
    }

    @Test
    void shouldBeAllowed() {
        final PermissionHolder holder = new PermissionHolder(PermissionHolder.Type.USER, 1000000000000L);
        final PermissionHolder holder2 = new PermissionHolder(PermissionHolder.Type.ROLE, 1000000000000L);
        permissions.allow("command.test", holder);
        assertTrue(permissions.getAllowed("command.test").contains(holder));
        assertFalse(permissions.getAllowed("command.test").contains(holder2));
    }

    @Test
    void listeners() {
        final MyListener listener = new MyListener("command.test");
        final PermissionHolder holder = new PermissionHolder(PermissionHolder.Type.USER, 1000000000000L);
        permissions.allow("command.test", holder);
        assertFalse(listener.isChanged());
        permissions.registerListener(listener);
        permissions.deny("command.test", holder);
        assertTrue(listener.isChanged());
        listener.reset();
        permissions.deny("command.test", holder);
        assertFalse(listener.isChanged());

    }

    @Getter
    @RequiredArgsConstructor
    static class MyListener implements PermissionsListener {

        private final String id;
        private boolean changed = false;

        public void reset() {
            changed = false;
        }

        @Override
        public void onPermissionChanged(@NonNull String id, @NonNull PermissionHolder holder, @NonNull Permissions.Type type, @Nullable Permissions.Type old) {
            if (this.id.equals(id)) {
                changed = true;
            }
        }

    }

}