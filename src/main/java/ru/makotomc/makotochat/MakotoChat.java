package ru.makotomc.makotochat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.makotomc.makotochat.Config.Config;
import ru.makotomc.makotochat.commands.PluginCommand;
import ru.makotomc.makotochat.handlers.ChatHandler;
import ru.makotomc.makotochat.handlers.DeathHandler;
import ru.makotomc.makotochat.handlers.JoinExitHandler;

import java.util.Objects;

public final class MakotoChat extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("MakotoChat is on!");
        //config
        saveDefaultConfig();
        Config.init();
        WebHook.init();

        //events
        Bukkit.getPluginManager().registerEvents(new ChatHandler(),this);
        Bukkit.getPluginManager().registerEvents(new JoinExitHandler(), this);
        Bukkit.getPluginManager().registerEvents(new DeathHandler(), this);

        //commands
        Objects.requireNonNull(Bukkit.getPluginCommand("makotochat")).setExecutor(new PluginCommand());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,ChatHandler::checkMsgs,1000,1000);
    }

    @Override
    public void onDisable() {
        getLogger().info("MakotoChat is disabled!");
    }


    public static MakotoChat getInstance(){
        return getPlugin(MakotoChat.class);
    }
}
