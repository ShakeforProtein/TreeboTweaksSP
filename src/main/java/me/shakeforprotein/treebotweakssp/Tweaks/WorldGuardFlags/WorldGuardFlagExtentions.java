package me.shakeforprotein.treebotweakssp.Tweaks.WorldGuardFlags;


import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.shakeforprotein.treebotweakssp.TreeboTweaksSP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class WorldGuardFlagExtentions {

    private TreeboTweaksSP pl;

    //Custom flag list;
    private Flag testFlag;
    private Flag boolTestFlag;


    public WorldGuardFlagExtentions(TreeboTweaksSP main){
        this.pl = main;

        this.testFlag = registerStringFlag("Shake_Flag", "");
        this.boolTestFlag = registerStateFlag("Shake_Bool_Flag", false);

        Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){

                    LocalPlayer wgPlayer = pl.worldGuardPlugin.wrapPlayer(player);
                    Vector playerVector = wgPlayer.getPosition();
                    RegionQuery query = pl.worldGuard.getPlatform().getRegionContainer().createQuery();
                    //Location loc = BukkitUtil.toLocation(pl.worldGuard.getPlatform()., playerVector);


                    /*
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                    STUFF GOES HERE IN FUTURE
                     */
                }
            }
        }, 50L, 50L);
    }

    public Flag registerStateFlag(String name, boolean deafultState){
        FlagRegistry registry = pl.worldGuard.getInstance().getFlagRegistry();
        try {
            // create a flag with the name "Shake-Flag", defaulting to false
            StateFlag flag = new StateFlag(name, deafultState);
            registry.register(flag);
            //testFlag = flag; // only set our field if there was no error
            return flag;
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get(name);
            if (existing instanceof StateFlag) {
                System.out.println(pl.badge + "Error registering flag with name: " + name + ". Another plugin has already registered a flag with this name, things may be unpredictable.");
                return (StateFlag) existing;
            } else {
                System.out.println(pl.badge + "Error registering flag with name: " + name + ". Another plugin has already registered a flag with this name with different type value");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
                return null;
            }
        }
    }

    public Flag registerStringFlag (String name, String defaultValue){
        FlagRegistry registry = pl.worldGuard.getInstance().getFlagRegistry();
        try{
            StringFlag flag = new StringFlag(name,"");
            return flag;
        }
        catch (FlagConflictException e){
            Flag<?> existing = registry.get(name);
            if (existing instanceof StateFlag) {
                System.out.println(pl.badge + "Error registering flag with name: " + name + ". Another plugin has already registered a flag with this name, things may be unpredictable.");
                return (StringFlag) existing;
            } else {
                System.out.println(pl.badge + "Error registering flag with name: " + name + ". Another plugin has already registered a flag with this name with different type value");
                return null;
            }
        }
    }
}
