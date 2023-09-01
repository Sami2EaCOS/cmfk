package me.smourad.cmfk.utils;

import com.google.gson.Gson;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private static final String ITEM_DATA_KEY = "cmfk.item.";

    private final ItemStack stack;
    private ItemMeta meta;

    private final Gson gson;

    private final JavaPlugin plugin;

    @AssistedInject
    public ItemBuilder(@Assisted ItemStack itemStack, JavaPlugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;

        stack = itemStack.clone();
        meta  = stack.hasItemMeta() ? stack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(stack.getType());
    }

    @AssistedInject
    public ItemBuilder(@Assisted String json, JavaPlugin plugin, Gson gson) {
        this(gson.fromJson(json, ItemStack.class), plugin, gson);
    }

    @AssistedInject
    public ItemBuilder(@Assisted Material material, JavaPlugin plugin, Gson gson) {
        this(material, 1, plugin, gson);
    }

    @AssistedInject
    public ItemBuilder(@Assisted Material material, @Assisted int amount, JavaPlugin plugin, Gson gson) {
        this(new ItemStack(material, amount), plugin, gson);
    }

    public ItemBuilder setMaterial(Material material) {
        return setType(material);
    }

    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        try {
            Damageable damageable = (Damageable) meta;
            damageable.setDamage(durability);
        } catch (ClassCastException e) {
            System.out.println("ItemBuilder : Durability Error");
        }

        return this;
    }

    public ItemBuilder setType(Material type) {
        stack.setType(type);
        meta = Bukkit.getItemFactory().asMetaFor(meta, stack);
        return this;
    }

    public ItemBuilder setName(Component name) {
        meta.displayName(name);
        return this;
    }

    public ItemBuilder setName(String name) {
        return setName(Component.text(name));
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        return addEnchantment(enchantment, level, true);
    }

    public ItemBuilder setUnbreakable(boolean state) {
        meta.setUnbreakable(state);
        return this;
    }

    public ItemBuilder setGlowing(boolean state) {
        if (state) {
            meta.addEnchant(Enchantment.DURABILITY, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.DURABILITY);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        return this;
    }

    public ItemBuilder setLore(List<TextComponent> lore) {
        meta.lore(lore);
        return this;
    }

    private List<Component> getLore() {
        return meta.hasLore() ? meta.lore() : new ArrayList<>();
    }

    public boolean removeLoreLine(int index) {
        List<Component> lore = getLore();

        try {
            lore.remove(index);
            meta.lore(lore);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean setLoreLine(Component line, int index) {
        List<Component> lore = getLore();

        try {
            lore.set(index, line);
            meta.lore(lore);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public ItemBuilder addLoreLine(Component... lines) {
        return addLoreLine(Arrays.asList(lines));
    }

    public ItemBuilder addLoreLine(List<Component> lines) {
        List<Component> lore = getLore();
        lore.addAll(lines);
        meta.lore(lore);

        return this;
    }

    public ItemBuilder clearLore() {
        meta.lore(null);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... itemFlags) {
        for (ItemFlag itemFlag : itemFlags)
            meta.addItemFlags(itemFlag);

        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
            leatherArmorMeta.setColor(color);
        } catch (ClassCastException e) {
            System.out.println("ItemBuilder : LeatherArmor Error");
        }

        return this;
    }

    public ItemBuilder setEffect(PotionEffectType effect, int duration, int amplifier) {
        try {
            PotionMeta potionMeta = (PotionMeta) meta;
            potionMeta.addCustomEffect(new PotionEffect(effect, duration, amplifier), true);
        } catch (ClassCastException e) {
            System.out.println("ItemBuilder : Potion Error");
        }

        return this;
    }

    protected NamespacedKey getKey(String key) {
        return new NamespacedKey(plugin, ITEM_DATA_KEY + key);
    }

    public <T> ItemBuilder setGsonPersistentData(String key, Type typeOfT, T value) {
        meta.getPersistentDataContainer().set(getKey(key), PersistentDataType.STRING, gson.toJson(value, typeOfT));
        return this;
    }

    public <T> ItemBuilder setPrimitivePersistentData(String key, PersistentDataType<T, T> type, T value) {
        meta.getPersistentDataContainer().set(getKey(key), type, value);
        return this;
    }

    public <T> T getPersistentData(String key, PersistentDataType<T, T> type) {
        return meta.getPersistentDataContainer().get(getKey(key), type);
    }

    public boolean hasPersistentData(String key) {
        return meta.getPersistentDataContainer().has(getKey(key));
    }

    public <T> T getPersistentData(String key, Type typeOfT) {
        return gson.fromJson(meta.getPersistentDataContainer().get(getKey(key), PersistentDataType.STRING), typeOfT);
    }

    public ItemStack toItemStack() {
        stack.setItemMeta(meta);
        return stack;
    }


}
