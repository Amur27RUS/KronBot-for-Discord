package music;

import main.Info;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "join") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "зайди")) {

            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();

            GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

            VoiceChannel voiceChannel = memberVoiceState.getChannel();
            Member selfmember = event.getGuild().getSelfMember();

            if (audioManager.isConnected()) {
                channel.sendMessage("Я уже подключён к каналу! OwO " +
                        "").queue();

            } else if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("Пожалуйста, зайдите в голосовой канал!" +
                        "").queue();

            } else if (!selfmember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                channel.sendMessage("У меня нет прав на подключение к каналу! 0.0 " +
                        "").queue();

            } else {
                audioManager.openAudioConnection(voiceChannel);
                channel.sendMessage("Подключился к вашему каналу! :3" +
                        "").queue();
            }

        }
    }
}
