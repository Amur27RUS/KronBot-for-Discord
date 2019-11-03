package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;

public class Bools {
    public static boolean isAdmin(GuildMessageReceivedEvent event) {
        if (event.getMember().getRoles().contains(event.getGuild().getRoleById("501815987499433985"))){
            return true;

        }else if(event.getMember().getRoles().contains(event.getGuild().getRoleById("501815987499433985"))){
            return true;
        }else {
            return false;
        }
    }



}
