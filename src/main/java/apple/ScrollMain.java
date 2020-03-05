package apple;

import apple.commands.CreateCommand;
import apple.commands.DuplicateCommand;
import apple.commands.ScrollCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollMain extends JavaPlugin {
    @Override
    public void onEnable() {
        new CreateCommand(this);
        new DuplicateCommand(this);
        new ClickListener(this);
        new ScrollCommand(this);
    }
}
