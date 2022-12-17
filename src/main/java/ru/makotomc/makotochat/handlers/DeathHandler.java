package ru.makotomc.makotochat.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.makotomc.makotochat.Config.Config;
import ru.makotomc.makotochat.Config.Option;
import ru.makotomc.makotochat.Utils;

import static ru.makotomc.makotochat.Config.Config.config;

public class DeathHandler implements Listener {
    private static final String path = "chat.system_msg.death.causes.";
    private static String getDeathMessage(String cause){
        return Config.configString(path+cause);
    }
    public static Entity getLastEntityDamager(Entity entity) {
        EntityDamageEvent event = entity.getLastDamageCause();
        if (event != null && !event.isCancelled() && (event instanceof EntityDamageByEntityEvent)) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            if (damager instanceof Projectile) {
                Object shooter = ((Projectile) damager).getShooter();
                if (shooter != null && (shooter instanceof Entity))
                    return (Entity) shooter;
            }
            return damager;
        }
        return null;
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(!config.getBool(Option.death_messages))
            return;
        String msg = null;
        try {
            EntityDamageEvent.DamageCause cause = e.getEntity().getLastDamageCause().getCause();
            msg = getDeathMessage(cause.name().toLowerCase());
        } catch (Exception ignored){
            msg = getDeathMessage("custom");
        }
        e.setDeathMessage(null);

        String deadNick = Utils.getNickname(e.getEntity());
        Entity lastDamager =  getLastEntityDamager(e.getEntity());

        if(msg.contains("<1>"))
            if(lastDamager!=null) {
                String damagerName = lastDamager.getName();
                if (lastDamager instanceof Player)
                    damagerName = Utils.getNickname(((Player) lastDamager));
                msg = msg.replace("<1>", damagerName);
            }

        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(msg.replace("<0>",deadNick));
        }
    }
}
