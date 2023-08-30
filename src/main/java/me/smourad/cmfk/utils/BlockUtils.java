package me.smourad.cmfk.utils;

import org.bukkit.Material;

import java.util.EnumSet;
import java.util.Set;

public class BlockUtils {

    private BlockUtils() {}

    public static final Set<Material> CAVE_WALL = EnumSet.of(
            Material.STONE, Material.ANDESITE, Material.DIORITE, Material.TUFF, Material.DEEPSLATE, Material.GRANITE
    );
}
