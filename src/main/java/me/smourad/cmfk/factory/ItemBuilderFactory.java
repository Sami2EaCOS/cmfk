package me.smourad.cmfk.factory;

import me.smourad.cmfk.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface ItemBuilderFactory {

    ItemBuilder createItem(ItemStack itemStack);

    ItemBuilder createItem(String json);

    ItemBuilder createItem(Material material);

    ItemBuilder createItem(Material material, int amount);

}
