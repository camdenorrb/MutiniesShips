package net.mutinies.ships.items;

import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class MoveItem extends ActionItem {
	public MoveItem(List<String> lore) {
		super(lore);
	}

	@Override
	public void leftClick(PlayerInteractEvent e) {
		e.getPlayer().sendMessage("Move");
		e.getPlayer().sendMessage("Left");
	}

	@Override
	public void rightClick(PlayerInteractEvent e) {
		e.getPlayer().sendMessage("Move");
		e.getPlayer().sendMessage("Right");
	}

	private void moveShip(PlayerInteractEvent e) {

	}
}