package me.smourad.cmfk.inventory.resource;

import me.smourad.cmfk.factory.InventorySlotFactory;
import me.smourad.cmfk.inventory.slot.CMFKInventorySlot;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class CMFKListMenu<T> extends CMFKMenu {

    private final boolean holderSlotsBackgroundRemoved;

    protected CMFKListMenu(
            Player player,
            boolean holderSlotsBackgroundRemoved,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        super(player, plugin, inventorySlotFactory);

        this.holderSlotsBackgroundRemoved = holderSlotsBackgroundRemoved;
    }

    protected CMFKListMenu(
            Player player,
            int height,
            boolean holderSlotsBackgroundRemoved,
            JavaPlugin plugin,
            InventorySlotFactory inventorySlotFactory
    ) {
        super(player, height, plugin, inventorySlotFactory);

        this.holderSlotsBackgroundRemoved = holderSlotsBackgroundRemoved;
    }

    protected abstract int[] getHolderSlots();
    protected abstract List<T> getDataList();
    protected abstract CMFKInventorySlot getInventorySlot(T object);

    @Override
    public void onOpen() {
        super.onOpen();
        List<T> dataList = getDataList();
        int dataListSize = dataList.size();

        int[] holderSlots = getHolderSlots();
        int holderSlotsLength = holderSlots.length;
        int holderSlotsIndex = holderSlotsLength*getPage();

        int listSizeByPage = dataListSize - holderSlotsIndex;

        int maxNumber = Math.max(Math.min(holderSlotsLength, listSizeByPage), 0);

        if (holderSlotsBackgroundRemoved) {
            for (int holderSlot : holderSlots) {
                removeSlot(holderSlot);
            }
        }

        for (int i = 0; i < maxNumber; i++) {
            setSlot(holderSlots[i], getInventorySlot(dataList.get(i+(holderSlotsIndex))));
        }
    }

    @Override
    public int getNumberOfPages() {
        return (int) Math.max(Math.ceil((double) getDataList().size() / getHolderSlots().length), 1);
    }

}
