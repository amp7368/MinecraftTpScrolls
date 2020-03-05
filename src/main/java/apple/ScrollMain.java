package apple;

import apple.commands.CreateCommand;
import apple.commands.DuplicateCommand;
import apple.commands.ScrollAddAllCommand;
import apple.commands.ScrollCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollMain extends JavaPlugin {
    @Override
    public void onEnable() {
        new CreateCommand(this);
        new DuplicateCommand(this);
        new ClickListener(this);
        ScrollCommand scrollCommand = new ScrollCommand(this);
        new ScrollAddAllCommand(this, scrollCommand);
    }
}
