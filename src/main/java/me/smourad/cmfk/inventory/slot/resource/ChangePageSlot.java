package me.smourad.cmfk.inventory.slot.resource;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.inventory.CMFKInventory;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChangePageSlot extends ActionSlot {

    private final int page;

    @AssistedInject
    public ChangePageSlot(
            @Assisted CMFKInventory inventory,
            @Assisted int page
    ) {
        super(inventory);

        this.page = page;
    }

    @Override
    protected Material getMaterial() {
        return Material.SPECTRAL_ARROW;
    }

    @Override
    protected String getName() {
        return page > inventory.getPage()
                ? "Page suivante"
                : "Page précédente";
    }

    @Override
    public boolean onAction(InventoryClickEvent event) {
        inventory.open(page);
        return true;
    }

}
