package music;

import main.Info;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends ListenerAdapter {


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "leave") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "выйди")) {
            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();

            VoiceChannel voiceChannel = audioManager.getConnectedChannel();


            if (!audioManager.isConnected()) {
                channel.sendMessage("Но я же не подключён к каналу... :( " +
                        "").queue();

            }else if(!voiceChannel.getMembers().contains(event.getMember())){

                channel.sendMessage("Хах, ты не в голосом канале со мной! :)" +
                        " ").queue();
            }else {


                PlayerManager manager = PlayerManager.getInstance();
                channel.sendMessage("Отключаюсь...").queue();

                audioManager.closeAudioConnection();

            }
        }
    }
}

