package me.smourad.cmfk.inventory.slot.resource;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.inventory.slot.CMFKInventorySlot;
import me.smourad.cmfk.inventory.CMFKInventory;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BackgroundSlot extends CMFKInventorySlot {

    private final Material icon;

    @AssistedInject
    public BackgroundSlot(
            @Assisted CMFKInventory inventory,
            @Assisted Material icon
    ) {
        super(inventory);

        this.icon = icon;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    protected Material getMaterial() {
        return icon;
    }

    @Override
    protected String getName() {
        return "";
    }

}
