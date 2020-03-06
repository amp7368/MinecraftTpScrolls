package apple.guiTypes;

import apple.utils.GUIFinals;
import apple.utils.SpaceInventory;
import org.bukkit.inventory.Inventory;

public class GUIPublicEdit extends GUI {
    public GUIPublicEdit(int size) {
        super(size, "Edit Public Scrolls");
        Inventory inv = super.getInventory();
        for (int i = 0; i < size; i++) {
            super.set(i, true);
        }
    }

    public static Inventory makeGUIPublicEdit() {
        GUI gui = new GUIPublicEdit(54).addHomeGUI();
        return gui.getInventory();
    }
}
