package apple;

import apple.commands.*;
import apple.listeners.ClickListener;
import apple.listeners.InventoryExitListener;
import apple.listeners.InventoryInteractListener;
import apple.finals.GUIActionsFinal;
import apple.finals.GUIFinals;
import apple.utils.GUIOpen;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollMain extends JavaPlugin {
    @Override
    public void onEnable() {
        new EditExit(this);
        new GUIActionsFinal();
        new GUIFinals();
        GUIFinals.initialize();

        new GUIOpen(this);

        new ScrollInventories(this);

        new CreateCommand(this);
        new DuplicateCommand(this);
        new ScrollHelpCommand(this);
        new ScrollCommand(this);

        new InventoryInteractListener(this);
        new ClickListener(this);
        new InventoryExitListener(this);
    }
}
