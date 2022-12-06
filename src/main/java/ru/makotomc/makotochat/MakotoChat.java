package ru.makotomc.makotochat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.makotomc.makotochat.Config.Config;
import ru.makotomc.makotochat.commands.PluginCommand;
import ru.makotomc.makotochat.handlers.ChatHandler;

import java.util.Objects;

public final class MakotoChat extends JavaPlugin {
//    public static Map<String, Boolean> config = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Config.init();

        Bukkit.getPluginManager().registerEvents(new ChatHandler(),this);
        Objects.requireNonNull(Bukkit.getPluginCommand("makotochat")).setExecutor(new PluginCommand());

    }

    @Override
    public void onDisable() {

    }


    public static MakotoChat getInstance(){
        return getPlugin(MakotoChat.class);
    }
}
