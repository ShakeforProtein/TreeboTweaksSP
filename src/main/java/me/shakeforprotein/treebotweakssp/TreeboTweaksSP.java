package me.shakeforprotein.treebotweakssp;


import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.shakeforprotein.treebotweakssp.Tweaks.ASTHandler.Listeners.AstHandler;
import me.shakeforprotein.treebotweakssp.Tweaks.BlockPvPNearOwnClaims.BlockPvPNearClaims;
import me.shakeforprotein.treebotweakssp.Tweaks.BlockWithersNearClaims.BlockWither;
import me.shakeforprotein.treebotweakssp.Tweaks.DoubleDoors.DoubleDoors;
import me.shakeforprotein.treebotweakssp.Tweaks.DynMapToInGameMap.CommandMakeMap;
import me.shakeforprotein.treebotweakssp.Tweaks.DynMapToInGameMap.InternalMapRenderer;
import me.shakeforprotein.treebotweakssp.Tweaks.DynMapToInGameMap.MapRenderListener;
import me.shakeforprotein.treebotweakssp.Tweaks.NerfMobDropsWhenNotKilledByPlayer.NerfDrops;
import me.shakeforprotein.treebotweakssp.Tweaks.PlayerKeepItemsIfKilledByPlayer.PlayerKeepItems;
import me.shakeforprotein.treebotweakssp.Tweaks.StoneToMonsterStone.Listeners.CreatureSpawnListener;
import me.shakeforprotein.treebotweakssp.Tweaks.StoneToMonsterStone.Listeners.PlayerInteractListener;
import me.shakeforprotein.treebotweakssp.Tweaks.TimedPvpBlockBreak.Listeners.LastPvpListener;
import me.shakeforprotein.treebotweakssp.Tweaks.TreeTwerking.Listeners.TwerkingListener;
import me.shakeforprotein.treebotweakssp.Tweaks.WorldGuardFlags.WorldGuardFlagExtentions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class TreeboTweaksSP extends JavaPlugin implements Listener {

    public static List<Player> vehicleViewLockList = new ArrayList<>();
    public boolean isRenderingMap = false;
    public String badge;
    public String mapToUse = "Map_LiveMap_Survival_1792_-7_-6.png";
    public GriefPrevention griefPrevention;
    public WorldGuard worldGuard;
    public WorldGuardPlugin worldGuardPlugin;
    public WorldGuardFlagExtentions worldGuardFlagExtentions;
    private HashMap<String, String> loadedPlugins = new HashMap<>();
    private String serverName;
    private WebhookClientBuilder builder;
    private WebhookClient webhookClient;
    private boolean notifyStaff = false;

    public boolean isRenderingMap() {
        return isRenderingMap;
    }

    public void setRenderingMap(boolean renderingMap) {
        isRenderingMap = renderingMap;
    }

    public String getMapToUse() {
        return mapToUse;
    }

    public void setMapToUse(String mapToUse) {
        this.mapToUse = mapToUse;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        saveConfig();
        if(Bukkit.getPluginManager().getPlugin("GriefPrevention").isEnabled()) {
            this.griefPrevention = GriefPrevention.instance;
        } else {System.out.println(badge + " Error: Was unable to find plugin - GriefPrevention. Some features may not work as intended");}
        if(Bukkit.getPluginManager().getPlugin("WorldGuard").isEnabled()) {
            this.worldGuard = WorldGuard.getInstance();
        }  else {System.out.println(badge + " Error: Was unable to find plugin - WorldGuard. Some features may not work as intended");}
        if (Bukkit.getPluginManager().getPlugin("WorldEdit").isEnabled()) {
            this.worldGuardPlugin = WorldGuardPlugin.inst();
        }  else {System.out.println(badge + " Error: Was unable to find plugin - WorldEdit. Some features may not work as intended");}
        if(getConfig().getString("General.ServerName") != null){
            serverName = getConfig().getString("General.ServerName");
        }
        badge = getConfig().getString("General.Messages.Badge") == null ? ChatColor.translateAlternateColorCodes('&', "&3&l[&2TreeboTweaksSP&3&l]&r") : ChatColor.translateAlternateColorCodes('&', getConfig().getString("General.Nessages.Badge"));


        if (getConfig().getBoolean("Tweaks.AutoFillChangeLog")) {
            if(getConfig().getLong("General.Discord.HookID") != 0 || getConfig().getLong("General.Discord.HookToken") != 0) {
                Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                    @Override
                    public void run() {
                        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                            String name = plugin.getName();
                            String version = plugin.getDescription().getVersion();
                            loadedPlugins.put(name, version);
                        }


                        if (getConfig().getConfigurationSection("plugins") == null) {
                            getConfig().set("plugins.TreeboTweaksSP", "0.0.0");
                        }
                        if(getConfig().getString("serverVersion") == null){
                            getConfig().set("serverVersion", "The Janky Old Server Build");
                        }

                        if(!Bukkit.getVersion().equalsIgnoreCase(getConfig().getString("serverVersion"))){
                            sendDiscordMessage(":white_check_mark: Server Jar on " + serverName + " updated from " + getConfig().getString("serverVersion") + " to " + Bukkit.getVersion());
                            getConfig().set("serverVersion", Bukkit.getVersion());
                        }
                        for (String plugin : getConfig().getConfigurationSection("plugins").getKeys(false)) {
                            if (!loadedPlugins.containsKey(plugin)) {
                                //If it's not loaded, it's been removed
                                sendDiscordMessage("<:RX:482590343762673695>" + plugin + " has been removed from " + serverName);
                                getConfig().set("plugins." + plugin, null);
                            } else if (!getConfig().getString("plugins." + plugin).equals(loadedPlugins.get(plugin))) {
                                sendDiscordMessage(":white_check_mark:" + plugin + " has been updated from version " + getConfig().getString("plugins." + plugin) + " to " + loadedPlugins.get(plugin) + " on " + serverName);
                                getConfig().set("plugins." + plugin, loadedPlugins.get(plugin));
                                loadedPlugins.remove(plugin);
                            } else if (getConfig().getString("plugins." + plugin).equals(loadedPlugins.get(plugin))) {
                                loadedPlugins.remove(plugin);
                            }
                        }

                        for (String lPlugin : loadedPlugins.keySet()) {
                            sendDiscordMessage(":ballot_box_with_check:" + lPlugin + " " + loadedPlugins.get(lPlugin) + " has been added to " + serverName);
                            getConfig().set("plugins." + lPlugin, loadedPlugins.get(lPlugin));
                        }
                        saveConfig();
                    }
                }, 200L);
            } else {
                Bukkit.getPluginManager().registerEvents(this, this);
                notifyStaff = true;
            }
        }

        if (getConfig().getBoolean("Tweaks.TimedPvpBlockBreak")) {
            Bukkit.getPluginManager().registerEvents(new LastPvpListener(this), this);
        }
        if (getConfig().getBoolean("Tweaks.TreeTwerking")) {
            Bukkit.getPluginManager().registerEvents(new TwerkingListener(this), this);
        }
        if (getConfig().getBoolean("Tweaks.HandleAST")) {
            Bukkit.getPluginManager().registerEvents(new AstHandler(), this);
        }
        if (getConfig().getBoolean("Tweaks.EasyStoneInResourceWorld")) {
            Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        }
        if (getConfig().getBoolean("Tweaks.DoubleDoors")) {
            Bukkit.getPluginManager().registerEvents(new DoubleDoors(), this);
        }
        if (getConfig().getBoolean("Tweaks.DynmapToIngameMap")) {
            CommandMakeMap cMK = new CommandMakeMap(this);
            InternalMapRenderer renderer = new InternalMapRenderer(this);
            Bukkit.getPluginManager().registerEvents(new MapRenderListener(this), this);
            this.getCommand("makemap").setExecutor(cMK);
            if (!getConfig().getBoolean("MapsGenerated")) {
                renderer.createBlankMaps("Maps.LiveMap");
                getConfig().set("MapsGenerated", true);
                saveConfig();
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                String world = getConfig().getString("DynmapToInGameMapDetails.World") == null ? "Survival" : getConfig().getString("DynmapToInGameMapDetails.World");
                int X = getConfig().getString("DynmapToInGameMapDetails.StartFromX") == null ? -16 : getConfig().getInt("DynmapToInGameMapDetails.StartFromX");
                int Y = getConfig().getString("DynmapToInGameMapDetails.StartFromY") == null ? -16 : getConfig().getInt("DynmapToInGameMapDetails.StartFromY");
                int dimensions = getConfig().getString("DynmapToInGameMapDetails.Dimensions") == null ? 4096 : getConfig().getInt("DynmapToInGameMapDetails.Dimensions");

                cMK.createPng(Bukkit.getConsoleSender(), (new SimpleDateFormat("yyyyMMddHHmm").format(new Date())) + ".png", world, dimensions, X, Y);
            }

            List<String> worldNames = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                worldNames.add(world.getName());
            }

            if (worldNames.contains("Survival")) {
                Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("TreeboTweaks attempting to update map.");
                        cMK.createPng2();
                        runCommandSynchronouslyLater(Bukkit.getConsoleSender(), "bb reload", 1);
                        System.out.println("TreeboTweaks Finished updating map.");
                    }
                }, TimeUnit.SECONDS.toMillis(60), TimeUnit.HOURS.toMillis(1));
            } else {
                System.out.println("Could not find world with name 'Survival'");
            }
            saveConfig();
        }
        if (getConfig().getBoolean("Tweaks.BlockWitherSpawnsNearClaimedLand") && griefPrevention != null) {
            Bukkit.getPluginManager().registerEvents(new BlockWither(this), this);
        }
        if (getConfig().getBoolean("Tweaks.NerfMobDropsWhenNotKilledByPlayer")) {
            Bukkit.getPluginManager().registerEvents(new NerfDrops(this), this);
        }
        if (getConfig().getBoolean("Tweaks.BlockPvPNearOwnClaim") && griefPrevention != null) {
            Bukkit.getPluginManager().registerEvents(new BlockPvPNearClaims(this), this);
        }
        if (getConfig().getBoolean("Tweaks.PlayerKeepMostItemsIfKilledByPlayer") && griefPrevention != null) {
            Bukkit.getPluginManager().registerEvents(new PlayerKeepItems(this), this);
        }
        if (getConfig().getBoolean("Tweaks.WorldGuardFlagExtentions") && griefPrevention != null) {
            worldGuardFlagExtentions = new WorldGuardFlagExtentions(this);
        }
    }

    public void runCommandSynchronouslyLater(CommandSender sender, String command, long delay) {
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(sender, command);
            }
        }, delay);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer() != null && (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SUNFLOWER || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SUNFLOWER)) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SUNFLOWER) {
                if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()
                        && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()
                        && ChatColor.stripColor(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()).toLowerCase().contains("treebucks")) {
                    e.setCancelled(true);
                }
            } else if (e.getPlayer().getInventory().getItemInOffHand().getType() == Material.SUNFLOWER) {
                if (e.getPlayer().getInventory().getItemInOffHand().hasItemMeta()
                        && e.getPlayer().getInventory().getItemInOffHand().getItemMeta().hasDisplayName()
                        && ChatColor.stripColor(e.getPlayer().getInventory().getItemInOffHand().getItemMeta().getDisplayName()).toLowerCase().contains("treebucks")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onStaffJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("treebo.staff") && notifyStaff){
            Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                @Override
                public void run() {
                    if(Bukkit.getOnlinePlayers().contains(e.getPlayer())) {
                        e.getPlayer().sendMessage(badge + "Auto Update Changelog is enabled but the webhook is not configured. Please configure the webhook and restart the server.");
                    }
                }
            }, 40L);
        }
    }

    public void sendDiscordMessage(String content) {

        if (builder == null) {
            builder = new WebhookClientBuilder(getConfig().getLong("General.Discord.HookID"), getConfig().getString("General.Discord.HookToken")); // or id, token
            builder.setThreadFactory((job) -> {
                Thread thread = new Thread(job);
                thread.setName("Hello");
                thread.setDaemon(true);
                return thread;
            });
            builder.setWait(true);
            webhookClient = builder.build();
        }
        webhookClient.send(content);
    }
}
