package events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloThere extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        String[] args = event.getMessage().getContentRaw().split(" ");

//Приветствие
        if (args[0].equalsIgnoreCase("Привет") && (args[1].equalsIgnoreCase("KronBot")
                || (args[1].equalsIgnoreCase("КронБот")))){
            User nickname = event.getAuthor();
            event.getChannel().sendMessage("Ну, здарова, "+nickname.getName()+")").queue();
        }

//Если послали
        if (args[0].equalsIgnoreCase("Иди") && (args[1].equalsIgnoreCase("нахуй") &&
                (args[2].equalsIgnoreCase("KronBot")
                || (args[2].equalsIgnoreCase("КронБот"))))){

            User nickname = event.getAuthor();
            event.getChannel().sendMessage("Иди нахуй "+nickname.getName()).queue();

        }

//Кто такой бот?
        if (args[0].equalsIgnoreCase("Кто") && (args[1].equalsIgnoreCase("такой")
                && (args[2].equalsIgnoreCase("KronBot?")
                || (args[2].equalsIgnoreCase("КронБот?"))))){

            User nickname = event.getAuthor();
            event.getChannel().sendMessage("Привет, "+nickname.getName()+"! Я ваш личный бот, который может вам помочь!" +
                    " Если есть идеи для новых функций, пиши создателю(Amur)!").queue();
        }

//        ШАБЛОН ДЛЯ СЛЕДУЮЩЕЙ ТЕКСТОВОЙ КОМАНДЫ:
//        if (args[0].equalsIgnoreCase("Привет") && (args[1].equalsIgnoreCase("KronBot")
//                || (args[1].equalsIgnoreCase("КронБот")))){
//
//            event.getChannel().sendMessage("Ну, здарова)").queue();
        }
    }

