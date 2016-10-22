package net.mutinies.ships.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.Map;

public class GUIManager {
	private static Map<InventoryView, GUI> inventoryGUIMap = new HashMap<>();

	public static Map<InventoryView, GUI> getInventoryGUIMap() {
		return inventoryGUIMap;
	}

	public static boolean processClick(InventoryClickEvent e) {
		if (inventoryGUIMap.containsKey(e.getView())) {
			inventoryGUIMap.get(e.getView()).process(e.getRawSlot(), e);
			return true;
		}
		return false;
	}

	public static void removeInventory(InventoryView view) {
		inventoryGUIMap.remove(view);
	}
}