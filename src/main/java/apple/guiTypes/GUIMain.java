package apple.guiTypes;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIMain extends GUI {
    public GUIMain(int size) {
        super(size, "Main Menu");
    }

    public static Inventory makeGUIMain(boolean isAdmin) {
        ItemMeta im;
        Inventory gui = (new GUIMain(9)).getInventory();
        ItemStack privateItem = new ItemStack(Material.RED_TERRACOTTA);
        im = privateItem.getItemMeta();
        if (im != null) {
            im.setDisplayName("Private Scrolls");
            privateItem.setItemMeta(im);
        }
        ItemStack privateItemEdit = new ItemStack(Material.RED_GLAZED_TERRACOTTA);
        im = privateItemEdit.getItemMeta();
        if (im != null) {
            im.setDisplayName("Editing Private Scrolls");
            privateItemEdit.setItemMeta(im);
        }
        ItemStack publicItem = new ItemStack(Material.GREEN_TERRACOTTA);
        im = publicItem.getItemMeta();
        if (im != null) {
            im.setDisplayName("Public Scrolls");
            publicItem.setItemMeta(im);
        }
        if (isAdmin) {
            ItemStack publicItemEdit = new ItemStack(Material.GREEN_GLAZED_TERRACOTTA);
            im = publicItemEdit.getItemMeta();
            if (im != null) {
                im.setDisplayName("Editing Public Scrolls (CAUTION!!!)");
                publicItemEdit.setItemMeta(im);
            }
            gui.setItem(1, publicItemEdit);
        }

        gui.setItem(8, privateItemEdit);
        gui.setItem(4, privateItem);
        gui.setItem(0, publicItem);
        return gui;
    }
}
