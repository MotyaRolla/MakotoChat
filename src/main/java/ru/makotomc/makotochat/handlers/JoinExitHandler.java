package ru.makotomc.makotochat.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.makotomc.makotochat.Config.Option;
import ru.makotomc.makotochat.Utils;

import java.util.List;

import static ru.makotomc.makotochat.Config.Config.config;

public class JoinExitHandler implements Listener {
    private String getMessage(Option opt, Player p){
        List<String> joinMsgs = config.getList(opt);

        int rand = Utils.random(joinMsgs.size());
        String nickname = Utils.getNickname(p);
        return Utils.formatMessage(joinMsgs.get(rand).replace("<0>",nickname));
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        if(!config.getBool(Option.player_join))
            return;
        e.setJoinMessage(getMessage(Option.join_messages,e.getPlayer()));
    }

    @EventHandler
    public void exit(PlayerQuitEvent e){
        if(!config.getBool(Option.player_exit))
            return;
        e.setQuitMessage(getMessage(Option.exit_messages,e.getPlayer()));
    }
}
