package me.shakeforprotein.treebotweakssp.Tweaks.BlockWithersNearClaims;

import me.shakeforprotein.treebotweakssp.Integrations.IntegrationGriefPrevention;
import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class BlockWither implements Listener {

    private TreeboTweaksSP pl;
    private IntegrationGriefPrevention integrationGriefPrevention;

    public BlockWither(TreeboTweaksSP main){
        this.pl = main;
        this.integrationGriefPrevention = new IntegrationGriefPrevention(pl);
    }

    @EventHandler
    public void onWitherSpawn(EntitySpawnEvent e){
        if (e.getEntity().getType() == EntityType.WITHER){
            Location loc = e.getLocation();
            for (Player p : Bukkit.getOnlinePlayers()){
                if(p.getLocation().distance(loc) < 20){
                    if(!integrationGriefPrevention.canSpawnWitherAtCoordinate(p, loc)){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
