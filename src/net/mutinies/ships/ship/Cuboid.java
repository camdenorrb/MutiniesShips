package net.mutinies.ships.ship;

import org.bukkit.Location;
import org.bukkit.World;

public class Cuboid
{
	private Location minPos;
	private Location maxPos;
	
	public Cuboid(World world)
	{
		this(new Location(world, 0, 0, 0), new Location(world, 0, 0, 0));
	}
	
	public Cuboid(Location pos1, Location pos2)
	{
		if (!pos1.getWorld().equals(pos2.getWorld()))
			throw new IllegalArgumentException("Must be in the proper world");
		
		double minX = Math.min(pos1.getX(), pos2.getX());
		double minY = Math.min(pos1.getY(), pos2.getY());
		double minZ = Math.min(pos1.getZ(), pos2.getZ());
		
		double maxX = Math.max(pos1.getX(), pos2.getX());
		double maxY = Math.max(pos1.getY(), pos2.getY());
		double maxZ = Math.max(pos1.getZ(), pos2.getZ());
	}
	
	public boolean inside(Location location)
	{
		return location.getX() <= maxPos.getX() &&
				location.getX() >= minPos.getX() &&
				location.getY() <= maxPos.getY() &&
				location.getY() >= minPos.getY() &&
				location.getZ() <= maxPos.getZ() &&
				location.getZ() >= minPos.getZ();
	}
	
	public void expandToLocation(Location location)
	{
		if (!location.getWorld().equals(minPos.getWorld()))
			throw new IllegalArgumentException("Must be in the proper world");
		if (location.getBlockX() > maxPos.getBlockX())
			maxPos.setX(location.getX());
		if (location.getBlockY() > maxPos.getBlockY())
			maxPos.setY(location.getY());
		if (location.getBlockZ() > maxPos.getBlockZ())
			maxPos.setZ(location.getZ());
		if (location.getBlockX() < minPos.getBlockX())
			minPos.setX(location.getX());
		if (location.getBlockY() < minPos.getBlockY())
			minPos.setY(location.getY());
		if (location.getBlockZ() < minPos.getBlockZ())
			minPos.setZ(location.getZ());
	}
}