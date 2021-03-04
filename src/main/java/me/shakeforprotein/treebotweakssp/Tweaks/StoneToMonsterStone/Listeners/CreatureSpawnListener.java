package me.shakeforprotein.treebotweakssp.Tweaks.StoneToMonsterStone.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class CreatureSpawnListener implements Listener {


    @EventHandler
    public void onMobSpaw(EntitySpawnEvent e){
        if(e.getEntityType() == EntityType.SILVERFISH && e.getLocation().getWorld().getName().toLowerCase().contains("resource_world")){
            e.setCancelled(true);
        }
    }
}
