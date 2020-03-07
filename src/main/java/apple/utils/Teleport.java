package apple.utils;

import apple.finals.MessageFinals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class Teleport {
    private final static int DISTANCE_MARGIN = 15;

    public static void teleportScroll(List<String> lore, Player player) {
        String worldString = lore.get(3).substring(6);
        World world;
        world = Bukkit.getWorld(worldString);
        if (world == null) {
            // the world doesn't exist
            player.sendMessage(MessageFinals.IMPROPER_FORMAT_MESSAGE);
        }
        String xString = lore.get(0).substring(2);
        String yString = lore.get(1).substring(2);
        String zString = lore.get(2).substring(2);
        int x, y, z;
        try {
            x = Integer.parseInt(xString);
            y = Integer.parseInt(yString);
            z = Integer.parseInt(zString);
        } catch (NumberFormatException e) {
            // coords aren't numbers
            player.sendMessage(MessageFinals.IMPROPER_FORMAT_MESSAGE);
            return;
        }
        Location playerLoc = player.getLocation();
        // if the player is already in the location
        if (x - DISTANCE_MARGIN < playerLoc.getBlockX() && playerLoc.getBlockX() < x + DISTANCE_MARGIN &&
                y - DISTANCE_MARGIN < playerLoc.getBlockY() && playerLoc.getBlockY() < y + DISTANCE_MARGIN &&
                z - DISTANCE_MARGIN < playerLoc.getBlockZ() && playerLoc.getBlockZ() < z + DISTANCE_MARGIN) {
            player.sendMessage(MessageFinals.TP_ALREADY_HERE);
            return;
        }

        // teleport the player to x y z in world
        player.teleport(new Location(world, x, y, z), PlayerTeleportEvent.TeleportCause.COMMAND);

    }
}
