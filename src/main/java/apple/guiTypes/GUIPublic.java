package apple.guiTypes;

import org.bukkit.inventory.Inventory;

public class GUIPublic extends GUI {
    public GUIPublic() {
        super(54, "Public Scrolls");

    }

    public static Inventory makeGUIPublic() {
        GUI gui = new GUIPublic();
        return gui.getInventory();
    }
}
