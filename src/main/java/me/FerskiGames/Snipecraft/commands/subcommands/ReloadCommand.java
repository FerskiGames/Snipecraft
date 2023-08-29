package me.FerskiGames.Snipecraft.commands.subcommands;

import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.commands.PermissionHelper;
import me.FerskiGames.Snipecraft.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    private PermissionHelper ph;

    public ReloadCommand(){ ph = setPermissionHelper("reloadCommand"); }

    @Override
    public PermissionHelper setPermissionHelper(String permKey){
        return new PermissionHelper(permKey);
    }

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
        if(ph.permissionNeeded()){
            if(!ph.hasPerm(player)){
                ph.sendNoPermMessage(player);
                if(ph.noPermissionMessage()){
                    ph.sendMissingPerm(player);
                }
                return;
            }
        }
        try{
            Main.getPlugin().getConfiguration().reload();
            Main.getPlugin().getConfiguration().update();
            Main.getPlugin().getPermissions().reload();
            Main.getPlugin().getPermissions().update();
            Main.getPlugin().getSniperRifles().reload();
            Main.getPlugin().getSniperRifles().update();
            player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.WHITE + "Snipecraft reloaded.");
        }catch(Exception e){
            player.sendMessage(Main.getPlugin().getPrefix() + "" + ChatColor.RED + "Could not reload Snipecraft correctly. Restart your server.");
        }
    }
}
