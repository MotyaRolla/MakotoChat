package ru.makotomc.makotochat;

import org.bukkit.entity.Player;
import ru.makotomc.makotochat.Config.Option;

import java.util.Random;
import java.util.UUID;

import static ru.makotomc.makotochat.Config.Config.config;

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
    public static int random(int max){
        Random r = new Random(UUID.randomUUID().getLeastSignificantBits());
        return r.nextInt(max);
    }
}
