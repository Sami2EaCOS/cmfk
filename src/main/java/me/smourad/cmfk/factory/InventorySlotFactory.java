package me.smourad.cmfk.factory;

import me.smourad.cmfk.inventory.CMFKInventory;
import me.smourad.cmfk.inventory.resource.team.TeamInventory;
import me.smourad.cmfk.inventory.slot.CMFKInventorySlot;
import me.smourad.cmfk.inventory.slot.resource.BackgroundSlot;
import me.smourad.cmfk.inventory.slot.resource.ChangePageSlot;
import me.smourad.cmfk.inventory.slot.resource.CloseSlot;
import me.smourad.cmfk.inventory.slot.resource.team.TeamInventorySlot;
import me.smourad.cmfk.team.TeamType;
import org.bukkit.Material;

public interface InventorySlotFactory {

    BackgroundSlot createBackgroundSlot(CMFKInventory inventory, Material icon);
    ChangePageSlot createChangePageSlot(CMFKInventory inventory, int page);
    CloseSlot createCloseSlot(CMFKInventory inventory);
    TeamInventorySlot createTeamInventorySlot(CMFKInventory inventory, TeamType teamType);

}
