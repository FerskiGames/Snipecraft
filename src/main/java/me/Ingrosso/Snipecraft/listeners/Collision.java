package me.Ingrosso.Snipecraft.listeners;

import me.Ingrosso.Snipecraft.Main;
import me.Ingrosso.Snipecraft.managers.SniperRifleManager;
import me.Ingrosso.Snipecraft.utils.nsk;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Collision implements Listener {
    private nsk NSK;

    @EventHandler
    public void onCollide(EntityDamageByEntityEvent event) {
        this.NSK = new nsk(Main.getPlugin());
        if(event.getDamager().getPersistentDataContainer().has(NSK.key("sc"),PersistentDataType.STRING)){
            event.setDamage(event.getDamager().getPersistentDataContainer().get(NSK.key("damage"),PersistentDataType.DOUBLE));
            event.getDamager().remove();
        }
    }

}
