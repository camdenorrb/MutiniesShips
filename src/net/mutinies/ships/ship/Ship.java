package net.mutinies.ships.ship;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Ship
{
	private static Set<Material> blacklisted = new HashSet<>();

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

	private void buildShip(Location source)
	{
		Queue<Location> toCheck = new LinkedList<>();
		Set<Location> checked = new HashSet<>();
		World reference = source.getWorld();

		Location offset = source.clone();

		toCheck.add(source);

		while (!toCheck.isEmpty())
		{
			Location checkedBlock = toCheck.remove();

			if (!checked.contains(checkedBlock))
			{
				checked.add(checkedBlock);
				if (!blacklisted.contains(checkedBlock.getBlock()))
				{
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