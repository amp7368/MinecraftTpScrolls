package apple.guiTypes;

import org.bukkit.inventory.Inventory;

public class GUIQuick extends GUI{
    public GUIQuick() {
        super(18, "QuickTp Scrolls");

    }

    public static Inventory makeGUIQuick() {
        GUI gui = new GUIQuick();
        return gui.getInventory();
    }
}
