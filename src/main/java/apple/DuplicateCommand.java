package apple;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DuplicateCommand implements CommandExecutor {
    JavaPlugin plugin;

    public DuplicateCommand(ScrollMain plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("scrollDuplicate")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
