package apple.utils;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIActionsFinal {
    public static final String doNothing = "doNothing";
    public static final String HOME = "home";

    public static void dealWith(String localName, InventoryClickEvent event) {
        if (localName.equals(GUIActionsFinal.HOME)) {
            HumanEntity who = event.getWhoClicked();
            if ((who instanceof Player)) {
                Player player = (Player) who;
                player.closeInventory();
                GUIOpen.openMainGUI(player);
            }
        }
    }
}
