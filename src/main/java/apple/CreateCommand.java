package apple;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CreateCommand implements CommandExecutor {
    JavaPlugin plugin;

    public CreateCommand(ScrollMain plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("scrollCreate")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = Bukkit.getServer().getPlayer(commandSender.getName());
        if (player == null) {
            return true;
        }
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand.getData() == null) {
            Bukkit.getServer().broadcastMessage("main hand is empty");
        } else {
            Bukkit.getServer().broadcastMessage("main hand is " + mainHand.getData().toString());
        }


        return false;
    }
}
