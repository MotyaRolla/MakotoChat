package ru.makotomc.makotochat.handlers;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.makotomc.makotochat.Config.Option;
import ru.makotomc.makotochat.Utils;
import ru.makotomc.makotochat.WebHook;

import java.util.*;
import java.util.regex.Pattern;

import static ru.makotomc.makotochat.Config.Config.config;

public class ChatHandler implements Listener {
    public static final Map<String,String> playerMessages = new HashMap<>();

    public static void checkMsgs(){
        for(Map.Entry<String, String> set : playerMessages.entrySet()){
            String player = set.getKey();
            String msg = set.getValue();
            String notFormatted = msg;

            msg = msg.replaceAll("(.)\\1+", "$1");
            msg = msg.replaceAll("[\\[\\]!@`#&%^*()_+=.>,</0-9 ]","").toLowerCase();

            boolean edited = false;
            List<String> founded = new ArrayList<>();
            for(String badword : config.getList(Option.badwords)){
                badword = badword.replaceAll("(.)\\1+", "$1").toLowerCase();
                if(Pattern.compile(badword).matcher(msg).find()){
                    notFormatted = notFormatted.replace(badword,"`"+badword+"`");
                    founded.add(badword);
                    edited = true;
                }
            }
            if(edited)
                WebHook.sendAlarm(
                        player,
                        notFormatted.replace("``",""),
                        Joiner.on(' ').join(founded)
                );
            playerMessages.remove(player);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e){
        Player author = e.getPlayer();
        String message = e.getMessage();

        boolean isGlobal = false;
        if(message.charAt(0)=='!') {
            isGlobal = true;
            message = message.substring(1);
        }

        if( config.getBool(Option.mentions)){
            String prefix = config.getString(Option.mentions_prefix);
            String chat_format = config.getString(Option.mention_format);

            boolean hasGlobal = config.getBool(Option.global_chat);

            boolean alarm = config.getBool(Option.alarm);
            String alarm_format = config.getString(Option.alarm_message);


            for(Player p : Bukkit.getOnlinePlayers()) {
                for(String word : message.split("\\s+")) {
                    if (word.equals(prefix+p.getName())) {
                        message = message.replace(word, Utils.formatMessage(chat_format.replace("<0>",Utils.getNickname(p))));
                        if (alarm&&(hasGlobal==isGlobal)) {
                            p.sendMessage(
                                    Utils.formatMessage(
                                            alarm_format
                                                    .replace("<0>",Utils.getNickname(author))
                                                    .replace("<1>", Utils.getNickname(p))
                                    )
                            );
                        }
                    }
                }
            }
        }

        if(config.getBool(Option.automod)){
            if(config.getBool(Option.blur)){

                List<String> badwords = config.getList(Option.badwords);
                List<String> goodwords = config.getList(Option.good_words);
                boolean alarm = config.getBool(Option.automod_alarm);

                String alarmMsg = config.getString(Option.automod_alarm_message);
                String format = config.getString(Option.blur_format);
                for (String word : message.split("\\s+")) {
                    if (badwords.contains(word) && !word.equals("")) {
                        Random r = new Random(UUID.randomUUID().toString().hashCode());
                        String randomWord = goodwords.get(r.nextInt(goodwords.size() - 1));

                        message = message.replace(word, Utils.formatMessage(format.replace("<0>",randomWord)));
                        if(alarm){
                            author.sendMessage(
                                    Utils.formatMessage(alarmMsg.replace("<0>",word).replace("<1>",randomWord))
                            );
                        }
                    }
                }
            }
            if(config.getBool(Option.discord_alarm)){
                playerMessages.put(Utils.getNickname(author),message);
            }
        }


        if(isGlobal)
            message = "!"+message;
        e.setMessage(message);
    }
}
