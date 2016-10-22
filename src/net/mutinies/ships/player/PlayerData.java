package net.mutinies.ships.player;

import net.mutinies.ships.MutiniesShips;
import net.mutinies.ships.ship.Ship;
import net.mutinies.ships.sql.MySQL;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerData {
	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXIST ShipsPlayerData (UUID CHAR(32), X INT, Y, INT, Z, INT, PRIMARY KEY (UUID))";
	public final static String SAVE_PLAYER = "INSERT INTO ShipsPlayerData VALUES(?,?)";
	public final static String LOAD_PLAYER = "SELECT X, Y, Z FROM ShipsPlayerData WHERE UUID=?";

	private static Map<Player, PlayerData> playerDataMap = new HashMap<>();

	public static Map<Player, PlayerData> getPlayerDataMap() {
		return playerDataMap;
	}

	private Player player;
	private Set<Ship> ownedShips = new HashSet<>();
	private Ship currentShip = null;

	public PlayerData(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public Set<Ship> getOwnedShips() {
		return ownedShips;
	}

	public Ship getCurrentShip() {
		return currentShip;
	}

	public void setCurrentShip(Ship currentShip) {
		this.currentShip = currentShip;
	}

	public void loadPlayer() {
		MySQL sql = MutiniesShips.getInstance().getSql();
		Connection connection = sql.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(LOAD_PLAYER);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int x = resultSet.getInt("X");
				int y = resultSet.getInt("Y");
				int z = resultSet.getInt("Z");

			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void savePlayer() {

	}
}