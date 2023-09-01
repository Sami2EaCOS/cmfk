package me.smourad.cmfk.inventory.resource.team;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import me.smourad.cmfk.factory.InventorySlotFactory;
import me.smourad.cmfk.game.TheDirector;
import me.smourad.cmfk.inventory.resource.CMFKCarvedMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GathererAdminMenu extends CMFKCarvedMenu {

    private final TheDirector director;

    @AssistedInject
    public GathererAdminMenu(
            @Assisted Player player,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory,
            TheDirector director
    ) {
        super(player, plugin, inventorySlotFactory);
        this.director = director;
    }

    @Override
    public TextComponent getName() {
        return Component.text("Administration");
    }

    @Override
    protected void onOpen() {
        super.onOpen();

        setSlot(10, inventorySlotFactory.createCustomSlot(this, Material.DIAMOND, "Lancer la partie",
                () -> {
                    director.launch();
                    close();
                }
        ));
    }

}
