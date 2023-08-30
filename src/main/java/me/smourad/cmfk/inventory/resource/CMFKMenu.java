package me.smourad.cmfk.inventory.resource;

import me.smourad.cmfk.factory.InventorySlotFactory;
import me.smourad.cmfk.inventory.CMFKInventory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CMFKMenu extends CMFKInventory {

    protected CMFKMenu(
            Player player,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        this(player, 6, plugin, inventorySlotFactory);
    }

    protected CMFKMenu(
            Player player,
            int height,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        super(player, height, plugin, inventorySlotFactory);
    }

}
