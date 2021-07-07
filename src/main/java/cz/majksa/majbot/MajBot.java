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
import cz.majksa.majbot.listeners.Listeners;
import cz.majksa.majbot.logging.Logger;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Class {@link cz.majksa.majbot.MajBot}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */

public final class MajBot {

    public static final String VERSION = "1.0.0";
    public static final String JDA_VERSION = "4.3.0_293";

    private static final Map<JDA, MajBot> bots = new HashMap<>();

    @Getter
    private final Logger logger = new Logger(LogManager.getLogger());
    private final Map<Guild, Server> servers = new HashMap<>();
    private final JDA api;
    @Getter
    private final Listeners listeners;

    private MajBot(JDA api) {
        this.api = api;
        listeners = new Listeners(api);
    }

    public static @NonNull MajBot get(@NonNull JDA jda) {
        bots.computeIfAbsent(jda, MajBot::new);
        return bots.get(jda);
    }

    public void start() {
        try {
            api.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        listeners.start();
    }

    public void shutdown() {
        listeners.shutdown();
        bots.remove(api);
    }

    public Server getServer(@NonNull Guild guild) {
        if (!servers.containsKey(guild)) {
            servers.put(guild, new ServerImpl(this, guild));
        }
        return servers.get(guild);
    }

}
