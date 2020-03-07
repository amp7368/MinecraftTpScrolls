package apple.guiTypes;

import apple.finals.GUIFinals;
import apple.utils.SpaceInventory;
import org.bukkit.inventory.Inventory;

public class GUIAdminEdit extends GUI {
    public GUIAdminEdit(int size) {
        super(size, "GUI Private");
        Inventory inv = super.getInventory();
        for (SpaceInventory space : GUIFinals.sideGui) {
            int index = space.getIndex();
            inv.setItem(index, space.getItem());
            super.set(index, space.isEditable());
        }
    }

    public static Inventory makeGUIPrivate() {
        return (new GUIPrivate(54)).getInventory();
    }
}
