package ru.makotomc.makotochat.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.makotomc.makotochat.Config.Option;
import ru.makotomc.makotochat.Utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static ru.makotomc.makotochat.Config.Config.config;

public class ChatHandler implements Listener {
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
                                                    .replace("<0>",Utils.getNickname(e.getPlayer()))
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
                            e.getPlayer().sendMessage(
                                    Utils.formatMessage(alarmMsg.replace("<0>",word).replace("<1>",randomWord))
                            );
                        }
                    }
                }
            }
        }

        if(isGlobal)
            message = "!"+message;
        e.setMessage(message);
    }
}
