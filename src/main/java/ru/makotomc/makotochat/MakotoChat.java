package ru.makotomc.makotochat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.makotomc.makotochat.Config.Config;
import ru.makotomc.makotochat.commands.PluginCommand;
import ru.makotomc.makotochat.handlers.ChatHandler;

import java.util.Objects;

public final class MakotoChat extends JavaPlugin {
    @Override
    public void onEnable() {
        //config
        saveDefaultConfig();
        Config.init();
        WebHook.init();

        //events
        Bukkit.getPluginManager().registerEvents(new ChatHandler(),this);

        //commands
        Objects.requireNonNull(Bukkit.getPluginCommand("makotochat")).setExecutor(new PluginCommand());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,ChatHandler::checkMsgs,100,100);

    }

    @Override
    public void onDisable() {

    }


    public static MakotoChat getInstance(){
        return getPlugin(MakotoChat.class);
    }
}
