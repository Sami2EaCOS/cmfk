package me.smourad.cmfk.generator;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum CMFKOre {

    IRON(Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE, 80, 80),
    LAPIS_LAZULI(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, 100, 93),
    COAL(Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE, 80, 97),
    REDSTONE(Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE, 30, 85),
    GOLD(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, 40, 80),
    EMERALD(Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, 40, 20),
    DIAMOND(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, 20, 25);

    private final Material normal;
    private final Material deepslate;
    private final int height;
    private final int rarity;

    CMFKOre(Material normal, Material deepslate, int height, int rarity) {
        this.normal = normal;
        this.deepslate = deepslate;
        this.height = height;
        this.rarity = rarity;
    }

}
