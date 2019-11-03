package music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "queue") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "очередь")) {


            TextChannel channel = event.getChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            if (queue.isEmpty()){
                channel.sendMessage("Очередь пуста!").queue();
                return;
            }

            int trackcount = Math.min(queue.size(), 20);
            List<AudioTrack> tracks = new ArrayList<>(queue);
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Текущая очередь: (Всего: " + queue.size() + ")");

            for (int i =0; i<trackcount; i++){
                AudioTrack track = tracks.get(i);
                AudioTrackInfo info = track.getInfo();

                builder.appendDescription(String.format("%s - %s\n", info.title, info.author));

            }
            channel.sendMessage(builder.build()).queue();


        }
    }
}
