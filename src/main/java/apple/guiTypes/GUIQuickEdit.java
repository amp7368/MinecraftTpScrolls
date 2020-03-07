package apple.guiTypes;

import org.bukkit.inventory.Inventory;

public class GUIQuickEdit extends GUI {
    public GUIQuickEdit(int size) {
        super(size, "Edit QuickTp Scrolls");
        Inventory inv = super.getInventory();
        for (int i = 0; i < size; i++) {
            super.set(i, true);
        }
    }

    public static Inventory makeGUIQuickEdit() {
        GUI gui = new GUIQuickEdit(18).addHomeGUI();
        return gui.getInventory();
    }

}
