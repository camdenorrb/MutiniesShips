package net.mutinies.ships.listeners;

import net.mutinies.ships.gui.GUIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		e.setCancelled(GUIManager.processClick(e));
	}
}