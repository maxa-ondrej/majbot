/*
 *  majbot - cz.majksa.majbot.permissions.Permissions
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p><b>Class {@link cz.majksa.majbot.permissions.Permissions}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Permissions {

    private final Map<String, Map<PermissionHolder, Type>> permissionMap = new HashMap<>();
    @Getter
    private final List<PermissionsListener> listeners = new ArrayList<>();

    public Map<PermissionHolder, Type> getPermissions(@NonNull String permissionId) {
        permissionMap.computeIfAbsent(permissionId, key -> new HashMap<>());
        return permissionMap.get(permissionId);
    }

    public List<PermissionHolder> getPermissions(@NonNull String permissionId, @NonNull Type type) {
        return getPermissions(permissionId)
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == type)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<PermissionHolder> getAllowed(@NonNull String permissionId) {
        return getPermissions(permissionId, Type.ALLOW);
    }

    public List<PermissionHolder> getDenied(@NonNull String permissionId) {
        return getPermissions(permissionId, Type.DENY);
    }

    public void set(@NonNull String permissionId, @NonNull PermissionHolder permissionHolder, @NonNull Type type) {
        final Map<PermissionHolder, Type> permissions = getPermissions(permissionId);
        final Type current = permissions.getOrDefault(permissionHolder, null);
        if (current != null) {
            if (current == type) {
                return;
            }
        }
        permissions.put(permissionHolder, type);
        listeners.forEach(listener -> listener.onPermissionChanged(permissionId, permissionHolder, type, current));
    }

    public void allow(@NonNull String permissionId, @NonNull PermissionHolder permissionHolder) {
        set(permissionId, permissionHolder, Type.ALLOW);
    }

    public void deny(@NonNull String permissionId, @NonNull PermissionHolder permissionHolder) {
        set(permissionId, permissionHolder, Type.DENY);
    }

    public void registerListener(@NonNull PermissionsListener listener) {
        listeners.add(listener);
    }

    public void removeListener(@NonNull PermissionsListener listener) {
        listeners.remove(listener);
    }

    public enum Type {
        ALLOW,
        DENY
    }

}
