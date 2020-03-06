package apple.guiTypes;

import apple.utils.GUIFinals;
import org.bukkit.inventory.Inventory;

public class GUIPublicEdit extends GUI {
    public GUIPublicEdit(int size) {
        super(size, "Edit Public Scrolls");
        for (int i = 0; i < super.getSize(); i++) {
            if (!GUIFinals.sideGui.contains(i)) {
                super.set(i, true);
            }
        }
    }

    public static Inventory makeGUIPublicEdit() {
        return (new GUIPublicEdit(54)).getInventory();
    }
}
