package me.shakeforprotein.treebotweakssp.Tweaks.DoubleDoors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoubleDoors implements Listener {

    @EventHandler
    public void PlayerOpenDoor(PlayerInteractEvent e){
        if(e.getClickedBlock() != null){
            if(e.getClickedBlock().getBlockData() instanceof Door && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() != Material.IRON_DOOR){
                Door door = (Door) e.getClickedBlock().getBlockData();
                String hinge = door.getHinge().name();
                BlockFace facing = door.getFacing();
                //west left  0 0 0 - west right 0 0 -1      west right 0 0 0 - west left 0 0 +1
                //east left 0 0 0 - east right 0 0 +1       east right 0 0 0 - east left 0 0 -1
                //south left 0 0 0 - south right -1 0 0     south right 0 0 0 - south left +1 0 0
                //north left 0 0 0 - north right +1 0 0      north right 0 0 0 - north left -1 0 0

                Location loc = e.getClickedBlock().getLocation();
                Location loc2 = e.getClickedBlock().getLocation();
                if (hinge.equalsIgnoreCase("left")){
                    switch(facing) {
                        case NORTH: loc2 = loc.add(1,0,0);
                            break;
                        case SOUTH: loc2 = loc.add(-1,0,0);
                            break;
                        case EAST: loc2 = loc.add(0,0,1);
                            break;
                        case WEST: loc2 = loc.add(0,0,-1);
                            break;
                    }
                } else if(hinge.equalsIgnoreCase("right")) {
                    switch(facing) {
                        case NORTH: loc2 = loc.add(-1,0,0);
                            break;
                        case SOUTH: loc2 = loc.add(1,0,0);
                            break;
                        case EAST: loc2 = loc.add(0,0,-1);
                            break;
                        case WEST: loc2 = loc.add(0,0,1);
                            break;
                    }
                }
                if(loc2.getBlock().getType() != Material.AIR && loc2.getBlock().getBlockData() instanceof Door && !hinge.equalsIgnoreCase(((Door) loc2.getBlock().getBlockData()).getHinge().name())){
                    BlockData door2 = loc2.getBlock().getBlockData();
                    ((Door) door2).setOpen(!door.isOpen());
                    loc2.getBlock().setBlockData(door2);
                }
            }
        }
    }

    @EventHandler
    public void RedstoneTriggerDoor(BlockRedstoneEvent e){
        if(e.getBlock() != null){
            if(e.getBlock().getBlockData() instanceof Door){
                Door door = (Door) e.getBlock().getBlockData();
                String hinge = door.getHinge().name();
                BlockFace facing = door.getFacing();
                //west left  0 0 0 - west right 0 0 -1      west right 0 0 0 - west left 0 0 +1
                //east left 0 0 0 - east right 0 0 +1       east right 0 0 0 - east left 0 0 -1
                //south left 0 0 0 - south right -1 0 0     south right 0 0 0 - south left +1 0 0
                //north left 0 0 0 - north right +1 0 0      north right 0 0 0 - north left -1 0 0

                Location loc = e.getBlock().getLocation();
                Location loc2 = e.getBlock().getLocation();
                if (hinge.equalsIgnoreCase("left")){
                    switch(facing) {
                        case NORTH: loc2 = loc.add(1,0,0);
                            break;
                        case SOUTH: loc2 = loc.add(-1,0,0);
                            break;
                        case EAST: loc2 = loc.add(0,0,1);
                            break;
                        case WEST: loc2 = loc.add(0,0,-1);
                            break;
                    }
                } else if(hinge.equalsIgnoreCase("right")) {
                    switch(facing) {
                        case NORTH: loc2 = loc.add(-1,0,0);
                            break;
                        case SOUTH: loc2 = loc.add(1,0,0);
                            break;
                        case EAST: loc2 = loc.add(0,0,-1);
                            break;
                        case WEST: loc2 = loc.add(0,0,1);
                            break;
                    }
                }
                if(loc2.getBlock().getType() != Material.AIR && loc2.getBlock().getBlockData() instanceof Door && !hinge.equalsIgnoreCase(((Door) loc2.getBlock().getBlockData()).getHinge().name())){
                    BlockData door2 = loc2.getBlock().getBlockData();
                    ((Door) door2).setOpen(!door.isOpen());
                    loc2.getBlock().setBlockData(door2);
                }
            }
        }
    }
}
