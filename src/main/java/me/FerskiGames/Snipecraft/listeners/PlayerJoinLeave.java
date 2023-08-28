package me.FerskiGames.Snipecraft.listeners;

import me.FerskiGames.Snipecraft.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
