package net.mutinies.ships.listeners;

import net.mutinies.ships.gui.GUIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener
{
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e)
	{
		GUIManager.removeInventory(e.getInventory());
	}
}
