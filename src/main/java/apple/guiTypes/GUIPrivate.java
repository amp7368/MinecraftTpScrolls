package apple.guiTypes;

import org.bukkit.inventory.Inventory;

public class GUIPrivate extends GUI {
    public GUIPrivate(int size) {
        super(size, "GUI Private");
    }

    public static Inventory makeGUIPrivate() {
        return (new GUIPrivate(54)).getInventory();
    }
}
