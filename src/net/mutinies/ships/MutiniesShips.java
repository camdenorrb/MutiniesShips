package net.mutinies.ships;

import net.mutinies.ships.commands.CommandShip;
import net.mutinies.ships.items.ActionItemManager;
import net.mutinies.ships.listeners.ListenerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MutiniesShips extends JavaPlugin
{
	private static MutiniesShips instance;

	public static MutiniesShips getInstance()
	{
		return instance;
	}

	private ActionItemManager actionItemManager;

	@Override
	public void onEnable()
	{
		instance = this;
		getCommand("ship").setExecutor(new CommandShip());
		ListenerManager.registerListeners();

		saveDefaultConfig();

		actionItemManager = new ActionItemManager(getConfig().getConfigurationSection("items"));
	}
}