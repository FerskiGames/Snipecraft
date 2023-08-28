package me.Ingrosso.Snipecraft.listeners;

import me.Ingrosso.Snipecraft.Main;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class PlayerJoinLeave implements Listener {
    private Player player;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        player = event.getPlayer();
        Main.getPlugin().addShooter(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        player = event.getPlayer();
        Main.getPlugin().removeShooter(player);
    }

}
