package me.smourad.cmfk.generator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.smourad.cmfk.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Singleton
public class OrePopulator extends BlockPopulator implements Listener {

    private final NavigableMap<Double, CMFKOre> weightedOres = new TreeMap<>();
    private final double total;

    @Inject
    public OrePopulator(JavaPlugin plugin) {
        this.total = initWeightedOres();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    protected double initWeightedOres() {
        List<CMFKOre> ores = List.of(CMFKOre.values());
        double sum = 0;
        for (CMFKOre ore : ores) {
            sum += ore.getRarity();
            weightedOres.put(sum, ore);
        }
        return sum;
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        e.getWorld().getPopulators().add(this);
    }

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion region) {
        int buffer = region.getBuffer();
        int cx = chunkX * buffer;
        int cz = chunkZ * buffer;

        int i = random.nextInt(100);
        while (i < 98) {
            CMFKOre selected = weightedOres.higherEntry(random.nextDouble(total)).getValue();
            int rarity = selected.getRarity();
            int height = selected.getHeight();

            if (random.nextInt(100) < rarity) {
                int x = cx + random.nextInt(16);
                int y = random.nextInt(height);
                int z = cz + random.nextInt(16);

                generateOreVein(selected, x, y, z, random, region);
            }

            i = random.nextInt(100);
        }
    }

    protected void generateOreVein(CMFKOre ore, int x, int y, int z, Random random, LimitedRegion region) {
        double reducer = 0.995;
        double chance = 100.;

        while (chance >= random.nextDouble(100)) {
            Material type = region.getType(x, y, z);
            if (region.isInRegion(x, y, z) && type.isBlock()) {
                if (BlockUtils.CAVE_WALL.contains(type)) {
                    if (Objects.equals(type, Material.DEEPSLATE)) {
                        region.setType(x, y, z, ore.getDeepslate());
                    } else {
                        region.setType(x, y, z, ore.getNormal());
                    }
                }

                switch (random.nextInt(6)) {
                    case 0 -> --x;
                    case 1 -> ++x;
                    case 2 -> --y;
                    case 3 -> ++y;
                    case 4 -> --z;
                    case 5 -> ++z;
                    default -> {}
                }
            }

            chance *= reducer;
        }
    }

}
