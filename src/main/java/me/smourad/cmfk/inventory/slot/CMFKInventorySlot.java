package me.smourad.cmfk.inventory.slot;

import me.smourad.cmfk.inventory.CMFKInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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

        List<String> description = getDescription();
        if (!description.isEmpty()) {
            List<TextComponent> lore = description.stream().map(text -> Component
                    .text(text)
                    .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            ).toList();
            meta.lore(lore);
        }

        stack.setItemMeta(meta);
        return stack;
    }

    protected abstract Material getMaterial();

    protected abstract String getName();

    protected List<String> getDescription() {
        return List.of();
    }

    protected Player getPlayer() {
        return inventory.getPlayer();
    }

}
