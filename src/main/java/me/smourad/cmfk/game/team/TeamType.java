package me.smourad.cmfk.game.team;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

@Getter
public enum TeamType {

    RED(Material.RED_WOOL, "Rouge", NamedTextColor.RED),
    BLUE(Material.BLUE_WOOL, "Bleue", NamedTextColor.BLUE),
    YELLOW(Material.YELLOW_WOOL, "Jaune", NamedTextColor.YELLOW),
    GREEN(Material.GREEN_WOOL, "Verte", NamedTextColor.GREEN);

    private final Material wool;
    private final String name;
    private final NamedTextColor color;

    TeamType(Material wool, String name, NamedTextColor color) {
        this.wool = wool;
        this.name = name;
        this.color = color;
    }
}
