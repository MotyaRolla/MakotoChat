package ru.makotomc.makotochat;

import org.bukkit.entity.Player;

import static ru.makotomc.makotochat.Config.config;

public class Utils {

    public static String formatMessage(String str){
        return str.replace("&","§");
    }
    public static String getNickname(Player p){
        if((!(boolean) config.getBool(Option.customNames))|| p.getDisplayName()==null)
            return p.getName();
        else {
            return p.getDisplayName();
        }
    }
}
