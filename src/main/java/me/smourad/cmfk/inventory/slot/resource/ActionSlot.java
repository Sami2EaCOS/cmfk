package me.smourad.cmfk.inventory.slot.resource;

import me.smourad.cmfk.inventory.slot.CMFKInventorySlot;
import me.smourad.cmfk.inventory.CMFKInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ActionSlot extends CMFKInventorySlot {

    protected ActionSlot(CMFKInventory inventory) {
        super(inventory);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.isShiftClick() || event.getClick().equals(ClickType.DOUBLE_CLICK)) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        Player player = getPlayer();
        if (onAction(event)) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 0.5f);
        }
    }

    public abstract boolean onAction(InventoryClickEvent event);

}
