package me.shakeforprotein.treebotweakssp.Tweaks.SleepAtCampfire;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SleepAtCampfire implements Listener {

    private TreeboTweaksSP pl;

    public SleepAtCampfire(TreeboTweaksSP main){
        this.pl = main;
    }

    @EventHandler
    private void playerInteractCampfire(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking() && event.getItem().getType() == Material.AIR && event.getPlayer().getWorld().getTime() > 12000){

            Location existingBedSpawnLocation = event.getPlayer().getBedSpawnLocation();

            Player player = event.getPlayer();

            player.sendBlockChange(event.getPlayer().getLocation().getBlock().getLocation(), Bukkit.createBlockData(Material.RED_BED));

            event.getPlayer().sleep(event.getPlayer().getLocation().getBlock().getLocation(), true);

            if(existingBedSpawnLocation != null) {
                if (event.getPlayer().getBedSpawnLocation() != existingBedSpawnLocation) {
                    event.getPlayer().setBedSpawnLocation(existingBedSpawnLocation, true);
                }
            }
        } else {
            event.getPlayer().sendMessage(event.getPlayer().getWorld().getTime() + "");
        }
    }
}
