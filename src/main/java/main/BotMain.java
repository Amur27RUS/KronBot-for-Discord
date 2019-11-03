package main;

import events.*;
import music.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class BotMain {

    private static JDA jda;

    public static void main(String[] args) throws Exception{
        jda = new JDABuilder(AccountType.BOT)
                .setToken("NjM3OTY1Njg3Njc5MDkwNzE4.XbWECQ.wBweW8ThmpO10UDucTYoPJAy4Os")
                .build();

        jda.addEventListener(new HelloThere());
        jda.addEventListener(new OnlineCommand());
        jda.addEventListener(new JoinCommand());
        jda.addEventListener(new LeaveCommand());
        jda.addEventListener(new PlayCommand());
        jda.addEventListener(new StopCommand());
        jda.addEventListener(new PlayJOJOCommand());
        jda.addEventListener(new SetVolumeCommand());
        jda.addEventListener(new ByeCommand());
        jda.addEventListener(new QueueCommand());
        jda.addEventListener(new SkipCommand());
        jda.addEventListener(new NowPlayingCommand());
    }
}
