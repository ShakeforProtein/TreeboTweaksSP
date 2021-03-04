package me.shakeforprotein.treebotweakssp.Tweaks.DynMapToInGameMap;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.*;


public class MapRenderListener implements Listener {


    private TreeboTweaksSP pl;
    private InternalMapRenderer internalMapRenderer;

    public MapRenderListener(TreeboTweaksSP main) {
        this.pl = main;
        this.internalMapRenderer = new InternalMapRenderer(pl);
    }

    @EventHandler
    public void mapInitializeEvent(MapInitializeEvent e) {
        if (pl.isRenderingMap) {
            System.out.println("Initializing maps");
            int id = e.getMap().getId();
            e.getMap().setScale(MapView.Scale.FAR);
            e.getMap().setUnlimitedTracking(true);
            e.getMap().getRenderers().clear();
            e.getMap().getRenderers().add(new InternalMapRenderer(pl));
        }
    }

}
