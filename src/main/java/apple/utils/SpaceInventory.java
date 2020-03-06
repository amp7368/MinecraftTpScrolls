package apple.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SpaceInventory {
    ItemStack item;
    boolean editable;
    int index;

    public SpaceInventory(int index, boolean isEditable, ItemStack item) {
        this.index = index;
        editable = isEditable;
        this.item = item;
    }

    public boolean isEditable() {
        return editable;
    }

    public ItemStack getItem() {
        return new ItemStack(item);
    }

    public int getIndex() {
        return index;
    }
}
