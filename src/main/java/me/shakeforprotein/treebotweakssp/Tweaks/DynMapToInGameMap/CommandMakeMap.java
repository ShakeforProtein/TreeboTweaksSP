package me.shakeforprotein.treebotweakssp.Tweaks.DynMapToInGameMap;

import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommandMakeMap implements CommandExecutor {

    TreeboTweaksSP pl;

    public CommandMakeMap(TreeboTweaksSP main){
        this.pl = main;
    }






    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        String world = args[0];
        int dimensions = 4096;
        int startFromX = 32;
        int startFromY = 32;

        if(args[0] == null){
            world = "Survival";
        }

        if(args[1] != null){
            dimensions = Integer.parseInt(args[1]);
        }

        if(args[2] != null){
            startFromX = Integer.parseInt(args[2]);
        }
        if(args[3] != null){
            startFromY = Integer.parseInt(args[3]);
        }

        String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        sender.sendMessage(createPng(sender, fileName, world, dimensions, startFromX, startFromY));
        return true;
    }





    public String createPng(CommandSender sender, String fileName, String world, int dimensions, int startfromX, int startfromY){
        sender.sendMessage("Starting map creation");
            FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File file) {

                    if(!file.getName().startsWith("z")){
                        return true;
                    }
                    return false;
                }
            };
            //sender.sendMessage(pl.getDataFolder().getParent() +File.separator + "dynmap" + File.separator + "web" + File.separator + "tiles" + File.separator + "Survival" + File.separator + "flat" + File.separator + "-1_-1");
            File[] dynmapImagesNE = new File(pl.getDataFolder().getParent() +File.separator + "dynmap" + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "-1_-1").listFiles(filter);
            File[] dynmapImagesNW = new File(Bukkit.getPluginManager().getPlugin("dynmap").getDataFolder() + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "-1_0").listFiles(filter);
            File[] dynmapImagesSE = new File(Bukkit.getPluginManager().getPlugin("dynmap").getDataFolder() + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "0_-1").listFiles(filter);
            File[] dynmapImagesSW = new File(Bukkit.getPluginManager().getPlugin("dynmap").getDataFolder() + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "0_0").listFiles(filter);


            int outWidth = dimensions;
            int outHeight = dimensions;

            BufferedImage outputImage = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = outputImage.getGraphics();

            for(File file : dynmapImagesNE){
                //sender.sendMessage(file.getName());
                addToCanvas(file, graphics, sender, startfromX, startfromY);
            }
             for(File file : dynmapImagesNW){
                //sender.sendMessage(file.getName());
                addToCanvas(file, graphics, sender, startfromX, startfromY);
            }
            for(File file : dynmapImagesSE){
                //sender.sendMessage(file.getName());
                addToCanvas(file, graphics, sender, startfromX, startfromY);
            }
            for(File file : dynmapImagesSW){
                //sender.sendMessage(file.getName());
                addToCanvas(file, graphics, sender, startfromX, startfromY);
            }

            graphics.dispose();

            try {
                ImageIO.write(outputImage, "PNG", new File(pl.getDataFolder(), "Map_" + fileName + "_" + world + "_" + dimensions + "_" + startfromX + "_" + startfromY +".png"));
                return "Image write complete.";
            } catch (IOException e){
                e.printStackTrace();
            }

            //Dynmap: zzz scale in 64x64 chunks, zz in 32x32chunks, z in 16x16 chunks, and blank in 8x8 chunks.
            //Minecraft:

            return "Failed to create image";

    }

    public String createPng2(){
        CommandSender sender = Bukkit.getConsoleSender();
        String world = "Survival";
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {

                if(!file.getName().startsWith("z")){
                    return true;
                }
                return false;
            }
        };
        //sender.sendMessage(pl.getDataFolder().getParent() +File.separator + "dynmap" + File.separator + "web" + File.separator + "tiles" + File.separator + "Survival" + File.separator + "flat" + File.separator + "-1_-1");
        File[] dynmapImagesNE = new File(pl.getDataFolder().getParent() +File.separator + "dynmap" + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "-1_-1").listFiles(filter);
        File[] dynmapImagesNW = new File(Bukkit.getPluginManager().getPlugin("dynmap").getDataFolder() + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "-1_0").listFiles(filter);
        File[] dynmapImagesSE = new File(Bukkit.getPluginManager().getPlugin("dynmap").getDataFolder() + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "0_-1").listFiles(filter);
        File[] dynmapImagesSW = new File(Bukkit.getPluginManager().getPlugin("dynmap").getDataFolder() + File.separator + "web" + File.separator + "tiles" + File.separator + world + File.separator + "flat" + File.separator + "0_0").listFiles(filter);


        int outWidth = 1792;
        int outHeight = 1792;

        BufferedImage outputImage = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = outputImage.getGraphics();

        for(File file : dynmapImagesNE){
            //sender.sendMessage(file.getName());
            addToCanvas(file, graphics, sender, -7, -6);
        }
        for(File file : dynmapImagesNW){
            //sender.sendMessage(file.getName());
            addToCanvas(file, graphics, sender, -7, -6);
        }
        for(File file : dynmapImagesSE){
            //sender.sendMessage(file.getName());
            addToCanvas(file, graphics, sender, -7, -6);
        }
        for(File file : dynmapImagesSW){
            //sender.sendMessage(file.getName());
            addToCanvas(file, graphics, sender, -7, -6);
        }

        graphics.dispose();

        try {
            File oldmap = new File(pl.getDataFolder().getParent() + "BannerBoard" + File.separator + "images", "current_map.png");
            System.out.println("TreeboTweaks FileDeltion for 'current_map.png' - " + oldmap.delete());
            ImageIO.write(outputImage, "PNG", new File(pl.getDataFolder().getParent() + "BannerBoard" + File.separator + "images", "current_map.png"));
            return "Image write complete.";
        } catch (IOException e){
            e.printStackTrace();
        }

        //Dynmap: zzz scale in 64x64 chunks, zz in 32x32chunks, z in 16x16 chunks, and blank in 8x8 chunks.
        //Minecraft:

        return "Failed to create image";

    }

    private void addToCanvas(File file, Graphics output, CommandSender sender, int startfromX, int startfromY){
        try {
            BufferedImage inputImage = ImageIO.read(file);

            int fileX = Integer.parseInt(file.getName().split("_")[0]);
            int fileZ = Integer.parseInt(file.getName().split("_")[1].split("\\.j")[0]);
            //sender.sendMessage(fileX + "");
            //sender.sendMessage(fileZ + "");

            int imgX = (fileX - startfromX) * 128;
            int imgY = (-fileZ - startfromY) * 128;
           // sender.sendMessage("adding " + file.getName() + " to new image in position " + imgX + "/" + imgY);
            output.drawImage(inputImage, imgX, imgY, null);

        } catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
}
