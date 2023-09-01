package me.smourad.cmfk.game.team;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.smourad.cmfk.factory.InventoryFactory;
import me.smourad.cmfk.factory.ItemBuilderFactory;
import me.smourad.cmfk.game.TheDirector;
import me.smourad.cmfk.utils.CMFKConstant;
import me.smourad.cmfk.utils.ItemBuilder;
import me.smourad.cmfk.utils.ItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Singleton
public class TheGatherer implements Listener {

    private final InventoryFactory inventoryFactory;
    private final ItemBuilderFactory itemBuilderFactory;
    private final TheDirector director;

    private final Map<TeamType, Set<UUID>> teams = new EnumMap<>(TeamType.class);

    @Inject
    public TheGatherer(
            JavaPlugin plugin,
            InventoryFactory inventoryFactory,
            ItemBuilderFactory itemBuilderFactory,
            TheDirector director
    ) {
        this.inventoryFactory = inventoryFactory;
        this.itemBuilderFactory = itemBuilderFactory;
        this.director = director;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void fillInventoryOnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (isInTeam(player)) {
            player.getInventory().clear();
        }

        if (director.isNotStarted()) {
            PlayerInventory inventory = player.getInventory();
            inventory.clear();
            inventory.setHeldItemSlot(0);
            inventory.setItem(0, getGathererItem());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemBuilder item = itemBuilderFactory.createItem(event.getCurrentItem());

        if (item.hasPersistentData(CMFKConstant.ITEM_TYPE_KEY) && event.getWhoClicked() instanceof Player player) {
            int type = item.getPersistentData(CMFKConstant.ITEM_TYPE_KEY, PersistentDataType.INTEGER);

            if (Objects.equals(type, ItemType.GATHERER_MENU.ordinal())) {
                openTeamSelector(player);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            ItemBuilder item = itemBuilderFactory.createItem(event.getItem());

            if (item.hasPersistentData(CMFKConstant.ITEM_TYPE_KEY)) {
                int type = item.getPersistentData(CMFKConstant.ITEM_TYPE_KEY, PersistentDataType.INTEGER);

                if (Objects.equals(type, ItemType.GATHERER_MENU.ordinal())) {
                    openTeamSelector(event.getPlayer());
                    event.setCancelled(true);
                }
            }
        }
    }

    protected ItemStack getGathererItem() {
        return itemBuilderFactory.createItem(Material.NETHER_STAR)
                .setPrimitivePersistentData(CMFKConstant.ITEM_TYPE_KEY, PersistentDataType.INTEGER, ItemType.GATHERER_MENU.ordinal())
                .toItemStack();
    }

    public boolean isInTeam(Player player) {
        return teams.entrySet().stream().anyMatch(entry -> entry.getValue().contains(player.getUniqueId()));
    }

    public void openTeamSelector(Player player) {
        if (director.isNotStarted()) {
            inventoryFactory.createTeamInventory(player).open();
        }
    }

    public void selectTeam(Player player, TeamType teamType) {
        if (director.isNotStarted()) {
            UUID uuid = player.getUniqueId();

            clearPlayer(uuid);
            teams.putIfAbsent(teamType, new HashSet<>());
            teams.get(teamType).add(uuid);

            setupPlayer(player, teamType);
            player.sendMessage(Component.text("Vous avez été ajouté dans l'Équipe %s".formatted(teamType.getName())));
        }
    }

    protected void setupPlayer(Player player, TeamType teamType) {
        TextComponent name = Component.text(player.getName(), teamType.getColor());
        player.displayName(name);
        player.playerListName(name);
    }

    protected void clearPlayer(UUID uuid) {
        teams.forEach((t, players) -> players.remove(uuid));
    }

    public List<Player> getPlayers(TeamType teamType) {
        return teams.getOrDefault(teamType, Set.of()).stream()
                .map(Bukkit::getPlayer)
                .toList();
    }


    public void openAdminMenu(Player player) {
        if (director.isNotStarted()) {
            inventoryFactory.createGathererAdminMenu(player).open();
        }
    }

}
