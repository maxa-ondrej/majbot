/*
 *  majbot - cz.majksa.majbot.commands.CommandsManager
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

package cz.majksa.majbot.commands;

import cz.majksa.majbot.commands.request.Argument;
import cz.majksa.majbot.commands.request.IntChoices;
import cz.majksa.majbot.commands.request.Request;
import cz.majksa.majbot.commands.request.StringChoices;
import cz.majksa.majbot.permissions.Permissions;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p><b>Class {@link cz.majksa.majbot.commands.CommandsConvertor}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class CommandsConvertor {

    private final Permissions permissions;

    public Result parse(@NonNull Class<? extends ICommand<? extends Request>> command) {
        final Command annotation = command.getAnnotation(Command.class);
        return new Result(
                toCommandData(annotation),
                toPrivileges(annotation)
        );
    }

    @Getter
    @RequiredArgsConstructor
    public static class Result {

        private final @NonNull CommandData commandData;
        private final @NonNull List<CommandPrivilege> privileges;

    }

    private CommandData toCommandData(@NonNull Command annotation) {
        final CommandData commandData = new CommandData(annotation.name(), annotation.description());
        commandData.setDefaultEnabled(annotation.defaultPermission());
        commandData.addOptions(
                Arrays.stream(annotation.request().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(Argument.class))
                        .map(this::toOptionData)
                        .toArray(OptionData[]::new)
        );
        return commandData;
    }

    private @NonNull List<CommandPrivilege> toPrivileges(@NonNull Command annotation) {
        return permissions.getPermissions(annotation.permissionId())
                .entrySet()
                .stream()
                .map(entry -> new CommandPrivilege(entry.getKey().getType(), entry.getValue() == Permissions.Type.ALLOW, entry.getKey().getIdLong()))
                .collect(Collectors.toList());
    }

    private OptionData toOptionData(@NonNull Field field) {
        final Argument annotation = field.getAnnotation(Argument.class);
        final OptionType optionType;
        if (int.class.equals(field.getType()) || long.class.equals(field.getType())
                || Integer.class.equals(field.getType()) || Long.class.equals(field.getType())) {
            optionType = OptionType.INTEGER;
        } else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
            optionType = OptionType.BOOLEAN;
        } else if (String.class.equals(field.getType())) {
            optionType = OptionType.STRING;
        } else if (MessageChannel.class.equals(field.getType()) || GuildChannel.class.equals(field.getType())) {
            optionType = OptionType.CHANNEL;
        } else if (Member.class.equals(field.getType()) || User.class.equals(field.getType())) {
            optionType = OptionType.USER;
        } else if (Role.class.equals(field.getType())) {
            optionType = OptionType.ROLE;
        } else if (IMentionable.class.equals(field.getType())) {
            optionType = OptionType.MENTIONABLE;
        } else {
            throw new IllegalArgumentException("The provided class " + field.getType().getName() + " is not a valid argument type!");
        }
        final OptionData optionData = new OptionData(optionType, annotation.name(), annotation.description(), annotation.required());
        if (Integer.class.equals(field.getType())) {
            if (field.isAnnotationPresent(IntChoices.class)) {
                optionData.addChoices(
                        Arrays.stream(field.getAnnotation(IntChoices.class).value())
                                .map(choice -> new net.dv8tion.jda.api.interactions.commands.Command.Choice(choice.name(), choice.value()))
                                .collect(Collectors.toList())
                );
            }
        } else if (String.class.equals(field.getType())) {
            if (field.isAnnotationPresent(StringChoices.class)) {
                optionData.addChoices(
                        Arrays.stream(field.getAnnotation(StringChoices.class).value())
                                .map(choice -> new net.dv8tion.jda.api.interactions.commands.Command.Choice(choice.name(), choice.value()))
                                .collect(Collectors.toList())
                );
            }
        }
        return optionData;
    }

}
