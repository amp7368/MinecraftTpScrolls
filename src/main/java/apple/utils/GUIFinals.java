package apple.utils;

import apple.guiTypes.Space;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GUIFinals {
    public static Set<SpaceInventory> sideGui = new HashSet<SpaceInventory>();

    public static void initialize() {
        List<String> loreGray = new ArrayList<String>();
        List<String> loreHome = new ArrayList<String>();
        String displayNameGray = "";
        String displayNameHome = "Home";
        Material materialGray = Material.GRAY_STAINED_GLASS_PANE;
        Material materialHome = Material.GREEN_TERRACOTTA;
        sideGui.add(new SpaceInventory(7, false, loreGray, displayNameGray, materialGray));
        sideGui.add(new SpaceInventory(16, false, loreGray, displayNameGray, materialGray));
        sideGui.add(new SpaceInventory(17, false, loreGray, displayNameGray, materialGray));
        sideGui.add(new SpaceInventory(8, false, loreHome, displayNameHome, materialHome));
    }
}
