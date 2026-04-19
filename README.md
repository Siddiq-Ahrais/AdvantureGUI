# AdvantureGUI

AdvantureGUI is a Java Swing GUI adventure game based on a text-adventure style flow.
You explore areas, collect items, fight monsters, manage HP, and use inventory strategically.

## About The Game

This project is a human-made adventure game with a GUI interface.
The game includes:

- Town / guard interaction flow
- Crossroad exploration (north, south, east, west)
- Monster combat system (attack, defend, run)
- Weapon drops with rarity chances
- Grass (healing item) collection and consumption by rarity
- Inventory UI with selection arrows
- Gear button for settings and bag button for quick inventory access

## Core Mechanics

### 1. Main Objective

Talk to the guard, complete requirements, get the Silver Ring, and unlock the ending.

### 2. Combat

- Choose `Fight` in the East area.
- Actions in battle:
	- `Attack`
	- `Defend`
	- `Run`
- Monster HP resets when a new fight starts after monster death.

### 3. Weapon System

Weapons affect player damage range.

Possible weapon drops from random item search:

- Void Nature Glass Sword (2%)
- Dynian Sword (28%)
- Steel Sword (20%)
- Old Sword (50%)

### 4. Grass / Healing System

Grass can be collected in South and consumed in inventory.

Healing values:

- Absolute Nature Glass: +100% current HP
- Super Rare Nature Glass: +30 HP
- Rare Nature Glass: +15 HP
- Nature Glass: +5 HP

Collection chances:

- Absolute Nature Glass: 2%
- Super Rare Nature Glass: 28%
- Rare Nature Glass: 20%
- Nature Glass: 50%

### 5. Inventory

- Open inventory via bag icon button.
- Navigate grass selection with `<` and `>` in inventory.
- Eat selected grass with `Eat Grass`.
- Empty/invalid choice buttons are hidden via opacity rules.

### 6. UI Controls

- Gear icon (top-left): open settings
- Bag icon (right of choice panel): open inventory shortcut
- Choice buttons: context-sensitive story and action options

## How To Run

You can run this game from source with Java.

### Option A: Compile directly with `javac`

From `Advanture/src`:

```bash
javac module-info.java pack/Game.java pack/playersetup.java
java pack.Game
```

### Option B: Build with Ant (if installed)

From `Advanture`:

```bash
ant -f build.xml
```

## Project Structure

```text
README.md
Advanture/
	build.xml
	src/
		module-info.java
		pack/
			Game.java
			choice.java
			playersetup.java
			Respon.java
			package-info.java
```

## Notes

- This project uses Java Swing for GUI rendering.
- Game balance and drop rates can be tuned further in `Game.java`.
- Some class names and text intentionally preserve original project naming style.
