package events;

import main.Info;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;
import java.awt.*;

import java.util.concurrent.TimeUnit;

public class OnlineCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "online") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "онлайн")){

            int online = 0;
            for(int i =0; i<event.getGuild().getMembers().size(); i++){
                if(event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.ONLINE ||
                        event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB){
                    online++;
                }
            }
            online--;
            event.getChannel().sendMessage("Текущий онлайн: " +online+ " человек! Всего на сервере: "
                    +event.getGuild().getMembers().size()+" человек!").queue();

        }else if (args[0].equalsIgnoreCase(Info.PREFIX + "tournaments") ||
                args[0].equalsIgnoreCase(Info.PREFIX + "турниры")){
            event.getChannel().sendMessage("**Ближайших турниров пока что нет***").queue();

        }else if (args[0].equalsIgnoreCase(Info.PREFIX + "help")){
            sendHelpMessage(event.getMember(), event.getChannel());

        }else if(args[0].equalsIgnoreCase(Info.PREFIX + "clear") ||
                (args[0].equalsIgnoreCase(Info.PREFIX + "очистить"))){

            if (args.length<2) {
                clearError(event.getMember(), event.getChannel());

            }else {
                try {
                    event.getMessage().delete().queue();
                    TextChannel target = event.getMessage().getMentionedChannels().get(0);
                    clearMessages(target, Integer.parseInt(args[2]));
                }catch (Exception ex){
                    event.getChannel().sendMessage("Я не могу удалять сообщения, которые старше 2х недель :(");
                }

            }
        }


        }

    public void clearError(Member member, TextChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Правильный вызов команды !clear/!очистить");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.RED);
        builder.addField("Вызывается: !clear/!очистить {#канал} {кол-во сообщений}", "",false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
    }

    public void sendHelpMessage(Member member, TextChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Список всех команд:");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#00FF00"));
        builder.addField("**!турниры или !tournaments** - список ближ. турниров", "",false);
        builder.addField("**!онлайн или !online** - покажет онлайн сервера", "", false);
        builder.addField("**!clear или !очистить {#канал} {кол-во сообщений}** - Удаляет сообщения.", "", false);
        builder.addField("**!join или !зайди** - Подключится к вашему каналу.", "", false);
        builder.addField("**!leave или !выйди** - Отключиться от вашего канала.", "", false);
        builder.addField("**!play или !сыграть {ссылка на песню}** - сыграть вашу песню", "", false);
        builder.addField("**!stop или !стоп** - остановить проигрывать песню", "", false);
        builder.addField("**!playJOJO или !сыгратьЖОЖО** - проигрывать рандомную JOJO-музыку 0.0", "", false);
        builder.addField("**!setvolume или !громкость {громкость}** -выставить громкость: 0-100", "", false);
        builder.addField("**!bye или !прощай** - бот попрощается с вами в голосовом канале...", "", false);
        builder.addField("**!queue или !очередь** - Покажет очередь песен", "", false);
        builder.addField("**!skip или !пропустить** - Пропустить текущую песню", "", false);
        builder.addField("**!np или !си** - Показывает песню, которая играет сейчас", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
    }

    private void clearMessages(TextChannel channel, int num){
        MessageHistory history = new MessageHistory(channel);
        List<Message> msgs;
        msgs = history.retrievePast(num).complete();
        channel.deleteMessages(msgs).queue();
    }
}