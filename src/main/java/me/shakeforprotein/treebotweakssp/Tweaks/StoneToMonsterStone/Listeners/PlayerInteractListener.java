package me.shakeforprotein.treebotweakssp.Tweaks.StoneToMonsterStone.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getAction()== Action.LEFT_CLICK_BLOCK && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.STONE && e.getPlayer() != null && e.getPlayer().getWorld().getName().toLowerCase().contains("resource_world")){
            e.getClickedBlock().setType(Material.INFESTED_STONE);
        }
    }
}
