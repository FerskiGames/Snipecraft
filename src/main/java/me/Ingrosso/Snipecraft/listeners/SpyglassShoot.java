package me.Ingrosso.Snipecraft.listeners;

import me.Ingrosso.Snipecraft.Main;
import me.Ingrosso.Snipecraft.managers.SniperRifleManager;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Random;

public class SpyglassShoot implements Listener {
    private Player player;
    private ItemStack itemInMainHand;
    private ItemMeta sniperMeta;
    private PersistentDataContainer sniperData;


    @EventHandler
    public void onPlayerSwap(PlayerSwapHandItemsEvent event) {
        this.player = event.getPlayer();
        this.itemInMainHand = player.getInventory().getItemInMainHand();

        if(!itemInMainHand.getType().equals(Material.SPYGLASS) || !itemInMainHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(),"isRifle"), PersistentDataType.STRING)){
            return;
        }
        event.setCancelled(true);


        SniperRifleManager srm = new SniperRifleManager(player, itemInMainHand);
        if(!srm.sniperRifleDefined()){
            player.getInventory().setItemInMainHand(new ItemStack(Material.SPYGLASS,1));
            return;
        }
        srm.action();
    }

    @EventHandler
    public void onPlayerAim(PlayerInteractEvent event) {
        this.player = event.getPlayer();
        if(event.getItem().getType() != Material.SPYGLASS){
            return;
        }
        if(Main.getPlugin().getShooter(player) == null){
            Main.getPlugin().addShooter(player);
        }
        Main.getPlugin().getShooter(player).setAiming(false);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable(){
            @Override
            public void run(){
                Main.getPlugin().getShooter(player).setAiming(true);
            }
        }, Main.getPlugin().getConfiguration().getLong("quickScopeLag"));
    }

    public void onItemChange(PlayerItemHeldEvent event){
        this.player = event.getPlayer();
        if(Main.getPlugin().getShooter(player).getIsAiming()){
            event.setCancelled(true);
        }
    }
}
