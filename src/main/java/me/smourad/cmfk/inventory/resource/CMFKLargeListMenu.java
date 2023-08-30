package me.smourad.cmfk.inventory.resource;

import me.smourad.cmfk.factory.InventorySlotFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CMFKLargeListMenu<T> extends CMFKListMenu<T> {

    protected CMFKLargeListMenu(
            Player player,
            boolean holderSlotsBackgroundRemoved,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        super(player, holderSlotsBackgroundRemoved, plugin, inventorySlotFactory);
    }

    @Override
    protected int[] getHolderSlots() {
        return new int[]{
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43
        };
    }

}
