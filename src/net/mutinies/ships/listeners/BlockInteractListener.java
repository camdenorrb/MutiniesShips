package net.mutinies.ships.listeners;

import net.mutinies.ships.MutiniesShips;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockInteractListener implements Listener {
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		e.setCancelled(MutiniesShips.getInstance().getActionItemManager().getActionItem(e.getItemInHand()) != null);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		e.setCancelled(MutiniesShips.getInstance().getActionItemManager().getActionItem(
				e.getPlayer().getInventory().getItemInMainHand()) != null);
	}
}
