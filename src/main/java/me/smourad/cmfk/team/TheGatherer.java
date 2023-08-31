package me.smourad.cmfk.team;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.smourad.cmfk.factory.InventoryFactory;
import me.smourad.cmfk.game.TheDirector;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Singleton
public class TheGatherer implements Listener {

    private final InventoryFactory inventoryFactory;
    private final TheDirector director;

    private final Map<TeamType, Set<UUID>> teams = new EnumMap<>(TeamType.class);

    @Inject
    public TheGatherer(
            JavaPlugin plugin,
            InventoryFactory inventoryFactory,
            TheDirector director
    ) {
        this.inventoryFactory = inventoryFactory;
        this.director = director;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void openTeamSelector(Player player) {
        if (Objects.equals(director.getState(), GameState.WAITING)) {
            inventoryFactory.createTeamInventory(player).open();
        }
    }

    public void selectTeam(Player player, TeamType teamType) {
        if (Objects.equals(director.getState(), GameState.WAITING)) {
            UUID uuid = player.getUniqueId();

            clearPlayer(uuid);
            teams.putIfAbsent(teamType, new HashSet<>());
            teams.get(teamType).add(uuid);

            player.sendMessage(Component.text("Vous avez été ajouté dans l'Équipe %s".formatted(teamType.getName())));
        }
    }

    protected void clearPlayer(UUID uuid) {
        teams.forEach((t, players) -> players.remove(uuid));
    }

    public List<Player> getPlayers(TeamType teamType) {
        return teams.getOrDefault(teamType, Set.of()).stream()
                .map(Bukkit::getPlayer)
                .toList();
    }



}
