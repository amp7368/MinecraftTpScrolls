package apple;

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
