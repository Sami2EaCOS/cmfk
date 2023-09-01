package me.smourad.cmfk.inventory.slot.resource.team;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.game.team.TeamType;
import me.smourad.cmfk.game.team.TheGatherer;
import me.smourad.cmfk.inventory.CMFKInventory;
import me.smourad.cmfk.inventory.slot.resource.ActionSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class TeamInventorySlot extends ActionSlot {

    private final TheGatherer gatherer;

    private final TeamType teamType;

    @AssistedInject
    public TeamInventorySlot(
            @Assisted CMFKInventory inventory,
            @Assisted TeamType teamType,
            TheGatherer gatherer
    ) {
        super(inventory);

        this.gatherer = gatherer;
        this.teamType = teamType;
    }

    @Override
    protected Material getMaterial() {
        return teamType.getWool();
    }

    @Override
    protected String getName() {
        return "Équipe %s".formatted(teamType.getName());
    }

    @Override
    protected List<String> getDescription() {
        return gatherer.getPlayers(teamType).stream()
                .map(Player::getName)
                .toList();
    }

    @Override
    public boolean onAction(InventoryClickEvent event) {
        gatherer.selectTeam(getPlayer(), teamType);
        return true;
    }

}
