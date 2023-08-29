package me.FerskiGames.Snipecraft.commands;

import me.FerskiGames.Snipecraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompletionManager implements TabCompleter {
    private PermissionHelper ph;
    private CommandManager commandManager;
    public TabCompletionManager(){
        ph = new PermissionHelper("tabCompletion");
        commandManager = new CommandManager();
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(ph.permissionNeeded()){
            if(!ph.hasPerm((Player)commandSender)){
                return null;
            }
        }
        if(args.length == 1){
            List<String> cmds = new ArrayList<>();
            for(int i = 0; i < commandManager.getSubCommands().size(); i++){
                cmds.add(commandManager.getSubCommands().get(i).getName());
            }
            return cmds;
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("reload")){
                return null;
            }else if(args[0].equalsIgnoreCase("give")){
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for(int i = 0; i < players.length; i++){
                    playerNames.add(players[i].getDisplayName());
                }
                return playerNames;
            }
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("reload")){
                return null;
            }else if(args[0].equalsIgnoreCase("give")){
                List<String> sniperRiflesNames = new ArrayList<>();
                String[] keys = new String[Main.getPlugin().getSniperRifles().getKeys().size()];
                Main.getPlugin().getSniperRifles().getKeys().toArray(keys);
                for(int i = 0; i < keys.length; i++){
                    sniperRiflesNames.add(keys[i]);
                }
                return sniperRiflesNames;
            }
        }
        return null;
    }
}
