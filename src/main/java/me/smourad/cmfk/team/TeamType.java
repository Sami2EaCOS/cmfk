package me.smourad.cmfk.team;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum TeamType {

    RED(Material.RED_WOOL, "Rouge"),
    BLUE(Material.BLUE_WOOL, "Bleue"),
    YELLOW(Material.YELLOW_WOOL, "Jaune"),
    GREEN(Material.GREEN_WOOL, "Verte");

    private final Material wool;
    private final String name;

    TeamType(Material wool, String name) {
        this.wool = wool;
        this.name = name;
    }
}
