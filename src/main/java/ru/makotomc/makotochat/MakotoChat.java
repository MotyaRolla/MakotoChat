package ru.makotomc.makotochat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class MakotoChat extends JavaPlugin {
//    public static Map<String, Boolean> config = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.init();

        Bukkit.getPluginManager().registerEvents(new ChatHandler(),this);

    }

    @Override
    public void onDisable() {

    }


    public static MakotoChat getInstance(){
        return getPlugin(MakotoChat.class);
    }
}
