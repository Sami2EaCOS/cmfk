package me.smourad.cmfk.team;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class TheGatherer implements Listener {

    private final JavaPlugin plugin;

    private final Map<TeamType, Set<Player>> teams = new EnumMap<>(TeamType.class);

    @Inject
    public TheGatherer(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void openTeamSelector(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {

        }
    }

}
