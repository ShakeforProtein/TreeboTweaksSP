package me.shakeforprotein.treebotweakssp.Tweaks.BlockPortalsInWorldGuardRegions;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import jdk.nashorn.internal.codegen.ClassEmitter;
import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPortalsInWorldGuardRegions implements Listener {

    private TreeboTweaksSP pl;
    private WorldGuard worldGuard;
    private boolean wgEnabled = false;

    public BlockPortalsInWorldGuardRegions(TreeboTweaksSP main) {
        this.pl = main;
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            Bukkit.getScheduler().runTaskLater(pl, () -> {
                if (Bukkit.getPluginManager().getPlugin("WorldGuard").isEnabled()) {
                    wgEnabled = true;
                    worldGuard = WorldGuard.getInstance();
                }
            }, 5L);
        }
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (!event.isCancelled()) {
            for (BlockState blockState : event.getBlocks()) {
                if (isInWgRegion(blockState.getLocation())) {
                    if (event.getEntity() instanceof Player) {
                        event.getEntity().sendMessage(pl.badge + "This portal was blocked as it would breach a protected region.");
                    }
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerUsePortal(PlayerPortalEvent event) {
        if(!event.isCancelled()) {
            if (event.getTo() != null && isInWgRegion(event.getTo())) {
                event.getPlayer().sendMessage(pl.badge + "This portal has been blocked as the other end would form in a protected region.");
                event.setCanCreatePortal(false);
                event.setCancelled(true);
                for(BlockFace face : BlockFace.values()){
                    if(event.getFrom().getBlock().getRelative(face).getType() == Material.NETHER_PORTAL){
                        event.getFrom().getBlock().getRelative(face).breakNaturally();
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void entityUsePortal(EntityPortalEvent event){
        if(!event.isCancelled()){
            if (event.getTo() != null && isInWgRegion(event.getTo())) {
                event.setCancelled(true);
                for(BlockFace face : BlockFace.values()){
                    if(event.getFrom().getBlock().getRelative(face).getType() == Material.NETHER_PORTAL){
                        event.getFrom().getBlock().getRelative(face).breakNaturally();
                        return;
                    }
                }
            }
        }
    }

    private boolean isInWgRegion(Location loc) {
        RegionContainer container = worldGuard.getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(loc.getWorld()));


        for(String allowedRegion : pl.getConfig().getStringList("AllowedPortalRegions")) {
            if (regions != null && regions.size() > 0 && regions.hasRegion(allowedRegion) && allowedRegion.toLowerCase() != "__global__") {
                if (regions.getRegion(allowedRegion).contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                    return false;
                }
            }
        }

        for (String regionKey : regions.getRegions().keySet()) {
            if (regionKey.toLowerCase() != "__global__") {
                if (regions.getRegion(regionKey) != null && regions.getRegion(regionKey).contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                    return true;
                }
            }
        }

        return false;
    }
}
