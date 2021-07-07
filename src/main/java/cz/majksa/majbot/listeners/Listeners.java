/*
 *  majbot - cz.majksa.majbot.listeners.Listeners
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

package cz.majksa.majbot.listeners;

import cz.majksa.majbot.MajBot;
import cz.majksa.majbot.logging.Logger;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.UpdateEvent;
import net.dv8tion.jda.api.events.application.ApplicationCommandCreateEvent;
import net.dv8tion.jda.api.events.application.ApplicationCommandDeleteEvent;
import net.dv8tion.jda.api.events.application.ApplicationCommandUpdateEvent;
import net.dv8tion.jda.api.events.application.GenericApplicationCommandEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.GenericCategoryEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent;
import net.dv8tion.jda.api.events.channel.category.update.GenericCategoryUpdateEvent;
import net.dv8tion.jda.api.events.channel.store.GenericStoreChannelEvent;
import net.dv8tion.jda.api.events.channel.store.StoreChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.store.StoreChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.store.update.GenericStoreChannelUpdateEvent;
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdatePositionEvent;
import net.dv8tion.jda.api.events.channel.text.GenericTextChannelEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.GenericTextChannelUpdateEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNSFWEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNewsEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateParentEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdatePositionEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateSlowmodeEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.api.events.channel.voice.GenericVoiceChannelEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.GenericVoiceChannelUpdateEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateBitrateEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateParentEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdatePositionEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateRegionEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.emote.GenericEmoteEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateRolesEvent;
import net.dv8tion.jda.api.events.emote.update.GenericEmoteUpdateEvent;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.GuildAvailableEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildTimeoutEvent;
import net.dv8tion.jda.api.events.guild.GuildUnavailableEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.UnavailableGuildJoinedEvent;
import net.dv8tion.jda.api.events.guild.UnavailableGuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.events.guild.member.update.GenericGuildMemberUpdateEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent;
import net.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent;
import net.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkChannelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkTimeoutEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBannerEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostTierEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateCommunityUpdatesChannelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateDescriptionEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateExplicitContentLevelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateFeaturesEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateLocaleEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateMFALevelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateMaxMembersEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateMaxPresencesEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNotificationLevelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateOwnerEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateRulesChannelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateSplashEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateSystemChannelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateVanityCodeEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateVerificationLevelEvent;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceRequestToSpeakEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSuppressEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.http.HttpRequestEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEmoteEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.priv.GenericPrivateMessageEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageUpdateEvent;
import net.dv8tion.jda.api.events.message.priv.react.GenericPrivateMessageReactionEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEmoteEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.role.GenericRoleEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateHoistedEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateMentionableEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;
import net.dv8tion.jda.api.events.self.GenericSelfUpdateEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateAvatarEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateMFAEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateNameEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateVerifiedEvent;
import net.dv8tion.jda.api.events.stage.GenericStageInstanceEvent;
import net.dv8tion.jda.api.events.stage.StageInstanceCreateEvent;
import net.dv8tion.jda.api.events.stage.StageInstanceDeleteEvent;
import net.dv8tion.jda.api.events.stage.update.GenericStageInstanceUpdateEvent;
import net.dv8tion.jda.api.events.stage.update.StageInstanceUpdatePrivacyLevelEvent;
import net.dv8tion.jda.api.events.stage.update.StageInstanceUpdateTopicEvent;
import net.dv8tion.jda.api.events.user.GenericUserEvent;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivitiesEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivityOrderEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateFlagsEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Class {@link cz.majksa.majbot.listeners.Listeners}</b></p>
 *
 * @author majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class Listeners  extends ListenerAdapter {

    /**
     * The entry points are registered here.
     *
     * @see cz.majksa.majbot.listeners.EntryPointList
     */
    @Getter
    private final Map<Class<? extends GenericEvent>, EntryPointList<? extends GenericEvent>> entryPointsMap = new HashMap<>();
    private final @NonNull JDA jda;
    private final @NonNull MajBot majBot;
    private final @NonNull Logger logger;

    private boolean running = false;

    public Listeners(@NonNull JDA jda) {
        this.jda = jda;
        majBot = MajBot.get(jda);
        logger = majBot.getLogger();
    }

    public void start() {
        logger.atDebug().log("Trying to start discord listeners");
        if (!running) {
            logger.atInfo().log("Starting discord listeners");
            jda.addEventListener(this);
            running = true;
        }
    }

    public void shutdown() {
        logger.atDebug().log("Trying to stop discord listeners");
        if (running) {
            logger.atInfo().log("Stopping discord listeners");
            jda.removeEventListener(this);
            running = false;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends GenericEvent> @NonNull EntryPoint<T> loadListener(@NonNull IListener<T> listener) {
        final Class<T> eventClass = (Class<T>) listener.getAnnotation().value();
        EntryPoint<T> entryPoint = new EntryPoint<>(eventClass, listener::run, listener::check, get(eventClass));
        entryPoint.register();
        return entryPoint;
    }

    /**
     * Find the corresponding {@link cz.majksa.majbot.listeners.EntryPointList} by the given class with generics.
     *
     * @param clazz the class of the event type
     * @param <T> the type of the event
     * @return the {@link cz.majksa.majbot.listeners.EntryPointList} corresponding to the given class
     */
    @SuppressWarnings("unchecked")
    public <T extends GenericEvent> @NonNull EntryPointList<T> get(@NonNull Class<T> clazz) {
        entryPointsMap.putIfAbsent(clazz, new EntryPointList<>(majBot));
        return (EntryPointList<T>) entryPointsMap.get(clazz);
    }

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        get(GenericEvent.class).run(event);
    }

    @Override
    public void onGenericUpdate(@NotNull UpdateEvent<?, ?> event) {
        get(UpdateEvent.class).run(event);
    }

    @Override
    public void onRawGateway(@NotNull RawGatewayEvent event) {
        get(RawGatewayEvent.class).run(event);
    }

    @Override
    public void onGatewayPing(@NotNull GatewayPingEvent event) {
        get(GatewayPingEvent.class).run(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        get(ReadyEvent.class).run(event);
    }

    @Override
    public void onDisconnect(@NotNull DisconnectEvent event) {
        get(DisconnectEvent.class).run(event);
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        get(ShutdownEvent.class).run(event);
    }

    @Override
    public void onStatusChange(@NotNull StatusChangeEvent event) {
        get(StatusChangeEvent.class).run(event);
    }

    @Override
    public void onException(@NotNull ExceptionEvent event) {
        get(ExceptionEvent.class).run(event);
    }

    @Override
    public void onUserUpdateName(@NotNull UserUpdateNameEvent event) {
        get(UserUpdateNameEvent.class).run(event);
    }

    @Override
    public void onUserUpdateDiscriminator(@NotNull UserUpdateDiscriminatorEvent event) {
        get(UserUpdateDiscriminatorEvent.class).run(event);
    }

    @Override
    public void onUserUpdateAvatar(@NotNull UserUpdateAvatarEvent event) {
        get(UserUpdateAvatarEvent.class).run(event);
    }

    @Override
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
        get(UserUpdateOnlineStatusEvent.class).run(event);
    }

    @Override
    public void onUserUpdateActivityOrder(@NotNull UserUpdateActivityOrderEvent event) {
        get(UserUpdateActivityOrderEvent.class).run(event);
    }

    @Override
    public void onUserUpdateFlags(@NotNull UserUpdateFlagsEvent event) {
        get(UserUpdateFlagsEvent.class).run(event);
    }

    @Override
    public void onUserTyping(@NotNull UserTypingEvent event) {
        get(UserTypingEvent.class).run(event);
    }

    @Override
    public void onUserActivityStart(@NotNull UserActivityStartEvent event) {
        get(UserActivityStartEvent.class).run(event);
    }

    @Override
    public void onUserActivityEnd(@NotNull UserActivityEndEvent event) {
        get(UserActivityEndEvent.class).run(event);
    }

    @Override
    public void onSelfUpdateAvatar(@NotNull SelfUpdateAvatarEvent event) {
        get(SelfUpdateAvatarEvent.class).run(event);
    }

    @Override
    public void onSelfUpdateMFA(@NotNull SelfUpdateMFAEvent event) {
        get(SelfUpdateMFAEvent.class).run(event);
    }

    @Override
    public void onSelfUpdateName(@NotNull SelfUpdateNameEvent event) {
        get(SelfUpdateNameEvent.class).run(event);
    }

    @Override
    public void onSelfUpdateVerified(@NotNull SelfUpdateVerifiedEvent event) {
        get(SelfUpdateVerifiedEvent.class).run(event);
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        get(GuildMessageReceivedEvent.class).run(event);
    }

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        get(GuildMessageUpdateEvent.class).run(event);
    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {
        get(GuildMessageDeleteEvent.class).run(event);
    }

    @Override
    public void onGuildMessageEmbed(@NotNull GuildMessageEmbedEvent event) {
        get(GuildMessageEmbedEvent.class).run(event);
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        get(GuildMessageReactionAddEvent.class).run(event);
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        get(GuildMessageReactionRemoveEvent.class).run(event);
    }

    @Override
    public void onGuildMessageReactionRemoveAll(@NotNull GuildMessageReactionRemoveAllEvent event) {
        get(GuildMessageReactionRemoveAllEvent.class).run(event);
    }

    @Override
    public void onGuildMessageReactionRemoveEmote(@NotNull GuildMessageReactionRemoveEmoteEvent event) {
        get(GuildMessageReactionRemoveEmoteEvent.class).run(event);
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        get(PrivateMessageReceivedEvent.class).run(event);
    }

    @Override
    public void onPrivateMessageUpdate(@NotNull PrivateMessageUpdateEvent event) {
        get(PrivateMessageUpdateEvent.class).run(event);
    }

    @Override
    public void onPrivateMessageDelete(@NotNull PrivateMessageDeleteEvent event) {
        get(PrivateMessageDeleteEvent.class).run(event);
    }

    @Override
    public void onPrivateMessageEmbed(@NotNull PrivateMessageEmbedEvent event) {
        get(PrivateMessageEmbedEvent.class).run(event);
    }

    @Override
    public void onPrivateMessageReactionAdd(@NotNull PrivateMessageReactionAddEvent event) {
        get(PrivateMessageReactionAddEvent.class).run(event);
    }

    @Override
    public void onPrivateMessageReactionRemove(@NotNull PrivateMessageReactionRemoveEvent event) {
        get(PrivateMessageReactionRemoveEvent.class).run(event);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        get(MessageReceivedEvent.class).run(event);
    }

    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {
        get(MessageUpdateEvent.class).run(event);
    }

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        get(MessageDeleteEvent.class).run(event);
    }

    @Override
    public void onMessageBulkDelete(@NotNull MessageBulkDeleteEvent event) {
        get(MessageBulkDeleteEvent.class).run(event);
    }

    @Override
    public void onMessageEmbed(@NotNull MessageEmbedEvent event) {
        get(MessageEmbedEvent.class).run(event);
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        get(MessageReactionAddEvent.class).run(event);
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        get(MessageReactionRemoveEvent.class).run(event);
    }

    @Override
    public void onMessageReactionRemoveAll(@NotNull MessageReactionRemoveAllEvent event) {
        get(MessageReactionRemoveAllEvent.class).run(event);
    }

    @Override
    public void onMessageReactionRemoveEmote(@NotNull MessageReactionRemoveEmoteEvent event) {
        get(MessageReactionRemoveEmoteEvent.class).run(event);
    }

    @Override
    public void onPermissionOverrideDelete(@NotNull PermissionOverrideDeleteEvent event) {
        get(PermissionOverrideDeleteEvent.class).run(event);
    }

    @Override
    public void onPermissionOverrideUpdate(@NotNull PermissionOverrideUpdateEvent event) {
        get(PermissionOverrideUpdateEvent.class).run(event);
    }

    @Override
    public void onPermissionOverrideCreate(@NotNull PermissionOverrideCreateEvent event) {
        get(PermissionOverrideCreateEvent.class).run(event);
    }

    @Override
    public void onStoreChannelDelete(@NotNull StoreChannelDeleteEvent event) {
        get(StoreChannelDeleteEvent.class).run(event);
    }

    @Override
    public void onStoreChannelUpdateName(@NotNull StoreChannelUpdateNameEvent event) {
        get(StoreChannelUpdateNameEvent.class).run(event);
    }

    @Override
    public void onStoreChannelUpdatePosition(@NotNull StoreChannelUpdatePositionEvent event) {
        get(StoreChannelUpdatePositionEvent.class).run(event);
    }

    @Override
    public void onStoreChannelCreate(@NotNull StoreChannelCreateEvent event) {
        get(StoreChannelCreateEvent.class).run(event);
    }

    @Override
    public void onTextChannelDelete(@NotNull TextChannelDeleteEvent event) {
        get(TextChannelDeleteEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdateName(@NotNull TextChannelUpdateNameEvent event) {
        get(TextChannelUpdateNameEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdateTopic(@NotNull TextChannelUpdateTopicEvent event) {
        get(TextChannelUpdateTopicEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdatePosition(@NotNull TextChannelUpdatePositionEvent event) {
        get(TextChannelUpdatePositionEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdateNSFW(@NotNull TextChannelUpdateNSFWEvent event) {
        get(TextChannelUpdateNSFWEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdateParent(@NotNull TextChannelUpdateParentEvent event) {
        get(TextChannelUpdateParentEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdateSlowmode(@NotNull TextChannelUpdateSlowmodeEvent event) {
        get(TextChannelUpdateSlowmodeEvent.class).run(event);
    }

    @Override
    public void onTextChannelUpdateNews(@NotNull TextChannelUpdateNewsEvent event) {
        get(TextChannelUpdateNewsEvent.class).run(event);
    }

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        get(TextChannelCreateEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelDelete(@NotNull VoiceChannelDeleteEvent event) {
        get(VoiceChannelDeleteEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelUpdateName(@NotNull VoiceChannelUpdateNameEvent event) {
        get(VoiceChannelUpdateNameEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelUpdatePosition(@NotNull VoiceChannelUpdatePositionEvent event) {
        get(VoiceChannelUpdatePositionEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelUpdateUserLimit(@NotNull VoiceChannelUpdateUserLimitEvent event) {
        get(VoiceChannelUpdateUserLimitEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelUpdateBitrate(@NotNull VoiceChannelUpdateBitrateEvent event) {
        get(VoiceChannelUpdateBitrateEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelUpdateParent(@NotNull VoiceChannelUpdateParentEvent event) {
        get(VoiceChannelUpdateParentEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelCreate(@NotNull VoiceChannelCreateEvent event) {
        get(VoiceChannelCreateEvent.class).run(event);
    }

    @Override
    public void onCategoryDelete(@NotNull CategoryDeleteEvent event) {
        get(CategoryDeleteEvent.class).run(event);
    }

    @Override
    public void onCategoryUpdateName(@NotNull CategoryUpdateNameEvent event) {
        get(CategoryUpdateNameEvent.class).run(event);
    }

    @Override
    public void onCategoryUpdatePosition(@NotNull CategoryUpdatePositionEvent event) {
        get(CategoryUpdatePositionEvent.class).run(event);
    }

    @Override
    public void onCategoryCreate(@NotNull CategoryCreateEvent event) {
        get(CategoryCreateEvent.class).run(event);
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        get(GuildReadyEvent.class).run(event);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        get(GuildJoinEvent.class).run(event);
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        get(GuildLeaveEvent.class).run(event);
    }

    @Override
    public void onGuildAvailable(@NotNull GuildAvailableEvent event) {
        get(GuildAvailableEvent.class).run(event);
    }

    @Override
    public void onGuildUnavailable(@NotNull GuildUnavailableEvent event) {
        get(GuildUnavailableEvent.class).run(event);
    }

    @Override
    public void onUnavailableGuildJoined(@NotNull UnavailableGuildJoinedEvent event) {
        get(UnavailableGuildJoinedEvent.class).run(event);
    }

    @Override
    public void onUnavailableGuildLeave(@NotNull UnavailableGuildLeaveEvent event) {
        get(UnavailableGuildLeaveEvent.class).run(event);
    }

    @Override
    public void onGuildBan(@NotNull GuildBanEvent event) {
        get(GuildBanEvent.class).run(event);
    }

    @Override
    public void onGuildUnban(@NotNull GuildUnbanEvent event) {
        get(GuildUnbanEvent.class).run(event);
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        get(GuildMemberRemoveEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateAfkChannel(@NotNull GuildUpdateAfkChannelEvent event) {
        get(GuildUpdateAfkChannelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateSystemChannel(@NotNull GuildUpdateSystemChannelEvent event) {
        get(GuildUpdateSystemChannelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateAfkTimeout(@NotNull GuildUpdateAfkTimeoutEvent event) {
        get(GuildUpdateAfkTimeoutEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateExplicitContentLevel(@NotNull GuildUpdateExplicitContentLevelEvent event) {
        get(GuildUpdateExplicitContentLevelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateIcon(@NotNull GuildUpdateIconEvent event) {
        get(GuildUpdateIconEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateMFALevel(@NotNull GuildUpdateMFALevelEvent event) {
        get(GuildUpdateMFALevelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateName(@NotNull GuildUpdateNameEvent event) {
        get(GuildUpdateNameEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateNotificationLevel(@NotNull GuildUpdateNotificationLevelEvent event) {
        get(GuildUpdateNotificationLevelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateOwner(@NotNull GuildUpdateOwnerEvent event) {
        get(GuildUpdateOwnerEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateSplash(@NotNull GuildUpdateSplashEvent event) {
        get(GuildUpdateSplashEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateVerificationLevel(@NotNull GuildUpdateVerificationLevelEvent event) {
        get(GuildUpdateVerificationLevelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateLocale(@NotNull GuildUpdateLocaleEvent event) {
        get(GuildUpdateLocaleEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateFeatures(@NotNull GuildUpdateFeaturesEvent event) {
        get(GuildUpdateFeaturesEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateVanityCode(@NotNull GuildUpdateVanityCodeEvent event) {
        get(GuildUpdateVanityCodeEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateBanner(@NotNull GuildUpdateBannerEvent event) {
        get(GuildUpdateBannerEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateDescription(@NotNull GuildUpdateDescriptionEvent event) {
        get(GuildUpdateDescriptionEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateBoostTier(@NotNull GuildUpdateBoostTierEvent event) {
        get(GuildUpdateBoostTierEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateBoostCount(@NotNull GuildUpdateBoostCountEvent event) {
        get(GuildUpdateBoostCountEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateMaxMembers(@NotNull GuildUpdateMaxMembersEvent event) {
        get(GuildUpdateMaxMembersEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateMaxPresences(@NotNull GuildUpdateMaxPresencesEvent event) {
        get(GuildUpdateMaxPresencesEvent.class).run(event);
    }

    @Override
    public void onGuildInviteCreate(@NotNull GuildInviteCreateEvent event) {
        get(GuildInviteCreateEvent.class).run(event);
    }

    @Override
    public void onGuildInviteDelete(@NotNull GuildInviteDeleteEvent event) {
        get(GuildInviteDeleteEvent.class).run(event);
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        get(GuildMemberJoinEvent.class).run(event);
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        get(GuildMemberRoleAddEvent.class).run(event);
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        get(GuildMemberRoleRemoveEvent.class).run(event);
    }

    @Override
    public void onGuildMemberUpdate(@NotNull GuildMemberUpdateEvent event) {
        get(GuildMemberUpdateEvent.class).run(event);
    }

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        get(GuildMemberUpdateNicknameEvent.class).run(event);
    }

    @Override
    public void onGuildMemberUpdateBoostTime(@NotNull GuildMemberUpdateBoostTimeEvent event) {
        get(GuildMemberUpdateBoostTimeEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        get(GuildVoiceUpdateEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        get(GuildVoiceJoinEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        get(GuildVoiceMoveEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        get(GuildVoiceLeaveEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceMute(@NotNull GuildVoiceMuteEvent event) {
        get(GuildVoiceMuteEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceDeafen(@NotNull GuildVoiceDeafenEvent event) {
        get(GuildVoiceDeafenEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceGuildMute(@NotNull GuildVoiceGuildMuteEvent event) {
        get(GuildVoiceGuildMuteEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceGuildDeafen(@NotNull GuildVoiceGuildDeafenEvent event) {
        get(GuildVoiceGuildDeafenEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceSelfMute(@NotNull GuildVoiceSelfMuteEvent event) {
        get(GuildVoiceSelfMuteEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceSelfDeafen(@NotNull GuildVoiceSelfDeafenEvent event) {
        get(GuildVoiceSelfDeafenEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceSuppress(@NotNull GuildVoiceSuppressEvent event) {
        get(GuildVoiceSuppressEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceStream(@NotNull GuildVoiceStreamEvent event) {
        get(GuildVoiceStreamEvent.class).run(event);
    }

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        get(RoleCreateEvent.class).run(event);
    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        get(RoleDeleteEvent.class).run(event);
    }

    @Override
    public void onRoleUpdateColor(@NotNull RoleUpdateColorEvent event) {
        get(RoleUpdateColorEvent.class).run(event);
    }

    @Override
    public void onRoleUpdateHoisted(@NotNull RoleUpdateHoistedEvent event) {
        get(RoleUpdateHoistedEvent.class).run(event);
    }

    @Override
    public void onRoleUpdateMentionable(@NotNull RoleUpdateMentionableEvent event) {
        get(RoleUpdateMentionableEvent.class).run(event);
    }

    @Override
    public void onRoleUpdateName(@NotNull RoleUpdateNameEvent event) {
        get(RoleUpdateNameEvent.class).run(event);
    }

    @Override
    public void onRoleUpdatePermissions(@NotNull RoleUpdatePermissionsEvent event) {
        get(RoleUpdatePermissionsEvent.class).run(event);
    }

    @Override
    public void onRoleUpdatePosition(@NotNull RoleUpdatePositionEvent event) {
        get(RoleUpdatePositionEvent.class).run(event);
    }

    @Override
    public void onEmoteAdded(@NotNull EmoteAddedEvent event) {
        get(EmoteAddedEvent.class).run(event);
    }

    @Override
    public void onEmoteRemoved(@NotNull EmoteRemovedEvent event) {
        get(EmoteRemovedEvent.class).run(event);
    }

    @Override
    public void onEmoteUpdateName(@NotNull EmoteUpdateNameEvent event) {
        get(EmoteUpdateNameEvent.class).run(event);
    }

    @Override
    public void onEmoteUpdateRoles(@NotNull EmoteUpdateRolesEvent event) {
        get(EmoteUpdateRolesEvent.class).run(event);
    }

    @Override
    public void onHttpRequest(@NotNull HttpRequestEvent event) {
        get(HttpRequestEvent.class).run(event);
    }

    @Override
    public void onGenericMessage(@NotNull GenericMessageEvent event) {
        get(GenericMessageEvent.class).run(event);
    }

    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        get(GenericMessageReactionEvent.class).run(event);
    }

    @Override
    public void onGenericGuildMessage(@NotNull GenericGuildMessageEvent event) {
        get(GenericGuildMessageEvent.class).run(event);
    }

    @Override
    public void onGenericGuildMessageReaction(@NotNull GenericGuildMessageReactionEvent event) {
        get(GenericGuildMessageReactionEvent.class).run(event);
    }

    @Override
    public void onGenericPrivateMessage(@NotNull GenericPrivateMessageEvent event) {
        get(GenericPrivateMessageEvent.class).run(event);
    }

    @Override
    public void onGenericPrivateMessageReaction(@NotNull GenericPrivateMessageReactionEvent event) {
        get(GenericPrivateMessageReactionEvent.class).run(event);
    }

    @Override
    public void onGenericUser(@NotNull GenericUserEvent event) {
        get(GenericUserEvent.class).run(event);
    }

    @Override
    public void onGenericUserPresence(@NotNull GenericUserPresenceEvent event) {
        get(GenericUserPresenceEvent.class).run(event);
    }

    @Override
    public void onGenericSelfUpdate(@NotNull GenericSelfUpdateEvent event) {
        get(GenericSelfUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericStoreChannel(@NotNull GenericStoreChannelEvent event) {
        get(GenericStoreChannelEvent.class).run(event);
    }

    @Override
    public void onGenericStoreChannelUpdate(@NotNull GenericStoreChannelUpdateEvent event) {
        get(GenericStoreChannelUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericTextChannel(@NotNull GenericTextChannelEvent event) {
        get(GenericTextChannelEvent.class).run(event);
    }

    @Override
    public void onGenericTextChannelUpdate(@NotNull GenericTextChannelUpdateEvent event) {
        get(GenericTextChannelUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericVoiceChannel(@NotNull GenericVoiceChannelEvent event) {
        get(GenericVoiceChannelEvent.class).run(event);
    }

    @Override
    public void onGenericVoiceChannelUpdate(@NotNull GenericVoiceChannelUpdateEvent event) {
        get(GenericVoiceChannelUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericCategory(@NotNull GenericCategoryEvent event) {
        get(GenericCategoryEvent.class).run(event);
    }

    @Override
    public void onGenericCategoryUpdate(@NotNull GenericCategoryUpdateEvent event) {
        get(GenericCategoryUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericGuildUpdate(@NotNull GenericGuildUpdateEvent event) {
        get(GenericGuildUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericGuildInvite(@NotNull GenericGuildInviteEvent event) {
        get(GenericGuildInviteEvent.class).run(event);
    }

    @Override
    public void onGenericGuildMember(@NotNull GenericGuildMemberEvent event) {
        get(GenericGuildMemberEvent.class).run(event);
    }

    @Override
    public void onGenericGuildMemberUpdate(@NotNull GenericGuildMemberUpdateEvent event) {
        get(GenericGuildMemberUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericGuildVoice(@NotNull GenericGuildVoiceEvent event) {
        get(GenericGuildVoiceEvent.class).run(event);
    }

    @Override
    public void onGenericRole(@NotNull GenericRoleEvent event) {
        get(GenericRoleEvent.class).run(event);
    }

    @Override
    public void onGenericRoleUpdate(@NotNull GenericRoleUpdateEvent event) {
        get(GenericRoleUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericEmote(@NotNull GenericEmoteEvent event) {
        get(GenericEmoteEvent.class).run(event);
    }

    @Override
    public void onGenericEmoteUpdate(@NotNull GenericEmoteUpdateEvent event) {
        get(GenericEmoteUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericPermissionOverride(@NotNull GenericPermissionOverrideEvent event) {
        get(GenericPermissionOverrideEvent.class).run(event);
    }

    @Override
    public void onResumed(@NotNull ResumedEvent event) {
        get(ResumedEvent.class).run(event);
    }

    @Override
    public void onReconnected(@NotNull ReconnectedEvent event) {
        get(ReconnectedEvent.class).run(event);
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        get(SlashCommandEvent.class).run(event);
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        get(ButtonClickEvent.class).run(event);
    }

    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        get(SelectionMenuEvent.class).run(event);
    }

    @Override
    public void onApplicationCommandUpdate(@NotNull ApplicationCommandUpdateEvent event) {
        get(ApplicationCommandUpdateEvent.class).run(event);
    }

    @Override
    public void onApplicationCommandDelete(@NotNull ApplicationCommandDeleteEvent event) {
        get(ApplicationCommandDeleteEvent.class).run(event);
    }

    @Override
    public void onApplicationCommandCreate(@NotNull ApplicationCommandCreateEvent event) {
        get(ApplicationCommandCreateEvent.class).run(event);
    }

    @Override
    public void onUserUpdateActivities(@NotNull UserUpdateActivitiesEvent event) {
        get(UserUpdateActivitiesEvent.class).run(event);
    }

    @Override
    public void onVoiceChannelUpdateRegion(@NotNull VoiceChannelUpdateRegionEvent event) {
        get(VoiceChannelUpdateRegionEvent.class).run(event);
    }

    @Override
    public void onStageInstanceDelete(@NotNull StageInstanceDeleteEvent event) {
        get(StageInstanceDeleteEvent.class).run(event);
    }

    @Override
    public void onStageInstanceUpdateTopic(@NotNull StageInstanceUpdateTopicEvent event) {
        get(StageInstanceUpdateTopicEvent.class).run(event);
    }

    @Override
    public void onStageInstanceUpdatePrivacyLevel(@NotNull StageInstanceUpdatePrivacyLevelEvent event) {
        get(StageInstanceUpdatePrivacyLevelEvent.class).run(event);
    }

    @Override
    public void onStageInstanceCreate(@NotNull StageInstanceCreateEvent event) {
        get(StageInstanceCreateEvent.class).run(event);
    }

    @Override
    public void onGuildTimeout(@NotNull GuildTimeoutEvent event) {
        get(GuildTimeoutEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateRulesChannel(@NotNull GuildUpdateRulesChannelEvent event) {
        get(GuildUpdateRulesChannelEvent.class).run(event);
    }

    @Override
    public void onGuildUpdateCommunityUpdatesChannel(@NotNull GuildUpdateCommunityUpdatesChannelEvent event) {
        get(GuildUpdateCommunityUpdatesChannelEvent.class).run(event);
    }

    @Override
    public void onGuildMemberUpdatePending(@NotNull GuildMemberUpdatePendingEvent event) {
        get(GuildMemberUpdatePendingEvent.class).run(event);
    }

    @Override
    public void onGuildVoiceRequestToSpeak(@NotNull GuildVoiceRequestToSpeakEvent event) {
        get(GuildVoiceRequestToSpeakEvent.class).run(event);
    }

    @Override
    public void onGenericApplicationCommand(@NotNull GenericApplicationCommandEvent event) {
        get(GenericApplicationCommandEvent.class).run(event);
    }

    @Override
    public void onGenericInteractionCreate(@NotNull GenericInteractionCreateEvent event) {
        get(GenericInteractionCreateEvent.class).run(event);
    }

    @Override
    public void onGenericComponentInteractionCreate(@NotNull GenericComponentInteractionCreateEvent event) {
        get(GenericComponentInteractionCreateEvent.class).run(event);
    }

    @Override
    public void onGenericStageInstance(@NotNull GenericStageInstanceEvent event) {
        get(GenericStageInstanceEvent.class).run(event);
    }

    @Override
    public void onGenericStageInstanceUpdate(@NotNull GenericStageInstanceUpdateEvent event) {
        get(GenericStageInstanceUpdateEvent.class).run(event);
    }

    @Override
    public void onGenericGuild(@NotNull GenericGuildEvent event) {
        get(GenericGuildEvent.class).run(event);
    }

}
