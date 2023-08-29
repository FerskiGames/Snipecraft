package me.FerskiGames.Snipecraft.commands;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.commands.subcommands.GiveCommand;
import me.FerskiGames.Snipecraft.commands.subcommands.ReloadCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    private PermissionHelper ph;

    public CommandManager(){
        subCommands.add(new GiveCommand());
        subCommands.add(new ReloadCommand());
        ph = new PermissionHelper("commandUse");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (ph.permissionNeeded()){
                if (!ph.hasPerm(player)){
                    ph.sendNoPermMessage(player);
                    if(ph.noPermissionMessage()){
                        ph.sendMissingPerm(player);
                    }
                }else if (args.length > 0){
                    boolean matched = false;
                    for (int i = 0; i < getSubCommands().size(); i++){
                        if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                            getSubCommands().get(i).perform(player,args);
                            matched = true;
                        }
                    }
                    if (!matched){ sendHelp(player); }
                }else{
                    sendHelp(player);
                }
            }
        }
        return true;
    }

    public void sendHelp(Player player){
        String helpMessage = Main.getPlugin().getPrefix() + ChatColor.WHITE + "Snipers help:" +
                "\n  " + ChatColor.WHITE + "List of commands:";
        for (int i = 0; i < getSubCommands().size(); i++){
            helpMessage += "\n  " + ChatColor.GRAY + " → " + ChatColor.WHITE + getSubCommands().get(i).getSyntax();
        }
        player.sendMessage(helpMessage);
    }

    public ArrayList<SubCommand> getSubCommands(){
        return subCommands;
    }
}
