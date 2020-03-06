package apple.commands;

import apple.ScrollInventories;
import apple.ScrollMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrollEditCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public ScrollEditCommand(ScrollMain plugin) {
        this.plugin = plugin;
        Bukkit.getPluginCommand("scroll_edit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null)
            return true;
        player.openInventory(ScrollInventories.scrollInvAllEdit);
        return false;
    }
}
