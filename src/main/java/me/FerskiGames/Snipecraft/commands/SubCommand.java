package me.FerskiGames.Snipecraft.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract PermissionHelper setPermissionHelper(String permKey);

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args);

}
