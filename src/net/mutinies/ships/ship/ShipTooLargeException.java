package net.mutinies.ships.ship;

public class ShipTooLargeException extends Exception {
	public ShipTooLargeException() {
		super("Ship being built exceeded size limit");
	}
}
