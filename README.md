# AdvantureGUI

AdvantureGUI is a JavaFX GUI adventure game with a classic text-adventure flow.
You explore areas, collect items, fight monsters, manage HP, and use inventory strategically.

## About The Game

This project is a human-made adventure game with a GUI interface.
The current version includes:

- Town and guard interaction flow
- Crossroad exploration (north, south, east, west)
- Monster combat system (attack, defend, run)
- Multiple monster types with a monster HUD
- Weapon drops with rarity chances and durability
- Grass (healing item) collection and consumption by rarity
- Inventory UI with selection arrows and item counts
- Settings menu with fullscreen toggle
- Quick restart after death or ending
- Synthesized sound effects (no external audio files)

## Core Mechanics

### 1. Main Objective

Talk to the guard, complete requirements, get the Silver Ring, and unlock the ending.

### 2. Combat

- Choose `Fight` in the East area.
- Actions in battle:
		- `Attack`
		- `Defend`
		- `Run`
- Monsters rotate by area encounter:
		- Orc (high HP, high damage, drops Silver Ring)
		- Goblin (medium HP, grants Max HP bonus)
		- Slime (low HP, grants HP recovery)

### 3. Weapon System

Weapons affect player damage range and now have durability.
Weapons can break and revert to the Knife when durability reaches 0.

Possible weapon drops from random item search:

- Void Nature Glass Sword (2%)
- Dynian Sword (28%)
- Steel Sword (20%)
- Old Sword (50%)

### 4. Grass and Healing

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

- Open inventory via the bag icon button.
- Navigate grass selection with `<` and `>` in inventory.
- Eat selected grass with `Eat Grass`.
- Item counts are shown for collected grass.

### 6. Progression and Training

- South includes `Train (+Max HP)` to raise max HP and fully heal.
- Settings menu shows current stats and weapon durability.

## How To Run

This project uses Gradle and JavaFX.

### Option A: Run with Gradle Wrapper

```bash
./gradlew run
```

On Windows:

```bat
gradlew.bat run
```

### Option B: Build a Distribution

```bash
./gradlew build
```

## Project Structure

```text
README.md
build.gradle
gradle.properties
settings.gradle
src/
	main/
		java/
			module-info.java
			pack/
				App.java
				GameController.java
				SoundManager.java
				TitleController.java
		resources/
			pack/
				game.fxml
				title.fxml
				style.css
```

## Notes

- The UI is built with JavaFX and FXML.
- Game balance and drop rates can be tuned in GameController.
- Some class names and text intentionally preserve original project naming style.
- At the beginning this project was 100% human-made with purpose to learning Java, since there are AI-tools, I start polish and change some algorithm with AI.
