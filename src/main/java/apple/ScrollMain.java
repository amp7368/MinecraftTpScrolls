package apple;

import apple.commands.*;
import apple.listeners.ClickListener;
import apple.listeners.InventoryExitListener;
import apple.listeners.InventoryInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollMain extends JavaPlugin {
    @Override
    public void onEnable() {
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
