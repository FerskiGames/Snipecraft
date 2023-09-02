package me.FerskiGames.Snipecraft.managers;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.database.SniperRifle;
import me.FerskiGames.Snipecraft.utils.nsk;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class SniperRifleManager {
    private Player player;
    private String key;
    private UUID rifleID;
    private SniperRifle sniperRifle;
    private ItemStack sniperRifleItem;
    private ItemMeta sniperRifleMeta;
    private PersistentDataContainer sniperRifleData;
    private nsk NSK;

    public SniperRifleManager(Player player, ItemStack item){
        this.player = player;
        this.NSK = new nsk(Main.getPlugin());
        this.sniperRifleItem = item;
        this.sniperRifleMeta = sniperRifleItem.getItemMeta();
        this.sniperRifleData = sniperRifleMeta.getPersistentDataContainer();
        this.key = extractKey();
        this.rifleID = extractRifleID();
    }


    public void action(){
        World world = player.getWorld();
        Location loc = player.getLocation();

        if(onCD()){
            return;
        }

        if(!hasAmmo()){
            world.playSound(loc, Sound.valueOf(getConfig().getString("noAmmoSound")), 1.0f, 1.0f);
            return;
        }

        // ACTION
        takeAmmo();

        loc.setY(loc.getY() + getConfig().getDouble("adjY"));
        loc.setX(loc.getX() + getConfig().getDouble("adjX"));
        loc.setZ(loc.getZ() + getConfig().getDouble("adjZ"));

        Vector dir = player.getEyeLocation().getDirection();
        if(isNoScope()){
            Main.getPlugin().getShootersHandler().getShooter(player).setAiming(false);
            Random rand = new Random();
            double  randNoScope = Main.getPlugin().getConfiguration().getDouble("noScopeError");
            double xFactor = rand.nextDouble() * (randNoScope*2) - randNoScope;
            dir.setX(dir.getX() + xFactor);
            double yFactor = rand.nextDouble() * (randNoScope*2) - randNoScope;
            dir.setY(dir.getY() + yFactor);
            double zFactor = rand.nextDouble() * (randNoScope*2) - randNoScope;
            dir.setZ(dir.getZ() + zFactor);
        }

        // Shoot an arrow with the speed specified in the config
        Projectile projectile;
        if(readProjectileType().equalsIgnoreCase("ARROW")){
            projectile = world.spawn(loc, Arrow.class);
        }else if(readProjectileType().equalsIgnoreCase("EGG")){
            projectile = world.spawn(loc, Egg.class);
        }else if(readProjectileType().equalsIgnoreCase("SNOWBALL")){
            projectile = world.spawn(loc, Snowball.class);
        }else{
            projectile = world.spawn(loc, Arrow.class);
        }
        projectile.getPersistentDataContainer().set(NSK.key("sc"),PersistentDataType.STRING,"enabled");
        projectile.getPersistentDataContainer().set(NSK.key("damage"),PersistentDataType.DOUBLE,readDamage());
        projectile.getPersistentDataContainer().set(NSK.key("shooter"),PersistentDataType.STRING,player.getUniqueId().toString());
        projectile.setVelocity(dir.multiply(readProjectileSpeed()));

        // Play a sound and particle effect
        world.playSound(loc, readShotSound(), 1.0f, 1.0f);
        world.spawnParticle(readShotParticle(), loc, 10, 0.5, 0.5, 0.5, 0.1);

        // Recoil only if hardscoping
        if(!isNoScope()){
            RecoilManager recoil = new RecoilManager(player,readRecoil());
            recoil.applyRecoil();
        }

        // Set sniper on cool down
        setOnCD();
    }

    public void setOnCD(){
        Main.getPlugin().getCooldowns().add(rifleID);
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Main.getPlugin().getCooldowns().remove(rifleID);
            }
        }, readFireDelay());
    }

    public boolean hasAmmo(){
        boolean has = false;
        ItemStack[] contents = player.getInventory().getContents();
        for(int i = 0; i < contents.length; i++){
            if(contents[i] != null){
                if(contents[i].getType().toString().equalsIgnoreCase(readProjectileType())){
                    has = true;
                }
            }
        }
        return has;
    }

    public boolean isNoScope(){
        return !Main.getPlugin().getShootersHandler().getShooter(player).getIsAiming() || player.getItemInUse() == null;
    }

    public void takeAmmo(){
        player.getInventory().removeItem(new ItemStack(Material.getMaterial(readProjectileType()),1));
    }

    //Action
    public boolean sniperRifleDefined() {
        if (Main.getPlugin().getSniperRifles().getSection(sniperRifleData.get(NSK.key("key"), PersistentDataType.STRING)) != null) {
            return true;
        }
        return false;
    }

    public boolean onCD(){
        boolean cd = false;
        if(Main.getPlugin().getCooldowns().contains(rifleID)){
            cd = true;
        }
        return cd;
    }

    public YamlDocument getConfig(){ return Main.getPlugin().getConfiguration(); }

    public String extractKey(){
        return sniperRifleData.get(NSK.key("key"),PersistentDataType.STRING);
    }

    public UUID extractRifleID(){
        return UUID.fromString(sniperRifleData.get(NSK.key("rifleID"),PersistentDataType.STRING));
    }


    public String readProjectileType(){
        return Main.getPlugin().getSniperRifles().getString(key + ".projectileType");
    }

    public double readProjectileSpeed(){
        return Main.getPlugin().getSniperRifles().getDouble(key + ".projectileSpeed");
    }

    public double readDamage(){
        return Main.getPlugin().getSniperRifles().getDouble(key + ".damage");
    }

    public float readRecoil(){
        return Main.getPlugin().getSniperRifles().getFloat(key + ".recoil");
    }

    public long readFireDelay(){
        return Main.getPlugin().getSniperRifles().getLong(key + ".fireDelay");
    }

    public Particle readShotParticle(){
        return Particle.valueOf(Main.getPlugin().getSniperRifles().getString(key + ".shotParticle"));
    }

    public Sound readShotSound(){
        return Sound.valueOf(Main.getPlugin().getSniperRifles().getString(key + ".shotSound"));
    }


    public boolean extractHasGlow() {
        if (sniperRifleData.get(NSK.key("hasGlow"), PersistentDataType.STRING).equals("true")) {
            return true;
        }
        return false;
    }


}
