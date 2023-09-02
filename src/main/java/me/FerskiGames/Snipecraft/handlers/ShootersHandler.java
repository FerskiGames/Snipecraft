package me.FerskiGames.Snipecraft.handlers;

import me.FerskiGames.Snipecraft.database.Shooter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ShootersHandler {
    private ArrayList<Shooter> shooters;

    public ShootersHandler(){
        shooters = new ArrayList<>();
    }

    public ArrayList<Shooter> getShooters(){ return shooters; }

    public Shooter getShooter(Player player){
        Shooter shooter = null;
        for (int i = 0; i < shooters.size(); i++){
            if(shooters.get(i).getPlayer().getUniqueId().equals(player.getUniqueId())){
                shooter = shooters.get(i);
            }
        }
        return shooter;
    }

    public void addShooter(Player player){
        shooters.add(new Shooter(player));
    }

    public void removeShooter(Player player){
        for (int i = 0; i < shooters.size(); i++){
            if(shooters.get(i).getPlayer().getUniqueId().equals(player.getUniqueId())){
                shooters.remove(i);
            }
        }
    }
}
