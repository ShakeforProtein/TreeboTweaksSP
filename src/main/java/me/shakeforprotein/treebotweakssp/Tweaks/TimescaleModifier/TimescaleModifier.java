package me.shakeforprotein.treebotweakssp.Tweaks.TimescaleModifier;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class TimescaleModifier implements Listener {

    private TreeboTweaksSP pl;

    private List<World> worlds;
    private String timeMultiplier = "NORMAL";
    private int cycles = 0;
    private BukkitTask timer;
    private Runnable normalRunnable = () -> {
    };
    private Runnable doubleRunnable = () -> {
        for (World world : worlds) {
            world.setTime(world.getFullTime() + 1);
        }
    };
    private Runnable quadRunnable = () -> {
        for (World world : worlds) {
            world.setTime(world.getFullTime() + 3);
        }
    };
    private Runnable stupidRunnable = () -> {
        for (World world : worlds) {
            world.setTime(world.getFullTime() + 127);
        }
    };
    private Runnable halfRunnable = () -> {
        if (cycles == 1) {
            for (World world : worlds) {
                world.setTime(world.getFullTime() - 1);
            }
            cycles = 0;
        } else {
            cycles = 1;
        }
    };
    private Runnable quarterRunnable = () -> {
        if (cycles >= 3) {
            for (World world : worlds) {
                world.setTime(world.getFullTime() - 1);
            }
            cycles = 0;
        } else {
            cycles++;
        }
    };

    private Runnable shortNightRunnable = () -> {
        for (World world : worlds) {
            Long time = world.getTime();
            if (time > 18000 || time < 6000) {
                world.setFullTime(world.getFullTime() + 3);
            }
        }
    };

        private HashMap<String, Runnable> speedHash = new HashMap<>();

    public TimescaleModifier(TreeboTweaksSP main) {
            this.pl = main;
            speedHash.put("NORMAL", normalRunnable);
            speedHash.put("DOUBLE", doubleRunnable);
            speedHash.put("QUADRUPLE", quadRunnable);
            speedHash.put("STUPID", stupidRunnable);
            speedHash.put("HALF", halfRunnable);
            speedHash.put("QUARTER", quarterRunnable);
            speedHash.put("SHORTNIGHTS", shortNightRunnable);


            Bukkit.getScheduler().runTaskLater(pl, () -> {
                worlds = Bukkit.getWorlds();
                timer = Bukkit.getScheduler().runTaskTimer(pl, normalRunnable, 1L, 1L);
            }, 1L);
        }

        @EventHandler
        public void multiplyTimeScale (PlayerInteractEvent event){
            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (event.getPlayer().hasPermission("treebo.staff") && event.getItem().getType() == Material.CLOCK && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasEnchants() && event.getItem().getItemMeta().hasEnchant(Enchantment.DIG_SPEED)) {
                    String newValue = "NORMAL";

                    if (timeMultiplier.equalsIgnoreCase("NORMAL")) {
                        newValue = "DOUBLE";
                    } else if (timeMultiplier.equalsIgnoreCase("DOUBLE")) {
                        newValue = "QUADRUPLE";
                    } else if (timeMultiplier.equalsIgnoreCase("QUADRUPLE")) {
                        newValue = "STUPID";
                    } else if (timeMultiplier.equalsIgnoreCase("STUPID")) {
                        newValue = "HALF";
                    } else if (timeMultiplier.equalsIgnoreCase("HALF")) {
                        newValue = "QUARTER";
                    } else if (timeMultiplier.equalsIgnoreCase("QUARTER")) {
                        newValue = "SHORTNIGHTS";
                    } else if (timeMultiplier.equalsIgnoreCase("SHORTNIGHTS")) {
                        newValue = "NORMAL";
                    }

                    timer.cancel();
                    timeMultiplier = newValue;
                    timer = Bukkit.getScheduler().runTaskTimer(pl, speedHash.get(newValue), 1L, 1L);

                    event.getPlayer().sendMessage(pl.badge + "Set Timescale to " + newValue);

                }
            }
        }
    }
