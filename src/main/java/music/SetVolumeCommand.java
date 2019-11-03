package music;

import main.Info;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetVolumeCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "setvolume") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "громоксть")) {

            TextChannel channel = event.getChannel();

            if (args.length < 2) {
                channel.sendMessage("Пожалуйста, укажи жлаемую громкость!").queue();
                return;
            }
            if (Integer.parseInt(args[1]) >=0 && Integer.parseInt(args[1]) <=100) {
                PlayerManager manager = PlayerManager.getInstance();
                manager.getGuildMusicManager(event.getGuild()).player.setVolume(Integer.parseInt(args[1]));
                channel.sendMessage("Громкость установлена на " + args[1] + ", наслаждайтесь)").queue();
            }else{
                channel.sendMessage("Укажите правильный диапазон громкости! (0-100)").queue();
            }


        }

    }
}
