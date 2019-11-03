package music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildID = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildID);

        if (musicManager == null){
            musicManager = new GuildMusicManager (playerManager);
            musicManagers.put(guildID, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackURL){
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Добавляю в очередь " + track.getInfo().title).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null){
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Добавляю в очередь "+ firstTrack.getInfo().title
                        + " (Первый трек в очереди: "+ playlist.getName() + ")").queue();

                play(musicManager, firstTrack);

            }

            @Override
            public void noMatches() {
                channel.sendMessage("Я ничего не нашёл... " + trackURL).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Не могу спеть для вас это: " + e.getMessage()).queue();
            }
        });

    }

    public void loadAndPlayWOMessage(TextChannel channel, String trackURL){
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();


                if (firstTrack == null){
                    firstTrack = playlist.getTracks().remove(0);
                }

                channel.sendMessage("Добавляю в очередь "+ firstTrack.getInfo().title
                        + " (Первый трек в очереди: "+ playlist.getName() + ")").queue();

                play(musicManager, firstTrack);
                playlist.getTracks().forEach(musicManager.scheduler::queue);

            }

            @Override
            public void noMatches() {
                channel.sendMessage("Я ничего не нашёл... " + trackURL).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Не могу спеть для вас это: " + e.getMessage()).queue();
            }
        });

    }

    private void play(GuildMusicManager musicManager, AudioTrack track){
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
