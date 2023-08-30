package me.smourad.cmfk.inventory;

import lombok.Getter;
import me.smourad.cmfk.factory.InventorySlotFactory;
import me.smourad.cmfk.inventory.slot.CMFKInventorySlot;
import me.smourad.cmfk.inventory.slot.resource.ChangePageSlot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public abstract class CMFKInventory implements Listener {

    protected final JavaPlugin plugin;
    private final InventorySlotFactory inventorySlotFactory;

    protected final int height;
    @Getter
    protected final Player player;

    @Getter
    protected Inventory inventory;
    protected CMFKInventorySlot[] slots;
    @Getter
    protected int page;
    @Getter
    protected CMFKInventory previous;
    protected BukkitTask updateTask;
    protected boolean isPrevious = false;

    protected CMFKInventory(
            Player player,
            int height,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        this.player = player;
        this.height = height;
        this.plugin = plugin;
        this.inventorySlotFactory = inventorySlotFactory;
        this.slots = new CMFKInventorySlot[getSize()];
    }

    protected void update() {
        onOpen();
        updateInventory();
    }

    protected void onOpen() {
        loadBackground();
        loadPagesButton();
    }

    protected void onClose() {
        HandlerList.unregisterAll(this);
        if (updateTask != null) updateTask.cancel();
    }

    public void open(CMFKInventory previous) {
        this.previous = previous;
        this.previous.isPrevious = true;
        open();
    }

    public void open(int page) {
        this.page = page;
        open();
    }

    public void open() {
        loadInventory();
        onOpen();
        updateInventory();

        loadLoop();

        HandlerList.unregisterAll(this);
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);

        player.openInventory(inventory);
    }

    public void close() {
        player.closeInventory();
        onHardClose();
    }

    protected void onHardClose() {
        onClose();

        if (Objects.nonNull(previous)) {
            previous.onHardClose();
        }
    }

    public void back() {
        if (Objects.nonNull(previous)) {
            previous.open();
            previous.isPrevious = false;
            previous = null;
            page = 0;
        } else {
            close();
        }
    }

    public int getNumberOfPages() {
        return 1;
    }

    private void loadLoop() {
        this.updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    protected void loadBackground() {
        for (int j = 0; j < getSize(); j++) {
            slots[j] = inventorySlotFactory.createBackgroundSlot(this, getBackgroundMaterial());
        }
    }

    protected void loadPagesButton() {
        int previousSlot = getSize() - 9;
        int nextSlot = getSize() - 1;

        setSlot(getSize() - 9 + 4, inventorySlotFactory.createCloseSlot(this));

        if (page != 0) {
            setSlot(previousSlot, getPageButton(page - 1));
        } else if (getSlot(previousSlot) instanceof ChangePageSlot) {
            setSlot(previousSlot, inventorySlotFactory.createBackgroundSlot(this, getBackgroundMaterial()));
        }

        if (page != getNumberOfPages() - 1) {
            setSlot(nextSlot, getPageButton(page + 1));
        } else if (getSlot(nextSlot) instanceof ChangePageSlot) {
            setSlot(nextSlot, inventorySlotFactory.createBackgroundSlot(this, getBackgroundMaterial()));
        }
    }

    protected ChangePageSlot getPageButton(int page) {
        return inventorySlotFactory.createChangePageSlot(this, page);
    }

    public void setSlot(int slot, CMFKInventorySlot inventorySlot) {
        slots[slot] = inventorySlot;
    }

    public void removeSlot(int slot) {
        slots[slot] = null;
    }

    public CMFKInventorySlot getSlot(int slot) {
        return slots[slot];
    }

    public void loadInventory() {
        this.inventory = Bukkit.createInventory(player, getSize(), getInventoryName(page));
    }

    public void updateInventory() {
        for (int j = 0; j < getSize(); j++) {
            if (slots[j] != null) {
                inventory.setItem(j, slots[j].getItemStack());
            } else {
                inventory.setItem(j, new ItemStack(Material.AIR));
            }
        }
    }

    protected int getSize() {
        return height * 9;
    }

    public TextComponent getInventoryName(int page) {
        if (getNumberOfPages() > 1)
            return getName().append(Component.text(" (" + (page + 1) + "/" + getNumberOfPages() + ")"));
        else return getName();
    }

    public abstract TextComponent getName();

    public Material getBackgroundMaterial() {
        return Material.LIGHT_BLUE_STAINED_GLASS_PANE;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            if (isPrevious || Objects.equals(InventoryCloseEvent.Reason.OPEN_NEW, event.getReason())) {
                onClose();
            } else {
                onHardClose();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().equals(inventory) && event.getRawSlot() == event.getSlot()) {
            if (page < 0) return;
            int slot = event.getSlot();

            if (slot > -1 && slots[slot] != null) {
                slots[slot].onClick(event);
            }
        }
    }

}
