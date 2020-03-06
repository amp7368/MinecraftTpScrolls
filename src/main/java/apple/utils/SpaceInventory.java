package apple.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SpaceInventory {
    ItemStack item;
    boolean editable;
    int index;

    public SpaceInventory(int index, boolean isEditable, List<String> lore, String displayName, Material type) {
        this.index = index;
        editable = isEditable;
        item = new ItemStack(type);
        ItemMeta im = item.getItemMeta();
        if (im != null) {
            im.setDisplayName(displayName);
            im.setLore(lore);
            item.setItemMeta(im);
        }
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
