package net.mutinies.ships.ship;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class Ship {
	private static Set<Material> blacklisted = new HashSet<>();

	private Player owner;
	private Set<Player> copilots = new HashSet<>();
	private Set<Location> blocks = new HashSet<>();
	private Material[] structure;
	private Cuboid cuboid;

	private int speed;

	private Location offset;

	public Ship(Location controlPanel) {
		offset = controlPanel;
		cuboid = new Cuboid(offset.getWorld());
	}

	public void move(Vector dir) {
		dir.multiply(new Vector(1, 0, 1)).normalize().multiply(speed);
		offset.add(dir);

	}

	private void buildShip(Location source) {
		Queue<Location> toCheck = new LinkedList<>();
		Set<Location> checked = new HashSet<>();
		World reference = source.getWorld();

		Location offset = source.clone();

		toCheck.add(source);

		while (!toCheck.isEmpty()) {
			Location checkedBlock = toCheck.remove();

			if (!checked.contains(checkedBlock)) {
				checked.add(checkedBlock);
				if (!blacklisted.contains(checkedBlock.getBlock())) {
					Location relative = checkedBlock.clone().subtract(offset);
					blocks.add(relative);
					cuboid.expandToLocation(relative);

					toCheck.add(new Location(reference, checkedBlock.getX() + 1, checkedBlock.getY(), checkedBlock.getZ()));
					toCheck.add(new Location(reference, checkedBlock.getX() - 1, checkedBlock.getY(), checkedBlock.getZ()));
					toCheck.add(new Location(reference, checkedBlock.getX(), checkedBlock.getY() + 1, checkedBlock.getZ()));
					toCheck.add(new Location(reference, checkedBlock.getX(), checkedBlock.getY() - 1, checkedBlock.getZ()));
					toCheck.add(new Location(reference, checkedBlock.getX(), checkedBlock.getY(), checkedBlock.getZ() + 1));
					toCheck.add(new Location(reference, checkedBlock.getX(), checkedBlock.getY(), checkedBlock.getZ() - 1));
				}
			}
		}
	}

	public void addCopilot(Player player) {
		copilots.add(player);
	}

	public void removeCopilot(Player player) {
		if (copilots.contains(player))
			copilots.remove(player);
	}

	public void clearCopilots() {
		copilots.clear();
	}

	private List<Entity> getPassengers() {
		List<Entity> passengers = new LinkedList<>();
		for (Chunk c : cuboid.getChunks(offset))
			for (Entity e : c.getEntities())
				if (blocks.contains(e.getLocation().add(0, -1, 0).subtract(offset)))
					passengers.add(e);
		return passengers;
	}

	public boolean setOwner(Player player) {
		if (!player.hasPermission("mutinies.ships.use")) return false;
		if (!player.hasPermission("mutinies.ship.manageLeaders")) copilots.clear();
		owner = player;
		return true;
	}

	public Player getOwner() {
		return owner;
	}

	public Set<Player> getCopilots() {
		return copilots;
	}

	public Set<Location> getBlocks() {
		return blocks;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}
}