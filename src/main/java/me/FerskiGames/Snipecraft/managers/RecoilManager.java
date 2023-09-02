package me.FerskiGames.Snipecraft.managers;

import me.FerskiGames.Snipecraft.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
public class RecoilManager {
    private Player player;
    private float recoil;
    private Location location;
    private float pitch;

    public RecoilManager(Player player, float recoil){
        this.player = player;
        this.recoil = Math.abs(recoil);
        this.location = player.getLocation();
        this.pitch = location.getPitch();
    }

    private float getTargetPitch(){
        if (recoil == 0 || pitch < -75){
            return pitch;
        }
        float factor = ((float) 90.0 - Math.abs(pitch))/90;
        return pitch - recoil*factor;
    }

    public void applyRecoil() {
        float targetPitch = getTargetPitch();
        if (targetPitch == pitch) {
            return;
        }
        location.setPitch(targetPitch);
        player.teleport(location);
    }

    private boolean isNoScope(){
        return !Main.getPlugin().getShooter(player).getIsAiming() || player.getItemInUse() == null;
    }
}
