package me.FerskiGames.Snipecraft.commands.subcommands;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.commands.PermissionHelper;
import me.FerskiGames.Snipecraft.commands.SubCommand;
import me.FerskiGames.Snipecraft.database.SniperRifle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveCommand extends SubCommand {
    private PermissionHelper ph;

    public GiveCommand(){
        ph = setPermissionHelper("giveCommand");
    }

    @Override
    public PermissionHelper setPermissionHelper(String permKey){
        return new PermissionHelper(permKey);
    }

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
        if(ph.permissionNeeded()){
            if(!ph.hasPerm(player)){
                ph.sendNoPermMessage(player);
                if(ph.noPermissionMessage()){
                    ph.sendMissingPerm(player);
                }
                return;
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

}
