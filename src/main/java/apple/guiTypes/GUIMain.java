package apple.guiTypes;

import org.bukkit.inventory.Inventory;

public class GUIMain extends GUI {
    public GUIMain(int size) {
        super(size, "Main Menu");
    }

    public static Inventory makeGUIMain() {
        return (new GUIMain(9)).getInventory();
    }
}
