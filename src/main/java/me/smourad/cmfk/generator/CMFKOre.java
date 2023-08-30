package me.smourad.cmfk.generator;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum CMFKOre {

    IRON(Material.IRON_ORE, 80, 80),
    LAPIS_LAZULI(Material.LAPIS_ORE, 100, 93),
    COAL(Material.COAL_ORE, 80, 97),
    REDSTONE(Material.REDSTONE_ORE, 30, 85),
    GOLD(Material.GOLD_ORE, 40, 80),
    EMERALD(Material.EMERALD_ORE, 40, 10),
    DIAMOND(Material.DIAMOND_ORE, 20, 15);

    private final Material material;
    private final int height;
    private final int rarity;

    CMFKOre(Material material, int height, int rarity) {
        this.material = material;
        this.height = height;
        this.rarity = rarity;
    }

}
