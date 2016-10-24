package net.mutinies.ships.ship;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ShipManager {
	private SortedSet<Ship> ships = new TreeSet<>();
	private int oceanLevel;

	public ShipManager(ConfigurationSection section) {
		loadShips(section);
	}

	public SortedSet<Ship> getShips() {
		return ships;
	}

	public void loadShips(ConfigurationSection section) {
		int oceanHeight = section.getInt("oceanHeight");
		List<String> shipStrings = section.getStringList("ships");

	}

	public List<String> saveShips() {
		List<String> shipStrings = new ArrayList<>();
		for (Ship ship : ships) {

		}
		return shipStrings;
	}

	public void getCurrentShip(Player player) {
		
	}

	public Ship getShip(Player player) {
		for (Ship ship : ships) {
			if (ship.getCuboid().inside(player.getLocation()))
				return ship;
		}
		return null;
	}

	public int getOceanLevel() {
		return oceanLevel;
	}
}