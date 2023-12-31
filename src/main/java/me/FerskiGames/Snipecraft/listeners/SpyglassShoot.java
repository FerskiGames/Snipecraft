package me.FerskiGames.Snipecraft.listeners;

import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.commands.PermissionHelper;
import me.FerskiGames.Snipecraft.managers.SniperRifleManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class SpyglassShoot implements Listener {
    private PermissionHelper ph;
    private Player player;
    private ItemStack itemInMainHand;

    public SpyglassShoot(){
        ph = new PermissionHelper("snipersUse");
    }
    @EventHandler
    public void onPlayerSwap(PlayerSwapHandItemsEvent event) {
        if(!Main.getPlugin().getConfiguration().getBoolean("allowedWorlds.all") &&
        !Main.getPlugin().getConfiguration().getList("allowedWorlds.list").contains(event.getPlayer().getWorld().getName())){
           return;
        }

        this.player = event.getPlayer();
        this.itemInMainHand = player.getInventory().getItemInMainHand();

        if(ph.permissionNeeded()){
            if(!ph.hasPerm(player)){
                return;
            }
        }

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
        if(event.getItem() == null || event.getItem().getType() != Material.SPYGLASS){
            return;
        }
        if(Main.getPlugin().getShootersHandler().getShooter(player) == null){
            Main.getPlugin().getShootersHandler().addShooter(player);
        }
        Main.getPlugin().getShootersHandler().getShooter(player).setAiming(false);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable(){
            @Override
            public void run(){
                Main.getPlugin().getShootersHandler().getShooter(player).setAiming(true);
            }
        }, Main.getPlugin().getConfiguration().getLong("quickScopeLag"));
    }

    public void onItemChange(PlayerItemHeldEvent event){
        this.player = event.getPlayer();
        if(Main.getPlugin().getShootersHandler().getShooter(player).getIsAiming()){
            event.setCancelled(true);
        }
    }
}
