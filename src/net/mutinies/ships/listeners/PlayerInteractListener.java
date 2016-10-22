package net.mutinies.ships.listeners;

import net.mutinies.ships.MutiniesShips;
import net.mutinies.ships.items.ActionItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		ActionItem actionItem = MutiniesShips.getInstance().getActionItemManager().getActionItem(e.getItem());

		if (actionItem == null) return;
		e.setCancelled(true);

		switch (e.getAction()) {
			case LEFT_CLICK_BLOCK:
				actionItem.leftClick(e);
				break;
			case RIGHT_CLICK_BLOCK:
				actionItem.rightClick(e);
				break;
			case LEFT_CLICK_AIR:
				actionItem.leftClick(e);
				break;
			case RIGHT_CLICK_AIR:
				actionItem.rightClick(e);
				break;
		}
	}
}