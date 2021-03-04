package me.shakeforprotein.treebotweakssp.Tweaks.BlockPvPNearOwnClaims;

import me.shakeforprotein.treebotweakssp.Integrations.IntegrationGriefPrevention;
import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BlockPvPNearClaims implements Listener {

    private TreeboTweaksSP pl;
    private IntegrationGriefPrevention integrationGriefPrevention;

    public BlockPvPNearClaims(TreeboTweaksSP main){
        this.pl = main;
        this.integrationGriefPrevention = new IntegrationGriefPrevention(pl);
    }

    @EventHandler
    public void PlayerDamagePlayer(EntityDamageByEntityEvent e){
        if (!e.isCancelled() && e.getEntity() instanceof Player && e.getDamager() instanceof Player && e.getEntity() != e.getDamager()) {
            e.setCancelled(!integrationGriefPrevention.canPvPAtLocation((Player) e.getEntity(), (Player) e.getDamager()));
        }

        else if (!e.isCancelled() && e.getDamager() instanceof Projectile && e.getEntity() instanceof Player){
            Projectile damager = (Projectile) e.getDamager();

            if(damager.getShooter() instanceof Player && e.getEntity() != damager.getShooter()) {
                Player damagingPlayer = (Player) damager.getShooter();
                e.setCancelled(!integrationGriefPrevention.canPvPAtLocation((Player) e.getEntity(), damagingPlayer));
            }
        }
    }
}
