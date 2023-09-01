package me.smourad.cmfk.inventory.resource.team;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.factory.InventorySlotFactory;
import me.smourad.cmfk.game.team.TeamType;
import me.smourad.cmfk.game.team.TheGatherer;
import me.smourad.cmfk.inventory.resource.CMFKCarvedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TeamInventory extends CMFKCarvedMenu {

    private final TheGatherer gatherer;

    @AssistedInject
    public TeamInventory(
            @Assisted Player player,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory,
            TheGatherer gatherer
    ) {
        super(player, 3, plugin, inventorySlotFactory);
        this.gatherer = gatherer;
    }

    @Override
    public TextComponent getName() {
        return Component.text("Choix des équipes");
    }

    @Override
    protected void onOpen() {
        super.onOpen();

        int i = 10;
        for (TeamType teamType : TeamType.values()) {
            setSlot(i++, inventorySlotFactory.createTeamInventorySlot(this, teamType));
        }

        if (player.isOp()) {
            setSlot(i, inventorySlotFactory.createCustomSlot(this, Material.CLOCK, "Administration",
                    () -> gatherer.openAdminMenu(player))
            );
        }
    }

}
