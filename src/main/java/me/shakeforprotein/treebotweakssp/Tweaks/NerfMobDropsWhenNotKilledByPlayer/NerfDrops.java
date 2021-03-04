package me.shakeforprotein.treebotweakssp.Tweaks.NerfMobDropsWhenNotKilledByPlayer;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NerfDrops implements Listener {

    private TreeboTweaksSP pl;

    public NerfDrops(TreeboTweaksSP main){
        this.pl = main;
    }

    @EventHandler
    public void mobDies(EntityDeathEvent e){
        if(e.getEntity() instanceof Monster && e.getEntity().getKiller() == null && !e.getDrops().isEmpty()){
            if(ThreadLocalRandom.current().nextInt(0,9) > 3){
                List<ItemStack> newDrops = new ArrayList<>();
                for(ItemStack item : e.getDrops()){
                    for(EquipmentSlot slot : EquipmentSlot.values()){
                        if(e.getEntity().getEquipment().getItem(slot) != null && e.getEntity().getEquipment().getItem(slot) == item){
                            newDrops.add(item);
                        }
                    }
                }
                e.getDrops().clear();
                e.getDrops().addAll(newDrops);
            }
        }
    }
}
