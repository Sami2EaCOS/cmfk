package me.smourad.cmfk.inventory.resource;

import me.smourad.cmfk.factory.InventorySlotFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CMFKCarvedMenu extends CMFKMenu {

    protected CMFKCarvedMenu(Player player, JavaPlugin plugin, InventorySlotFactory inventorySlotFactory) {
        super(player, plugin, inventorySlotFactory);
    }

    protected CMFKCarvedMenu(Player player, int height, JavaPlugin plugin, InventorySlotFactory inventorySlotFactory) {
        super(player, height, plugin, inventorySlotFactory);
    }

    @Override
    protected void onOpen() {
        super.onOpen();

        for (int i=1; i<height-1; i++) {
            for (int j=1; j<8; j++) {
                removeSlot(i*9 + j);
            }
        }
    }

}
