package me.FerskiGames.Snipecraft.listeners;

import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.managers.SniperRifleManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClick implements Listener {
    private Player player;
    private ItemStack itemClicked;
    private ItemMeta sniperMeta;
    private PersistentDataContainer sniperData;


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        this.player = (Player) event.getWhoClicked();
        this.itemClicked = event.getCurrentItem();
        if(itemClicked == null || !itemClicked.getType().equals(Material.SPYGLASS)){
            return;
        }
        this.sniperMeta = itemClicked.getItemMeta();
        this.sniperData = sniperMeta.getPersistentDataContainer();
        if(!itemClicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(),"isrifle"), PersistentDataType.STRING)){
            return;
        }

        SniperRifleManager srm = new SniperRifleManager(player, itemClicked);
        if(!srm.sniperRifleDefined()){
            event.setResult(Event.Result.DENY);
            event.setCurrentItem(new ItemStack(Material.SPYGLASS, 1));
            return;
        }
    }
}
