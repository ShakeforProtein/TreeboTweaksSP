package me.shakeforprotein.treebotweakssp.Tweaks.AutoBalancePvP;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.io.File;
import java.io.IOException;


public class AutoBalancePvP implements Listener {

    private TreeboTweaksSP pl;
    File pvpFile;
    YamlConfiguration pvpYml;
    public AutoBalancePvP(TreeboTweaksSP main){
        this.pl = main;
        this.pvpFile = new File(pl.getDataFolder(), "pvpBalance.yml");
        if(pvpFile.exists()){
           pvpYml = YamlConfiguration.loadConfiguration(pvpFile);
        } else {
            try{pvpFile.createNewFile();}
            catch(IOException e){
                pl.getLogger().warning(pl.badge + "Failed to create PVP Balancing YML. Please try manually creating pvpBalance.yml in the plugin data directory.");
            }
        }
    }

    @EventHandler
    public void PlayerDamageByPlayer(EntityDamageByEntityEvent e){
        if(!e.isCancelled()) {
            if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
                double attackerWins = pvpYml.getDouble("Players." + e.getDamager().getUniqueId().toString() + ".Other." + e.getEntity().getUniqueId().toString() + ".Kills", 0);
                double defenderWins = pvpYml.getDouble("Players." + e.getEntity().getUniqueId().toString() + ".Other." + e.getDamager().getUniqueId().toString() + ".Kills", 0);


                if (defenderWins == 0) {
                    defenderWins = 1;
                }
                if (attackerWins == 0) {
                    attackerWins = 1;
                }
//            Bukkit.broadcastMessage(e.getFinalDamage() + "");

                e.setDamage(e.getFinalDamage() * ((defenderWins / attackerWins) / 2));
//            Bukkit.broadcastMessage(e.getFinalDamage() + "");
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        if(pvpYml.getString("Players." + e.getPlayer().getUniqueId() + ".Name") == null){
           pvpYml.set("Players." + e.getPlayer().getUniqueId() + ".Name", e.getPlayer().getDisplayName());
        }
    }

    @EventHandler
    public void PlayerKIllPlayer(PlayerDeathEvent e){
        if(e.getEntity().getKiller() instanceof Player){
            String winner = e.getEntity().getKiller().getUniqueId().toString();
            String loser = e.getEntity().getUniqueId().toString();
            pvpYml.set("Players." + winner + ".Other." + loser + ".Kills", pvpYml.getInt("Players." + winner + ".Other." + loser + ".Kills") + 1);
            savePvPFile();
        }
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e){
        savePvPFile();
    }

    @EventHandler
    public void PluginUnload(PluginDisableEvent e){
        savePvPFile();
    }

    public void savePvPFile(){
        try{pvpYml.save(pvpFile);}
        catch (IOException ex){
            pl.getLogger().warning("Unable to save pvpBalance.yml");
        }
    }
}
