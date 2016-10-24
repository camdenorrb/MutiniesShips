package net.mutinies.ships.listeners;

import net.mutinies.ships.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		PlayerData.getPlayerDataMap().put(e.getPlayer(), new PlayerData(e.getPlayer()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PlayerData.getPlayerDataMap().get(e.getPlayer()).savePlayer();
		PlayerData.getPlayerDataMap().remove(e.getPlayer());
	}
}
