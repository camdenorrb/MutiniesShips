# MutiniesShips
Ships plugin by Mutinies

**Messages**
  - setControlPanel
    - Right click the block that you want to make your ship control panel
  -controlPanelDestroyed
    - Your control panel was destroyed! Pleace right click new control panel
  - cantUseItem
    - You cannot use this item unless you are the captain or a copilot of the vessel
  - notOnShip
    - You must be on a ship to use this item

**V1 Goals:**
  - Detect a ship from a single block, this ship can be moved in water
  - GUI Menu
    - Accessible by clicking control panel or typing /ship
    - Select an item to bind it
      - Click the appropriate item, the next item in hotbar clicked is bound
      - Items:
        - Move
        - Rotate
        - Change speed (left click faster, right slower)
    - Clear all binded
    - Autopilot
      - 1 row chest GUI, 5 blocks spaced out, move speed 1-5
    - Leader management (captain +)
      - List leaders
      - Add copilot (anivil menu, input name to add)
      - Remove copilot (list all, remove on click)
    - Give ownership (only lists sailor+ on ship)
      - Confirmation GUI
      - Reset copilots if not captain+
    - Abandon ship
    - Change control panel block

  - /ship
    - if (!player.hasShip) tell: "Right click your desired ship Control Panel!"
    - else opengui controlPanel

**Mechanics**
  - Using control panel block as origin, adapt paint program fill bucket algorithm for 3D
    - Add a feature that stores 2 corners of a cuboid for collision checks
  -Rotate blocks and players in relation to control panel

**Dev Branch (experimental, if too laggy, drop):**
  - Represent ships with armor stands
    - Invisible
    - Blocks used for hat
    - Disable gravity
  - Move ship by setting velocity of each stand/player