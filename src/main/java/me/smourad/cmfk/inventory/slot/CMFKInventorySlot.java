package me.smourad.cmfk.inventory.slot;

import me.smourad.cmfk.inventory.CMFKInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CMFKInventorySlot {

    protected final CMFKInventory inventory;

    protected CMFKInventorySlot(CMFKInventory inventory) {
        this.inventory = inventory;
    }

    public abstract void onClick(InventoryClickEvent event);

    public ItemStack getItemStack() {
        ItemStack stack = new ItemStack(getMaterial());
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component
                .text(getName())
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
        );
        stack.setItemMeta(meta);
        return stack;
    }

    protected abstract Material getMaterial();

    protected abstract String getName();

    protected Player getPlayer() {
        return inventory.getPlayer();
    }

}
