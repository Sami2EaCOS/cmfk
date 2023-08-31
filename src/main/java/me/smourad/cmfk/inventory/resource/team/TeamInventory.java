package me.smourad.cmfk.inventory.resource.team;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.factory.InventorySlotFactory;
import me.smourad.cmfk.inventory.CMFKInventory;
import me.smourad.cmfk.inventory.resource.CMFKLargeListMenu;
import me.smourad.cmfk.inventory.slot.CMFKInventorySlot;
import me.smourad.cmfk.team.TeamType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TeamInventory extends CMFKLargeListMenu<TeamType> {

    @AssistedInject
    public TeamInventory(
            @Assisted Player player,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        super(player, Boolean.TRUE, plugin, inventorySlotFactory);
    }

    @Override
    public TextComponent getName() {
        return Component.text("Choix des équipes");
    }


    @Override
    protected List<TeamType> getDataList() {
        return List.of(TeamType.values());
    }

    @Override
    protected CMFKInventorySlot getInventorySlot(TeamType teamType) {
        return inventorySlotFactory.createTeamInventorySlot(this, teamType);
    }

}
