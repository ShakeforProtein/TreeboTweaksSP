package me.shakeforprotein.treebotweakssp.Tweaks.PlayerKeepItemsIfKilledByPlayer;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PlayerKeepItems implements Listener {

    private TreeboTweaksSP pl;

    private HashMap<Player, List<ItemStack>> returnHash = new HashMap<Player, List<ItemStack>>();

    public PlayerKeepItems(TreeboTweaksSP main) {
        this.pl = main;
    }

    @EventHandler
    public void PlayerDiesEvent(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
            Iterator<ItemStack> dropsIterator = e.getDrops().iterator();
            List<ItemStack> newDrops = new ArrayList<>();
            List<ItemStack> returnItems = new ArrayList<>();

            while (dropsIterator.hasNext()) {
                ItemStack drop = dropsIterator.next();

                if (drop.getType() == Material.SUNFLOWER || drop.getType() == Material.COBBLESTONE || drop.getType() == Material.PLAYER_HEAD) {
                    newDrops.add(drop);
                } else {
                    returnItems.add(drop);
                }
            }
            e.getDrops().clear();

            if (newDrops.size() > 0) {
                e.getDrops().addAll(newDrops);
            }
            returnHash.put(e.getEntity(), returnItems);
        }
    }

    @EventHandler
    public void PlayerRespawn(PlayerRespawnEvent e) {
        if (returnHash.containsKey(e.getPlayer())) {
            List<ItemStack> items = returnHash.get(e.getPlayer());
            for (ItemStack item : items) {
                if (e.getPlayer().getInventory().firstEmpty() != -1) {
                    e.getPlayer().getInventory().addItem(item);
                } else {
                    e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), item);
                }
            }
            returnHash.remove(e.getPlayer());
        }
    }
}
