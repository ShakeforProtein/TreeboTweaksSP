package me.shakeforprotein.treebotweakssp.Tweaks.DynMapToInGameMap;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InternalMapRenderer extends MapRenderer {

    private TreeboTweaksSP pl;

    public InternalMapRenderer(TreeboTweaksSP main) {
        this.pl = main;
    }


    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        System.out.println("Received Request for Treebo Map Rendered");
        int mapId = mapView.getId();
        int confX = 99;
        int confY = 99;
        BufferedImage image = null;
        String newKey = "";
        String groupKey = "";
        ConfigurationSection cMaps = pl.getConfig().getConfigurationSection("Maps");
        for (String mapGroup : pl.getConfig().getConfigurationSection("Maps").getKeys(false)) {
            for (String key : cMaps.getConfigurationSection(mapGroup).getKeys(false)) {

            }
        }
    }


    private BufferedImage getMapImage(int X, int Y, String key) {
        System.out.println("Attempting to grab image for map/");
        //key = Maps.MapName.GridCoordinates
        String pathToMapFile = "Maps." + key + ".filename";
        System.out.println("PTM -  " + pathToMapFile);
        BufferedImage source;
        try {
            source = ImageIO.read(new File(pl.getDataFolder(), pl.getConfig().getString(pathToMapFile)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return source.getSubimage(X * 128, Y * 128, 128, 128);

    }

    public void createBlankMaps(String configValue) {
/*        pl.setRenderingMap(true);

        for (int i = 0; i < 7; i++) {
            for (int ii = 0; ii < 7; ii++) {

                ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
                MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();


                int mapId = mapMeta.getMapView().getId();

                mapMeta.getMapView().setScale(MapView.Scale.FAR);
                mapMeta.getMapView().setUnlimitedTracking(true);
                mapMeta.getMapView().getRenderers().clear();
                mapMeta.getMapView().getRenderers().add(this);

                mapItem.setItemMeta(mapMeta);
                //int mapId = Bukkit.createMap(Bukkit.getWorlds().get(0)).getId();
                System.out.println("Generating new map id's for TreeboTweaks Dynmap Functions - " + pl.mapToUse + " - MapId: " + mapId);
                //Bukkit.getMap(mapId).setLocked(true);
                pl.getConfig().set(configValue + "." + i + "_" + ii + ".id", mapId);
            }
        }
        pl.getConfig().set(configValue + ".filename", pl.getMapToUse());
        pl.setRenderingMap(false);
    }
*/
    }
}
