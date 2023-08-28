package me.Ingrosso.Snipecraft.commands.subcommands;

import me.Ingrosso.Snipecraft.Main;
import me.Ingrosso.Snipecraft.commands.SubCommand;
import me.Ingrosso.Snipecraft.database.SniperRifle;
import me.Ingrosso.Snipecraft.listeners.SpyglassShoot;
import me.Ingrosso.Snipecraft.managers.SniperRifleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends SubCommand {
    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Give a sniper rifle to a player.";
    }

    @Override
    public String getSyntax() {
        return "/sc give <player> <sniper>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(permissionNeeded()){
            if(!hasPerm(player)){
                player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.RED + "You don't have permission to do this.");
            }
        }

        if (args.length == 1 || args.length == 2 ) {
            player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.WHITE + "Correct Usage:\n" + getSyntax());
        }else{
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.RED + "Player not found");
                return;
            }
            //Give Sniper actions
            SniperRifle sniperRifle = new SniperRifle(args[2]);
            if (sniperRifle.sniperRifleExists()){
                sniperRifle.build();
                if(target.getInventory().firstEmpty() != -1){
                    target.getInventory().addItem(sniperRifle.getSniperRifleItem());
                }else{
                    target.getWorld().dropItem(target.getLocation(), sniperRifle.getSniperRifleItem());
                }
            }else{
                player.sendMessage(Main.getPlugin().getPrefix() + ChatColor.RED + "That rifle is not defined.");
            }

        }
    }

    @Override
    public boolean permissionNeeded() {
        boolean needsPerm = false;
        if(Main.getPlugin().getConfiguration().getBoolean("giveCommandPermissionNeeded")){
            needsPerm = true;
        }
        return needsPerm;
    }

    @Override
    public boolean hasPerm(Player player) {
        boolean has = false;
        if(player.hasPermission(Main.getPlugin().getConfiguration().getString("giveCommand"))){
            has = true;
        }
        return has;
    }
}
