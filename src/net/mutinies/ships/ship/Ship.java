package net.mutinies.ships.ship;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Ship
{
	private Player owner;
	private Set<Player> copilots = new HashSet<>();
	private Set<Location> blocks = new HashSet<>();
	private Cuboid cuboid;

	private Location offset;

	public Ship(Location controlPanel)
	{
		offset = controlPanel;
		cuboid = new Cuboid(offset.getWorld());
	}

	public void addCopilot(Player player)
	{
		copilots.add(player);
	}

	public void removeCopilot(Player player)
	{
		if (copilots.contains(player))
			copilots.remove(player);
	}

	public void clearCopilots()
	{
		copilots.clear();
	}

	public boolean setOwner(Player player)
	{
		if (!player.hasPermission("mutinies.ships.use"))
			return false;
		if (!player.hasPermission("mutinies.ship.manageLeaders"))
			copilots.clear();
		owner = player;
		return true;
	}

	public Player getOwner()
	{
		return owner;
	}
	public Set<Player> getCopilots()
	{
		return copilots;
	}
	public Set<Location> getBlocks()
	{
		return blocks;
	}
	public Cuboid getCuboid()
	{
		return cuboid;
	}
}