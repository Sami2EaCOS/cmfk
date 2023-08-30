package me.smourad.cmfk.inventory.slot.resource;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.inventory.CMFKInventory;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CloseSlot extends ActionSlot {

    @AssistedInject
    public CloseSlot(
            @Assisted CMFKInventory inventory
    ) {
        super(inventory);
    }

    @Override
    protected Material getMaterial() {
        return Material.BARRIER;
    }

    @Override
    protected String getName() {
        return "Fermer";
    }

    @Override
    public boolean onAction(InventoryClickEvent event) {
        inventory.close();
        return true;
    }

}
