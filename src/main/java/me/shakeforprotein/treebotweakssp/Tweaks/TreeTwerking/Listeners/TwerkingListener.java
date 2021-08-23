package me.shakeforprotein.treebotweakssp.Tweaks.TreeTwerking.Listeners;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TwerkingListener implements Listener {

    private TreeboTweaksSP pl;
    private List<Material> saplings = new ArrayList<>();
    private List<TreeType> treeTypesSmall = new ArrayList<>();
    private List<TreeType> treeTypesLarge = new ArrayList<>();
    private String direction = "";

    public TwerkingListener(TreeboTweaksSP main) {
        this.pl = main;
        for (Material mat : Material.values()) {
            if (mat.name().toLowerCase().endsWith("sapling") && !mat.name().toLowerCase().contains("potted")) {
                saplings.add(mat);
            }
        }
        treeTypesLarge.add(TreeType.JUNGLE);
        treeTypesLarge.add(TreeType.MEGA_REDWOOD);
        treeTypesSmall.add(TreeType.SMALL_JUNGLE);
        treeTypesSmall.add(TreeType.TREE);
        treeTypesSmall.add(TreeType.BIRCH);
        treeTypesSmall.add(TreeType.ACACIA);
        treeTypesSmall.add(TreeType.TALL_REDWOOD);
        treeTypesSmall.add(TreeType.REDWOOD);
        treeTypesSmall.add(TreeType.TALL_BIRCH);
    }

    @EventHandler
    public void PlayerTwerk(PlayerToggleSneakEvent e) {
        if (!e.getPlayer().isSneaking()) {
            Location loc = e.getPlayer().getLocation();
            for (int x = -5; x < 5; x++) {
                for (int y = -2; y < 2; y++) {
                    for (int z = -5; z < 5; z++) {
                        Block block = new Location(loc.getWorld(), loc.getBlockX() + x + 0.5, loc.getY() + y + 0.5, loc.getZ() + z + 0.5).getBlock();
                        /* */

                        if(block.getState().getBlockData() instanceof Sapling){
                            Sapling sapling = (Sapling) block.getState().getBlockData();
                            Material saplingType = block.getType();
                            if(sapling.getStage() < sapling.getMaximumStage() && ThreadLocalRandom.current().nextInt(100) < 10){
                                sapling.setStage(sapling.getStage() +1);
                                block.getLocation().getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().add(block.getX() + 0.5,block.getY() + 0.8,block.getZ() + 0.5), 3, new Particle.DustOptions(Color.GREEN, 3f));
                                block.setBlockData(sapling);
                            } else if(sapling.getStage() == sapling.getMaximumStage()){

                                block.getLocation().getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().add(block.getX() + 0.5,block.getY() + 0.8,block.getZ() + 0.5), 3, new Particle.DustOptions(Color.ORANGE, 5f));
                            } else {

                                block.getLocation().getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().add(block.getX() + 0.5,block.getY() + 0.8,block.getZ() + 0.5), 3, new Particle.DustOptions(Color.WHITE, 1f));
                            }
                        }
                    }
                }
            }
        }
    }

                        /*
                        for (Material mat : saplings) {
                            if (!block.getType().isAir() && block.getType() != Material.VOID_AIR && block.getType() == mat) {
                                if (Math.floor(ThreadLocalRandom.current().nextInt(5)) == 4) {
                                    Sapling sapling = (Sapling) block.getBlockData();

                                    if (sapling.getStage() <= sapling.getMaximumStage() ) {
                                        if (ThreadLocalRandom.current().nextInt(20) > 15) {
                                            sapling.setStage(sapling.getMaximumStage());
                                            block.setBlockData(sapling);
                                            block.getState().update();
                                            makeTree(block);
                                            BlockGrowEvent blockGrowEvent = new BlockGrowEvent(block, block.getState());

                                            if(block.getType() == Material.AIR){
                                                block.setType(mat);
                                                for(double a = -0.5; a < 0.5; a = a + 0.1){
                                                    for(double b = 0.1; b < 0.5; b = b + 0.1){
                                                        for(double c = -0.5; c < 0.5; c = c + 0.1){
                                                            block.getLocation().getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().add(a + 0.5,b + 0.8,c + 0.5), 3, new Particle.DustOptions(Color.RED, 5.5f));
                                                            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(mat, 1));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                for(double a = -0.5; a < 0.5; a = a + 0.1){
                                    for(double b = 0.1; b < 0.5; b = b + 0.1){
                                        for(double c = -0.5; c < 0.5; c = c + 0.1){
                                            block.getLocation().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(a + 0.5,b + 0.8,c + 0.5), 3);
                                        }
                                    }
                                }
                                block.getState().update();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeTree(Block block){
        Material saplingType = block.getType();
        Location l = block.getLocation();
        direction = "";
        boolean big = false;
        boolean grew = false;
        if(new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ()).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ() + 1).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() + 1).getBlock().getType() == saplingType){
            big = true;
            direction = "SOUTHEAST";
        } else
        if(new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ()).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ() + 1).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() + 1).getBlock().getType() == saplingType){
            big = true;

            direction = "SOUTHWEST";
        } else
        if(new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ()).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ() - 1).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() - 1).getBlock().getType() == saplingType){
            big = true;
            direction = "NORTHWEST";
        } else
        if(new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ()).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ() - 1).getBlock().getType() == saplingType
                && new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() - 1).getBlock().getType() == saplingType){
            big = true;
            direction = "NORTHEAST";
        }

        if(big){
            for(TreeType treeType : treeTypesLarge){
                String ttName = treeType.name();
                if(ttName.toLowerCase().contains("redwood")){
                    ttName = ttName.toLowerCase().replace("redwood", "spruce");
                }

                if(ttName.toLowerCase() == "big_tree"){ttName = "tall_oak";}
                if(ttName.toLowerCase().contains(saplingType.name().toLowerCase().split("_")[0].split(" ")[0])) {
                    switch (direction) {

                        case "SOUTHEAST":
                            l.getBlock().setType(Material.AIR);

                            new Location(l.getWorld(), l.getBlockX()+1, l.getBlockY(), l.getBlockZ()).getBlock().setType(Material.AIR);
                            new Location(l.getWorld(), l.getBlockX()+1, l.getBlockY(), l.getBlockZ()+1).getBlock().setType(Material.AIR);
                            new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()+1).getBlock().setType(Material.AIR);
                            break;
                        case "SOUTHWEST":
                            l.getBlock().setType(Material.AIR);

                            new Location(l.getWorld(), l.getBlockX()-1, l.getBlockY(), l.getBlockZ()).getBlock().setType(Material.AIR);
                            new Location(l.getWorld(), l.getBlockX()-1, l.getBlockY(), l.getBlockZ()+1).getBlock().setType(Material.AIR);
                            new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()+1).getBlock().setType(Material.AIR);

                            break;
                        case "NORTHWEST":
                            l.getBlock().setType(Material.AIR);

                            new Location(l.getWorld(), l.getBlockX()-1, l.getBlockY(), l.getBlockZ()).getBlock().setType(Material.AIR);
                            new Location(l.getWorld(), l.getBlockX()-1, l.getBlockY(), l.getBlockZ()-1).getBlock().setType(Material.AIR);
                            new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()-1).getBlock().setType(Material.AIR);

                            break;
                       case "NORTHEAST":
                           l.getBlock().setType(Material.AIR);

                           new Location(l.getWorld(), l.getBlockX()+1, l.getBlockY(), l.getBlockZ()).getBlock().setType(Material.AIR);
                           new Location(l.getWorld(), l.getBlockX()+1, l.getBlockY(), l.getBlockZ()-1).getBlock().setType(Material.AIR);
                           new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()-1).getBlock().setType(Material.AIR);

                           break;
                }
                    l.getWorld().generateTree(l, treeType);
                    
                    Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
                        @Override
                        public void run() {                                
                            if(l.getBlock().getType() == Material.AIR) {
                              switch (direction) {

                                    case "SOUTHEAST":
                                        l.getBlock().setType(saplingType);

                                        new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ()).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ() + 1).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() + 1).getBlock().setType(saplingType);

                                        break;
                                    case "SOUTHWEST":
                                        l.getBlock().setType(saplingType);

                                        new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ()).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ() + 1).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() + 1).getBlock().setType(saplingType);

                                        break;
                                    case "NORTHWEST":
                                        l.getBlock().setType(saplingType);

                                        new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ()).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY(), l.getBlockZ() - 1).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() - 1).getBlock().setType(saplingType);

                                        break;
                                    case "NORTHEAST":
                                        l.getBlock().setType(saplingType);

                                        new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ()).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY(), l.getBlockZ() - 1).getBlock().setType(saplingType);
                                        new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ() - 1).getBlock().setType(saplingType);

                                        break;
                                }

                            }
                        }
                    }, 1L);


                    grew = true;
                }
            }
        }

        if(grew){return;}
        else if(!grew){
            for(TreeType treeType : treeTypesSmall){
                String ttName = treeType.name();
                if(ttName.toLowerCase().contains("redwood")){
                    ttName = ttName.toLowerCase().replace("redwood", "spruce");
                }

                if(ttName.toLowerCase().contains("tree")){
                    ttName = "oak";
                }

                if(ttName.toLowerCase().contains(saplingType.name().toLowerCase().split("_")[0].split(" ")[0])){
                    block.setType(Material.AIR);
                    l.getWorld().generateTree(l, treeType);
                    break;
                }
            }
        }


    }

   */
}
