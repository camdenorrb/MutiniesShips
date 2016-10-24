package net.mutinies.ships.ship;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ShipManager {
	private static SortedSet<Ship> ships = new TreeSet<>();

	public static SortedSet<Ship> getShips() {
		return ships;
	}

	public static void loadShips(ConfigurationSection section) {
		List<String> shipStrings = section.getStringList("ships");
		for (String string : shipStrings) {
			ByteBuffer buffer = ByteBuffer.wrap(string.getBytes());
			int numVals = buffer.getInt();
			float offX = buffer.getFloat();
			float offY = buffer.getFloat();
			float offZ = buffer.getFloat();
			float minX = buffer.getFloat();
			float minY = buffer.getFloat();
			float minZ = buffer.getFloat();
			float maxX = buffer.getFloat();
			float maxY = buffer.getFloat();
			float maxZ = buffer.getFloat();

		}
	}

	public static List<String> saveShips() {
		List<String> shipStrings = new ArrayList<>();
		for (Ship ship : ships) {

		}
		return shipStrings;
	}

	public static void getCurrentShip(Player player) {
		
	}
}
