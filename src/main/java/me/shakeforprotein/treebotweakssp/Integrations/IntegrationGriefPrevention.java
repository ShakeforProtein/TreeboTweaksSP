package me.shakeforprotein.treebotweakssp.Integrations;

import me.ryanhamshire.GriefPrevention.Claim;
import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class IntegrationGriefPrevention {

    private TreeboTweaksSP pl;

    public IntegrationGriefPrevention(TreeboTweaksSP main) {
        this.pl = main;
    }

    public boolean canSpawnWitherAtCoordinate(Player p, Location loc) {
        boolean foundClaim = false;
        for (int i = -9; i < 9; i++) {
            for (int j = -9; j < 9; j++) {
                if (!pl.griefPrevention.dataStore.getClaims(loc.getChunk().getX() + i, loc.getChunk().getZ() + j).isEmpty()) {
                    foundClaim = true;
                }
            }
        }
        if(foundClaim){p.sendMessage(pl.badge + "You may not summon a wither this close to player claims");}
        return !foundClaim;
    }

    public boolean canPvPAtLocation(Player defender, Player attacker) {
        boolean canFight = true;
        Location loc1 = defender.getLocation();
        Location loc2 = attacker.getLocation();
        Vector<Claim> claims = new Vector<>();
        claims.addAll(pl.griefPrevention.dataStore.getPlayerData(defender.getUniqueId()).getClaims());
        claims.addAll(pl.griefPrevention.dataStore.getPlayerData(attacker.getUniqueId()).getClaims());
        for (Claim claim : claims) {
            List<Chunk> chunks = claim.getChunks();
            for(Chunk chunk : chunks){
                for (int i = -5; i < 5; i++) {
                    for (int j = -5; j < 5; j++) {
                        Chunk comparrisonChunk1 = loc1.getWorld().getChunkAt(loc1.getChunk().getX() + i, loc1.getChunk().getZ() + j);
                        Chunk comparrisonChunk2 = loc2.getWorld().getChunkAt(loc2.getChunk().getX() + i, loc2.getChunk().getZ() + j);
                        if(comparrisonChunk1 == chunk || comparrisonChunk2 == chunk){
                            canFight = false;
                        }
                    }
                }
            }
        }
        if(!canFight){
            defender.sendMessage(pl.badge + "You may not engage in PvP with " + attacker.getDisplayName() + " at this location");
            attacker.sendMessage(pl.badge + "You may not engage in PvP with " + defender.getDisplayName() + " at this location");
            defender.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, defender.getLocation().add(0,1.5,0), 5, 0.2, 0.1, 0.2);
            attacker.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, attacker.getLocation().add(0,1.5,0), 5, 0.2, 0.1,0.2);
        }
        return canFight;
    }


}
