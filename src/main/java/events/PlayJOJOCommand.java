package events;

import main.Info;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URL;

public class PlayJOJOCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "playJOJO") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "сыгратьЖОЖО")) {

            TextChannel channel = event.getChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();

            if (!audioManager.isConnected()){
                channel.sendMessage("Для начала добавьте меня в ваш голосовой канал! (!join)").queue();
                return;
            }

            channel.sendMessage("JOTARO...").queue();
            channel.sendMessage("DIO?!").queue();

            PlayerManager manager = PlayerManager.getInstance();

            double random = Math.random();

            if (random <= 0.1) {
                manager.loadAndPlayWOMessage(event.getChannel(), "https://www.youtube.com/watch?v=2MtOpB5LlUA");

            }else if (random > 0.1 && random <= 0.3){
                manager.loadAndPlayWOMessage(event.getChannel(), "https://youtu.be/gH_i1mqgEDU");

            }else if (random >0.3 && random <= 0.5){
                manager.loadAndPlayWOMessage(event.getChannel(), "https://youtu.be/gH_i1mqgEDU");

            }else if(random > 0.5 && random <=0.7){
                manager.loadAndPlayWOMessage(event.getChannel(), "https://youtu.be/MPWziwsKmkc");

            }else if(random > 0.7){
                manager.loadAndPlayWOMessage(event.getChannel(), "https://www.youtube.com/watch?v=0oIg2R_-gSI");

            }



        }


    }

}

