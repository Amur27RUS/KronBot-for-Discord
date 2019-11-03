package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SkipCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "skip") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "пропустить")) {

            TextChannel channel = event.getChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            TrackScheduler scheduler = musicManager.scheduler;
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null){
                channel.sendMessage("Хмммм... Но я же сейчас ничего не воспроизвожу :( ").queue();

                return;
            }

            scheduler.nextTrack();

            channel.sendMessage("Пропускаю текущую песню...").queue();

        }
    }
}
