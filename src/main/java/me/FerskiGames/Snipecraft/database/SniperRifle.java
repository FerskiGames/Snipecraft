package me.FerskiGames.Snipecraft.database;

import me.FerskiGames.Snipecraft.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class SniperRifle {
    private String key;
    private String name;
    private boolean hasGlow;
    private ItemStack sniperRifleItem;
    private ItemMeta itemMeta;
    private PersistentDataContainer data;
    private String rifleUUID;

    public SniperRifle(String key){
        this.key = key;
    }

    public boolean sniperRifleExists() {
        if (Main.getPlugin().getSniperRifles().getSection(key) != null) {
            return true;
        }
        return false;
    }

    public void build(){
        this.name = readName();
        this.hasGlow = readHasGlow();

        this.sniperRifleItem = new ItemStack(Material.SPYGLASS, 1);
        this.itemMeta = sniperRifleItem.getItemMeta();
        this.itemMeta.setDisplayName(name);
        this.data = itemMeta.getPersistentDataContainer();
        this.rifleUUID = String.valueOf(UUID.randomUUID());

        itemMeta.setDisplayName(this.name);

        data.set(new NamespacedKey(Main.getPlugin(),"key"), PersistentDataType.STRING, key);

        data.set(new NamespacedKey(Main.getPlugin(),"rifleid"), PersistentDataType.STRING, rifleUUID);

        data.set(new NamespacedKey(Main.getPlugin(),"isrifle"), PersistentDataType.STRING, "true");

        data.set(new NamespacedKey(Main.getPlugin(),"hasglow"), PersistentDataType.STRING, String.valueOf(hasGlow));

        if(hasGlow) {
            itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        sniperRifleItem.setItemMeta(itemMeta);
    }


    public ItemStack getSniperRifleItem(){
        return this.sniperRifleItem;
    }

    public String readName(){
        return Main.getPlugin().getSniperRifles().getSection(key).getString( "name");
    }

    public boolean readHasGlow(){
        if(Main.getPlugin().getSniperRifles().getSection(key).getBoolean("hasGlow")){
            return true;
        }
        return false;
    }
}
