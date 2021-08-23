package me.shakeforprotein.treebotweakssp.Tweaks.MaintainFlightBetweenWorlds;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.HashMap;

public class MaintainFlightBetweenWorlds implements Listener {


    private TreeboTweaksSP pl;
    private HashMap<Player, Boolean> flyingPlayers = new HashMap<>();

    public MaintainFlightBetweenWorlds(TreeboTweaksSP main){
        this.pl = main;

        Bukkit.getScheduler().runTaskTimer(pl, ()->{
            flyingPlayers.clear();
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.isFlying() && player.getAllowFlight()){
                    flyingPlayers.put(player, true);
                } else {
                    flyingPlayers.put(player, false);
                }
            }
        }, 60L, 5L);
    }
    
    @EventHandler
    public void maintainFlight(PlayerChangedWorldEvent e){
        if(flyingPlayers.containsKey(e.getPlayer()) && e.getPlayer().hasPermission("essentials.fly")){
            Bukkit.getScheduler().runTaskLater(pl, ()->{
                e.getPlayer().setAllowFlight(true);
                e.getPlayer().setFlying(true);
            }, 2L);
        }
    }
}
