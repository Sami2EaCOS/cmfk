package me.smourad.cmfk.team;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum TeamType {

    RED(Material.RED_WOOL, "Rouge", 0xFF5555),
    BLUE(Material.BLUE_WOOL, "Bleue", 0x5555FF),
    YELLOW(Material.YELLOW_WOOL, "Jaune", 0xFFFF55),
    GREEN(Material.GREEN_WOOL, "Verte", 0x55FF55);

    private final Material wool;
    private final String name;
    private final int hexColor;

    TeamType(Material wool, String name, int hexColor) {
        this.wool = wool;
        this.name = name;
        this.hexColor = hexColor;
    }
}
