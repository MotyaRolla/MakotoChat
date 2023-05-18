package ru.makotomc.makotochat.handlers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.makotomc.makotochat.Config.Config;
import ru.makotomc.makotochat.Config.Option;
import ru.makotomc.makotochat.Utils;

import java.util.HashMap;

public class PMessageHandler implements Listener, CommandExecutor {
    static HashMap<String, String> nickToLastSenderNick = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        if(strings.length<2){
            p.sendMessage(Utils.formatMessage(Config.config.getString(Option.locale_wrongusage_pm)).replace("<0>",command.getName()));
            return true;
        }
        // {0} - sender; {1} - Getter; {2} - message;
        String format = "{0} "+ChatColor.WHITE+ChatColor.BOLD+"-> "+ChatColor.RESET+"{1} "+ChatColor.GRAY+ChatColor.BOLD+"> "+ChatColor.RESET+ChatColor.WHITE+"{2}";
        String message = "";

        for(String st : strings)
            if(!st.equals(strings[0]))
                message = message+" "+st;

        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.getName().equals(strings[0])){

                String formatted = format
                        .replace("{0}", Utils.getNickname(p))
                        .replace("{1}", Utils.getNickname(players))
                        .replace("{2}",message);

                players.sendMessage(formatted);
                p.sendMessage(formatted);

                players.playSound(players.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_STEP, 1, 1);
                players.playNote(p.getLocation(), Instrument.PIANO, new Note(4));

                players.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Utils.formatMessage(Config.config.getString(Option.locale_newmessage)).replace("<0>",Utils.getNickname(p))
                ));
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Utils.formatMessage(Config.config.getString(Option.locale_newmessage)).replace("<0>",Utils.getNickname(players))
                ));
                players.sendTitle("",Utils.formatMessage(Config.config.getString(Option.locale_newmessage_title)),0,10,20);

                nickToLastSenderNick.put(players.getName(), p.getName());
                return true;
            }
        }
        //no player found
        p.sendMessage(Config.config.getString(Option.locale_player_not_found));
        return true;
    }
    @EventHandler
    public void onPm(PlayerCommandPreprocessEvent e){
        String[] msg = e.getMessage().split(" ");
        String[] pm = "w,m,t,pm,emsg,msg,epm,tell,etell,whisper,ewhisper,m,msg,ms,w".split(",");
        String[] rep = "er,reply,ereply,r,к,re".split(",");
        for(String a : pm){
            if(msg[0].equals("/"+a)) {
                e.setMessage(e.getMessage().replace(msg[0], "/makotomsg"));
                return;
            }

        }
        for(String a : rep)
            if(msg[0].equals("/"+a)) {
                if (nickToLastSenderNick.containsKey(e.getPlayer().getName()))
                    e.setMessage(e.getMessage().replace(msg[0], "/makotomsg " + nickToLastSenderNick.get(e.getPlayer().getName())));
                else
                    e.getPlayer().sendMessage(Utils.formatMessage(Config.config.getString(Option.locale_noreply)));
                return;
            }
    }
}
