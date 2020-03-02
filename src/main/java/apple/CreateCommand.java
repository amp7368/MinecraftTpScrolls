package apple;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateCommand implements CommandExecutor {
    JavaPlugin plugin;

    public CreateCommand(ScrollMain plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("scrollCreate")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = Bukkit.getPlayer(commandSender.getName());
        if (player == null) {
            return true;
        }
        if (args.length == 0) {
            commandSender.sendMessage("Correct Usage: /scrollCreate <name>");
            return true;
        }
        String itemName = args[0];
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (mainHand.getAmount() == 0 || mainHand.getType().equals(Material.AIR)) {
            // create a scroll
            ItemStack scroll = new ItemStack(Material.PAPER, 64);
            ItemMeta meta = scroll.getItemMeta();
            assert meta != null;

            // get the player location
            Location loc = player.getLocation();
            List<String> coords = new ArrayList<String>(4);
            coords.add("x:" + loc.getBlockX());
            coords.add("y:" + loc.getBlockY());
            coords.add("z:" + loc.getBlockZ());
            World w = loc.getWorld();
            if (w == null) {
                commandSender.sendMessage("Something went wrong >.>");
                return true;
            }
            coords.add("world:" + w.getName());

            // set the meta data
            meta.setLore(coords);
            meta.setDisplayName(itemName);
            scroll.setItemMeta(meta);

            player.getInventory().setItemInMainHand(scroll);
        }
        return false;
    }
}
