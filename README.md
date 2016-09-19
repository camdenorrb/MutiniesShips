# MutiniesShips
Ships plugin by Mutinies

**V1 Goals:**
  - Detect a ship from a single block, this ship can be moved in water
  - Take control of ship by typing /makeship
    - "Right click the block that you want to make your control panel"
  - "Your control panel was destroyed! Pleace right click new control panel"
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

**Dev Branch (experimental, if too laggy, drop):**
  - Represent ships with armor stands
    - Invisible
    - Blocks used for hat
    - Disable gravity
  - Move ship by setting velocity of each stand/player
