package me.FerskiGames.Snipecraft.commands;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.FerskiGames.Snipecraft.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PermissionHelper {
    private String permKey;
    public PermissionHelper(String permKey){
        this.permKey = permKey;
    }

    public String getPermKey(){ return permKey; };

    public String getPermNeededKey() { return getPermKey() + "PermissionNeeded"; }

    public boolean permissionNeeded(){
        return getPermFile().getBoolean(getPermNeededKey());
    }

    public boolean hasPerm(Player player){
        return player.hasPermission(getPermFile().getString(permKey));
    }

    public boolean noPermissionMessage(){
        return(getPermFile().getBoolean("sendMissingPerm"));
    }

    public void sendNoPermMessage(Player player){
        player.sendMessage(Main.getPlugin().getPrefix() + getPermFile().getString("noPermissionMessage"));
    }

    public void sendMissingPerm(Player player){
        player.sendMessage(ChatColor.WHITE + getPermFile().getString(permKey));
    }

    public YamlDocument getPermFile(){ return Main.getPlugin().getPermissions(); }

}
