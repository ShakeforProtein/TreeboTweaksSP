package me.shakeforprotein.treebotweakssp.Tweaks.TimedPvpBlockBreak.Listeners;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class LastPvpListener implements Listener {

    private TreeboTweaksSP pl;
    private static HashMap<Player, Long> pvpList = new HashMap<>();
    private static HashMap<Player, Long> warnedList = new HashMap<>();

    public LastPvpListener(TreeboTweaksSP main){
        this.pl = main;
    }

    @EventHandler
    private void OnPlayerHitPlayer(EntityDamageByEntityEvent e){
        if(!e.isCancelled()) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                pvpList.put((Player) e.getEntity(), System.currentTimeMillis());
                pvpList.put((Player) e.getDamager(), System.currentTimeMillis());
            }
        }
    }


    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {

        if (!e.isCancelled() && e.getPlayer() != null) {
            if (pvpList.containsKey(e.getPlayer()) && System.currentTimeMillis() - pvpList.get(e.getPlayer()) < 30000){


                if(warnedList.containsKey(e.getPlayer())){
                    if(System.currentTimeMillis() - warnedList.get(e.getPlayer()) > 10000){
                        warnedList.put(e.getPlayer(), System.currentTimeMillis());
                        e.getPlayer().sendMessage("You are currently in PvP combat, any placed block will break in 30 seconds");
                    }
                } else {
                    warnedList.put(e.getPlayer(), System.currentTimeMillis());
                    e.getPlayer().sendMessage("You are currently in PvP combat, any placed block will break in 30 seconds");
                }



                Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
                    @Override
                    public void run() {
                        e.getBlockPlaced().getLocation().getBlock().breakNaturally();
                    }
                }, 600);
            }
        }
    }

}
