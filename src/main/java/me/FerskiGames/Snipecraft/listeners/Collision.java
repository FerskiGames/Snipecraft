package me.FerskiGames.Snipecraft.listeners;

import me.FerskiGames.Snipecraft.Main;
import me.FerskiGames.Snipecraft.utils.nsk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class Collision implements Listener {
    private nsk NSK;

    @EventHandler
    public void onCollide(EntityDamageByEntityEvent event) {
        this.NSK = new nsk(Main.getPlugin());
        if(event.getDamager().getPersistentDataContainer().has(NSK.key("sc"),PersistentDataType.STRING)){
            event.setDamage(event.getDamager().getPersistentDataContainer().get(NSK.key("damage"),PersistentDataType.DOUBLE));
            event.getDamager().remove();
        }
    }

}
