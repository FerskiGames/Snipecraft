package me.FerskiGames.Snipecraft.database;

import org.bukkit.entity.Player;

public class Shooter {
    private Player player;

    public Boolean aiming;

    public Shooter(Player player){
        this.player = player;
        this.aiming = false;
    }


    public Player getPlayer(){
        return this.player;
    }

    public boolean getIsAiming(){
        return this.aiming;
    }

    public void setAiming(Boolean aim){
        this.aiming = false;
        if (aim){
            this.aiming = true;
        }
    }
}
