package me.smourad.cmfk.factory;

import me.smourad.cmfk.inventory.resource.team.GathererAdminMenu;
import me.smourad.cmfk.inventory.resource.team.TeamInventory;
import org.bukkit.entity.Player;

public interface InventoryFactory {

    TeamInventory createTeamInventory(Player player);
    GathererAdminMenu createGathererAdminMenu(Player player);

}
