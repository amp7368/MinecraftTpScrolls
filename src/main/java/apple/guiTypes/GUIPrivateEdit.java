package apple.guiTypes;

import org.bukkit.inventory.Inventory;

public class GUIPrivateEdit extends GUI {
    public GUIPrivateEdit(int size) {
        super(size, "Edit GUI Private");
        for (int i = 0; i < size; i++) {
            super.set(i, true);
        }
    }

    public static Inventory makeGUIPrivateEdit() {
        return (new GUIPrivateEdit(54)).getInventory();
    }
}
