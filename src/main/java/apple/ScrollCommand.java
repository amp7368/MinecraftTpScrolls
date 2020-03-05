package apple;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ScrollCommand implements CommandExecutor {
    JavaPlugin plugin;
    Inventory scrollInv;

    public ScrollCommand(ScrollMain plugin) {
        this.plugin = plugin;
        scrollInv = Bukkit.createInventory(new InventoryChest(plugin, 54, "Scrolls"), InventoryType.CHEST);
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta im = item.getItemMeta();
        assert im != null;
        List<String> lore = new ArrayList<String>(4);
        lore.add("x:0");
        lore.add("y:65");
        lore.add("z:0");
        lore.add("world:world");

        im.setLore(lore);
        item.setItemMeta(im);
        scrollInv.addItem(item);
        PluginCommand command = plugin.getCommand("scroll");
        if (command == null) {
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // get the player
        Player player = Bukkit.getServer().getPlayer(commandSender.getName());

        // return if player is non existent
        if (player == null)
            return false;

        // open the scroll inventory
        player.openInventory(scrollInv);

        return false;
    }
}
