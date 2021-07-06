/*
 *  majbot - cz.majksa.majbot.MajBot
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

package cz.majksa.majbot;

import cz.majksa.majbot.core.Server;
import cz.majksa.majbot.core.ServerImpl;
import cz.majksa.majbot.exceptions.AlreadyActivatedException;
import cz.majksa.majbot.exceptions.NotActivatedException;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Class {@link cz.majksa.majbot.MajBot}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class MajBot {

    private static final Map<Guild, Server> servers = new HashMap<>();

    private static JDA api = null;

    public static boolean isActivated() {
        return api != null;
    }

    public static void activate(@NonNull JDA jda) {
        if (isActivated()) {
            throw new AlreadyActivatedException();
        }
        api = jda;
        try {
            api.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deactivate() {
        if (!isActivated()) {
            throw new NotActivatedException();
        }
        api.shutdown();
        api = null;
    }

    public static Server get(@NonNull Guild guild) {
        if (!servers.containsKey(guild)) {
            servers.put(guild, new ServerImpl(guild));
        }
        return servers.get(guild);
    }

}
