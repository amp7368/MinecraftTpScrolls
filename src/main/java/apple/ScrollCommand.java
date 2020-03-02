package apple;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollCommand implements CommandExecutor {
    JavaPlugin plugin;

    public ScrollCommand(ScrollMain plugin) {
        this.plugin = plugin;
        PluginCommand command = plugin.getCommand("scroll");
        if (command == null) {
            System.out.println("ScrollsTp could not get the scrollDuplicate command");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("'/scroll_duplicate' will give you a full stack of whatever scroll you have in your hand.");
        commandSender.sendMessage("'/scroll_create <name>' creates a scroll to your current location with a name of 'name'.");
        return false;
    }
}
