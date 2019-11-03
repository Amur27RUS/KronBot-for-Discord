package events;

import events.Bools;
import main.Info;
import music.PlayerManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "play") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "сыграть")) {

            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();


            if (args.length < 2){
                channel.sendMessage("Пожалуйста, укажи ссылку на песню!").queue();
                return;
            }

            if (!audioManager.isConnected()){
                channel.sendMessage("Для начала добавьте меня в ваш голосовой канал! (!join)").queue();
                return;
            }

            String input = args[1];

            if (!isUrl(input) && !input.startsWith("ytsearch:")){
                //Use the youtube api for search instead, I CAN GET BLOCKED IF I'LL DO A LOT OF REQUESTS
                channel.sendMessage("Пожалуйста, укажите нормальную ссылку на песню!").queue();
                return;
            }

            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(event.getChannel(), input);

        }
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
