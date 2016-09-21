package net.mutinies.ships.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GUIManager
{
	private static Map<Inventory, GUI> inventoryGUIMap = new HashMap<>();

	public static Map<Inventory, GUI> getInventoryGUIMap()
	{
		return inventoryGUIMap;
	}

	public static boolean processClick(InventoryClickEvent e)
	{
		if (inventoryGUIMap.containsKey(e.getClickedInventory()))
		{
			inventoryGUIMap.get(e.getClickedInventory()).process(e.getSlot(), e);
			return true;
		}
		return false;
	}

	public static void removeInventory(Inventory inventory)
	{
		inventoryGUIMap.remove(inventory);
	}
}