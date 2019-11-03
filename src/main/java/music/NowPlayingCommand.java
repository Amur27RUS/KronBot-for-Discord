package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class NowPlayingCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "np") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "си")) {

            TextChannel channel = event.getChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null) {
                channel.sendMessage("Хмммм, я же сейчас ничего не воспроизвожу...").queue();

                return;
            }

            AudioTrackInfo info = player.getPlayingTrack().getInfo();

            channel.sendMessage(String.format("**Playing** [%s]\n%s %s - %s", info.title, info.uri, player.isPaused() ? "\u23F8" : "▶",
                    formatTime(player.getPlayingTrack().getPosition()),
                    formatTime(player.getPlayingTrack().getDuration()))).queue();
        }
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

