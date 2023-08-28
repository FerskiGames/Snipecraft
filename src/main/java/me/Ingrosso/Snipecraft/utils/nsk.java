package me.Ingrosso.Snipecraft.utils;

import me.Ingrosso.Snipecraft.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class nsk {
    private Plugin plugin;
    public nsk(Plugin plugin){
        this.plugin = plugin;
    }
    public NamespacedKey key(String k){
        return new NamespacedKey(this.plugin,k);
    }
}
