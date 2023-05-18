package ru.makotomc.makotochat.handlers;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.makotomc.makotochat.Config.Option;
import ru.makotomc.makotochat.Utils;
import ru.makotomc.makotochat.WebHook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import static ru.makotomc.makotochat.Config.Config.config;

class MessageContainer{
    String nickname;
    String msg;
    public MessageContainer(String nickname, String msg){
        this.nickname = nickname;
        this.msg = msg;
    }
}
public class ChatHandler implements Listener {
    public static final List<MessageContainer> playerMessages = new ArrayList<>();

    public static void checkMsgs(){
        try {
            if (playerMessages.isEmpty())
                return;
            for (MessageContainer set : playerMessages) {
                String player = set.nickname;
                String msg = set.msg;
                String notFormatted = msg;

                msg = msg.replaceAll("(.)\\1+", "$1");
                msg = msg.replaceAll("[\\[\\]!@`#&%^*()_+=.>,</0-9 ]", "").toLowerCase();

                boolean edited = false;
                List<String> founded = new ArrayList<>();
                for (String badword : config.getList(Option.badwords)) {
                    badword = badword.replaceAll("(.)\\1+", "$1").toLowerCase();
                    if (Pattern.compile(badword).matcher(msg).find()) {
                        notFormatted = notFormatted.replace(badword, "`" + badword + "`");
                        founded.add(badword);
                        edited = true;
                    }
                }
                if (edited)
                    WebHook.sendAlarm(
                            player,
                            notFormatted.replace("``", ""),
                            Joiner.on(' ').join(founded)
                    );
                playerMessages.remove(set);
            }
        }catch (Exception ignored){}
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
                            p.playSound(p.getLocation(), Sound.valueOf(config.getString(Option.mention_alarm_sound)),1,1);
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
                            author.playSound(author.getLocation(), Sound.valueOf(config.getString(Option.blur_alarm_sound)),1,1);
                            author.sendMessage(
                                    Utils.formatMessage(alarmMsg.replace("<0>",word).replace("<1>",randomWord))
                            );
                        }
                    }
                }
            }
            if(config.getBool(Option.discord_alarm))
                playerMessages.add(new MessageContainer(author.getName(),message));
        }

        if(isGlobal)
            message = "!"+message;
        e.setMessage(message);
    }
}
