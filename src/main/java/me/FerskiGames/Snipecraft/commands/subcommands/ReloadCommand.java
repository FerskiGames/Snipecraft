package me.FerskiGames.Snipecraft.commands.subcommands;

import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload Snipecraft.";
    }

    @Override
    public String getSyntax() {
        return "/sc reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        try{
            Main.getPlugin().getConfiguration().reload();
            Main.getPlugin().getConfiguration().update();
            Main.getPlugin().getSniperRifles().reload();
            Main.getPlugin().getSniperRifles().update();
            player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.WHITE + "Snipecraft reloaded.");
        }catch(Exception e){
            player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.RED + "Could not reload Snipecraft correctly. Restart your server.");
        }
    }

    @Override
    public boolean permissionNeeded() {
        boolean needsPerm = false;
        if(Main.getPlugin().getConfiguration().getBoolean("reloadCommandPermissionNeeded")){
            needsPerm = true;
        }
        return needsPerm;
    }

    @Override
    public boolean hasPerm(Player player) {
        boolean has = false;
        if(player.hasPermission(Main.getPlugin().getConfiguration().getString("reloadCommand"))){
            has = true;
        }
        return has;
    }
}
