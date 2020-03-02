package apple;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ClickListener implements Listener {
    JavaPlugin plugin;

    public ClickListener(ScrollMain plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void rightClickListener(PlayerInteractEvent event) {
        Action action = event.getAction();

        // if the action is a right click
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

            Bukkit.getServer().broadcastMessage("you right clicked!");
            if (Objects.requireNonNull(event.getItem()).getType() == Material.PAPER) {
                Bukkit.getServer().broadcastMessage("you right clicked paper!");
            }
            return;
        }
    }
}
