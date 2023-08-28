package me.Ingrosso.Snipecraft;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.Ingrosso.Snipecraft.commands.CommandManager;
import me.Ingrosso.Snipecraft.commands.TabCompletionManager;
import me.Ingrosso.Snipecraft.database.Shooter;
import me.Ingrosso.Snipecraft.listeners.Collision;
import me.Ingrosso.Snipecraft.listeners.InventoryClick;
import me.Ingrosso.Snipecraft.listeners.PlayerJoinLeave;
import me.Ingrosso.Snipecraft.listeners.SpyglassShoot;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static Main plugin;
    private static ConsoleCommandSender logger;
    public String prefix;
    public YamlDocument config;

    public YamlDocument sniperRifles;

    public YamlDocument permissions;

    private ArrayList<Shooter> shooters;

    private ArrayList<UUID> cooldowns;

    @Override
    public void onEnable(){
        plugin = this;
        logger = Bukkit.getConsoleSender();
        shooters = new ArrayList<>();
        cooldowns = new ArrayList<>();

        Bukkit.getServer().getPluginManager().registerEvents(new SpyglassShoot(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Collision(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinLeave(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), this);

        getCommand("sc").setExecutor(new CommandManager());
        getCommand("sc").setTabCompleter(new TabCompletionManager());

        // Create and update the config file
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version")).build());
            prefix = config.getString("chatPrefix");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create and update the permissions file
        try {
            permissions = YamlDocument.create(new File(getDataFolder(), "permissions.yml"), getResource("permissions.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version")).build());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create the sniperRifles file
        if (! new File(getDataFolder(),"sniperRifles.yml").exists()){
            try {
                sniperRifles = YamlDocument.create(new File(getDataFolder(), "sniperRifles.yml"), getResource("sniperRifles.yml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            try {
                sniperRifles = YamlDocument.create(new File(getDataFolder(),"sniperRifles.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        logger.sendMessage(getPrefix() + "Snipecraft Loaded");
    }

    @Override
    public void onDisable(){
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getServer().getLogger().info(getPrefix() + "Snipecraft Disabled");
    }

    public static Main getPlugin(){
        return plugin;
    }

    public YamlDocument getConfiguration(){
        return this.config;
    }

    public YamlDocument getSniperRifles(){
        return this.sniperRifles;
    }

    public static void log(String str){
        logger.sendMessage(str);
    }


    public ArrayList<Shooter> getShooters(){ return shooters; }

    public Shooter getShooter(Player player){
        Shooter shooter = null;
        for (int i = 0; i < shooters.size(); i++){
            if(shooters.get(i).getPlayer().getUniqueId().equals(player.getUniqueId())){
                shooter = shooters.get(i);
            }
        }
        return shooter;
    }

    public void addShooter(Player player){
        shooters.add(new Shooter(player));
    }

    public void removeShooter(Player player){
        for (int i = 0; i < shooters.size(); i++){
            if(shooters.get(i).getPlayer().getUniqueId().equals(player.getUniqueId())){
                shooters.remove(i);
            }
        }
    }

    public ArrayList<UUID> getCooldowns(){
        return cooldowns;
    }

    public String getPrefix(){
        return prefix;
    }
}
