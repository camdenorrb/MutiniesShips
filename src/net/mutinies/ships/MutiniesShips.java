package net.mutinies.ships;

import net.mutinies.ships.commands.CommandShip;
import net.mutinies.ships.gui.ControlPanelGUI;
import net.mutinies.ships.items.ActionItemManager;
import net.mutinies.ships.listeners.ListenerManager;
import net.mutinies.ships.player.PlayerData;
import net.mutinies.ships.ship.ShipManager;
import net.mutinies.ships.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public class MutiniesShips extends JavaPlugin {
	private static MutiniesShips instance;

	public static MutiniesShips getInstance() {
		return instance;
	}

	private ActionItemManager actionItemManager;

	public ActionItemManager getActionItemManager() {
		return actionItemManager;
	}

	private MySQL sql;
	private ShipManager shipManager;

	public Connection getConnection() {
		return sql.getConnection();
	}
	public ShipManager getShipManager() {
		return shipManager;
	}

	@Override
	public void onEnable() {
		instance = this;
		getCommand("ship").setExecutor(new CommandShip());
		ListenerManager.registerListeners();

		saveDefaultConfig();

		FileConfiguration config = getConfig();
		ConfigurationSection database = config.getConfigurationSection("database");
		String url = database.getString("URL");
		String user = database.getString("User");
		String pass = database.getString("Pass");
		sql = new MySQL(url, user, pass);

		PlayerData.loadTable();
		shipManager = new ShipManager(getConfig().getConfigurationSection("shipConfig"));

		Bukkit.getOnlinePlayers().forEach(p -> PlayerData.getPlayerDataMap().put(p, new PlayerData(p)));

		ControlPanelGUI.setItemDataSection(getConfig().getConfigurationSection("controlPanelItems"));

		actionItemManager = new ActionItemManager(getConfig().getConfigurationSection("items"));
	}

	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p -> PlayerData.getPlayerDataMap().get(p).savePlayer());
		sql.close();
	}
}