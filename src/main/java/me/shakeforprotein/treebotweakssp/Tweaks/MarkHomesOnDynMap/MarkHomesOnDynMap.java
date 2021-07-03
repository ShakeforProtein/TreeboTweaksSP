package me.shakeforprotein.treebotweakssp.Tweaks.MarkHomesOnDynMap;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import java.io.File;

public class MarkHomesOnDynMap {

    private TreeboTweaksSP pl;
    private DynmapAPI dynmapAPI;
    private MarkerAPI markerAPI;

    public MarkHomesOnDynMap(TreeboTweaksSP main, DynmapAPI dynmapAPI, MarkerAPI markerAPI){
        this.pl = main;
        this.dynmapAPI = dynmapAPI;
        this.markerAPI = markerAPI;
    }

    public void enable(){
        MarkerIcon house = markerAPI.getMarkerIcon("House");
        MarkerSet playerHomes = markerAPI.createMarkerSet("playerHomes", "Player Homes", markerAPI.getMarkerIcons(), false);
        Plugin TTele = Bukkit.getPluginManager().getPlugin("TreeboTeleport");

        File[] files = new File(TTele.getDataFolder() + File.separator + "homes").listFiles();
        if(files != null) {
            for (File file : files) {
                YamlConfiguration homesYml = YamlConfiguration.loadConfiguration(file);

                for(String key : homesYml.getConfigurationSection("homes").getKeys(false)){
                    playerHomes.createMarker(file.getName().split("\\.")[0], Bukkit.getOfflinePlayer(file.getName().split("\\.")[0]).getName(), homesYml.getString("homes." + key + ".World"), homesYml.getInt("homes." + key + ".x"), homesYml.getInt("homes." + key + ".y"), homesYml.getInt("homes." + key + ".z"), house, false);
                }
            }
        }
    }
}
