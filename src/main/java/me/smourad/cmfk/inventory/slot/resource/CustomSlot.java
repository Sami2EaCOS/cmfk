package me.smourad.cmfk.inventory.slot.resource;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.inventory.CMFKInventory;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CustomSlot extends ActionSlot {

    private final Runnable action;
    private final Material material;
    private final String name;

    @AssistedInject
    public CustomSlot(
            @Assisted CMFKInventory inventory,
            @Assisted Material material,
            @Assisted String name,
            @Assisted Runnable action
    ) {
        super(inventory);

        this.material = material;
        this.name = name;
        this.action = action;
    }

    @Override
    protected Material getMaterial() {
        return material;
    }

    @Override
    protected String getName() {
        return name;
    }

    @Override
    public boolean onAction(InventoryClickEvent event) {
        action.run();
        return true;
    }
}
