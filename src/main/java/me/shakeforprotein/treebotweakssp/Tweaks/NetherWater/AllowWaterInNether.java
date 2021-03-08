package me.shakeforprotein.treebotweakssp.Tweaks.NetherWater;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class AllowWaterInNether implements Listener {

    @EventHandler
    public void NetherSizzle(BlockFadeEvent e){
        if(e.getBlock().getType() == Material.ICE && e.getBlock().getWorld().getEnvironment() == World.Environment.NETHER){
            e.setCancelled(true);
            e.getBlock().setType(Material.WATER);
        }
    }
}
