package net.mutinies.ships.listeners;

import net.mutinies.ships.MutiniesShips;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerManager
{
	private static Listener[] listeners =
			{
					new PlayerInteractListener(),
					new BlockInteractListener(),
					new InventoryClickListener(),
					new PlayerInteractListener(),
			};

	public static void registerListeners()
	{
		for (Listener listener : listeners)
			Bukkit.getPluginManager().registerEvents(listener, MutiniesShips.getInstance());
	}
}
