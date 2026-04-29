# AdvantureGUI — Product Requirements Document (PRD) v2.0

> **Version:** 2.0  
> **Date:** April 29, 2026  
> **Status:** Current State — Dual-Build Documentation (Swing v1 + JavaFX v2)

---

## 1. Product Overview

### 1.1 Summary

**AdvantureGUI** is a single-player text-adventure RPG built in Java. The player explores a fantasy world through narrative text and choice buttons — collecting items, fighting monsters, and completing a quest to enter a guarded town.

### 1.2 Origin

> *"At the beginning this project was 100% human-made with purpose to learning Java, since there are AI-tools, I start polish and change some algorithm with AI."*

### 1.3 Project Evolution

| Phase | Build | UI Framework | Build System | Status |
|---|---|---|---|---|
| **v1.0** | Legacy | Java Swing | Apache Ant (`build.xml`) | ✅ Complete, archived in `Advanture/` |
| **v2.0** | Current | JavaFX + FXML + CSS | Gradle + JavaFX Plugin | ✅ Complete, active in `src/` |

---

## 2. Build System & Architecture

### 2.1 Build v2 — JavaFX + Gradle (CURRENT)

| Property | Value |
|---|---|
| **Build Tool** | Gradle with `org.openjfx.javafxplugin` v0.1.0 |
| **Java Version** | 21 (via toolchain in `build.gradle`) |
| **JDK Path** | `jdk21-temp/jdk-21.0.10+7` (set in `gradle.properties`) |
| **JavaFX Version** | 21.0.2 |
| **JavaFX Modules** | `javafx.controls`, `javafx.fxml`, `javafx.media` |
| **Group** | `com.advanture` |
| **App Version** | `2.0.0` |
| **Main Module** | `Advanture` |
| **Main Class** | `pack.App` |
| **Run Command** | `gradlew.bat run` |

#### Module Descriptor (`src/main/java/module-info.java`)

```java
module Advanture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;   // For SoundManager (javax.sound.sampled)
    opens pack to javafx.fxml;
    exports pack;
}
```

#### Source Layout

```
AdvantureGUI/              ← Project root
├── build.gradle           ← Gradle build config
├── gradle.properties      ← JDK path config
├── settings.gradle        ← rootProject.name = 'Advanture'
├── gradlew.bat            ← Gradle wrapper (Windows)
├── src/main/
│   ├── java/
│   │   ├── module-info.java
│   │   └── pack/
│   │       ├── App.java              (42 lines)  — JavaFX Application entry point
│   │       ├── GameController.java   (663 lines) — All game state + UI logic
│   │       ├── SoundManager.java     (126 lines) — Synthesized SFX engine
│   │       └── TitleController.java  (30 lines)  — Title screen controller
│   └── resources/pack/
│       ├── title.fxml    — Title screen layout
│       ├── game.fxml     — Main game screen layout
│       └── style.css     — Dark RPG theme stylesheet
└── Advanture/             ← Legacy Swing build (archived)
```

### 2.2 Build v1 — Java Swing + Ant (LEGACY)

| Property | Value |
|---|---|
| **Build Tool** | Apache Ant (`Advanture/build.xml`) |
| **Java Version** | 24 (source/target in build.xml) |
| **IDE Support** | Eclipse (`.classpath`, `.project`), IntelliJ (`.idea/`) |
| **Main Class** | `pack.Game` |
| **Run Command** | `ant -f build.xml game` |

#### Legacy Source Layout

```
Advanture/src/
├── module-info.java          — requires java.desktop only
└── pack/
    ├── Game.java             (1,501 lines) — Monolithic: UI + logic + inner classes
    ├── AnimatedChoiceButton.java (123 lines) — Custom JButton with scale animations
    ├── TypewriterTextArea.java   (54 lines)  — Character-by-character text display
    ├── BagIcon.java              (52 lines)  — Procedural bag icon (Graphics2D)
    ├── GearIcon.java             (54 lines)  — Procedural gear icon (Graphics2D)
    ├── SoundManager.java         (126 lines) — Shared with v2
    ├── playersetup.java          (30 lines)  — Mostly unused player data stub
    └── package-info.java         (1 line)    — Package descriptor
```

---

## 3. Java Files — Detailed Breakdown

### 3.1 `App.java` — Application Entry Point (v2 only)

**Purpose:** Launches the JavaFX application, loads the title screen FXML, applies the CSS stylesheet, and configures the primary stage.

| Responsibility | Detail |
|---|---|
| Stage setup | Title: "Advanture", Size: 1000×680, Min: 800×500 |
| Scene loading | Loads `/pack/title.fxml` as initial root |
| CSS binding | Applies `/pack/style.css` globally |
| Static access | `getPrimaryStage()` for controllers to access the window |

### 3.2 `TitleController.java` — Title Screen Controller (v2 only)

**Purpose:** Handles the START button click on the title screen. Transitions to the game screen.

| Method | Behavior |
|---|---|
| `onStartClicked()` | Plays click SFX → loads `game.fxml` → replaces scene root |

### 3.3 `GameController.java` — Core Game Controller (v2)

**Purpose:** The central controller for all game state, scene transitions, combat, inventory, and UI updates. Bound to `game.fxml` via FXML injection.

#### FXML-Bound UI Elements

| Field | Type | Purpose |
|---|---|---|
| `rootPane` | `BorderPane` | Root layout container |
| `hpLabel`, `maxHpLabel` | `Label` | Player HP display |
| `weaponLabel` | `Label` | Current weapon + durability |
| `monsterName` | `Label` | Enemy name in combat |
| `monsterHPBar` | `ProgressBar` | Visual enemy HP bar |
| `monsterHPText` | `Label` | Numeric enemy HP text |
| `monsterHUD` | `HBox` | Monster info row (hidden outside combat) |
| `mainTextArea` | `TextArea` | Narrative text display |
| `c1`–`c4` | `Button` | Choice buttons |
| `invButton` | `Button` | Inventory shortcut (🎒) |
| `settingButton` | `Button` | Settings shortcut (⚙) |

#### Game State Variables

| Variable | Type | Initial | Purpose |
|---|---|---|---|
| `pHP` | int | 15 | Current player HP |
| `maxHP` | int | 15 | Maximum player HP |
| `Wp` | String | "Knife" | Current weapon name |
| `wpDurability` | int | 0 | Current weapon durability |
| `wpMaxDurability` | int | 0 | Max durability (0 = infinite/Knife) |
| `position` | String | "" | Current game state/scene identifier |
| `prevp` | String | "" | Previous position for context-aware returns |
| `monHP` | int | 25 | Current monster HP |
| `monNameStr` | String | "Monster" | Current monster name |
| `monMaxDmg` | int | 8 | Current monster max damage |
| `ng, ngm, ngh, nghs` | int | 0 | Grass item counts (4 tiers) |
| `vsword, osword, sword, dsword` | int | 0 | Weapon ownership flags |
| `sRing` | int | 0 | Silver Ring flag (quest item) |
| `selectedGrassIndex` | int | 0 | Currently selected grass in inventory |
| `isFullscreen` | boolean | false | Fullscreen toggle state |

#### All Scene Methods (22 total)

| Method | Position Key | Description |
|---|---|---|
| `tg()` | `tg` | Town Gate — starting area |
| `talkGuard()` | `talkGuard` | Guard dialogue |
| `attg()` | `attg` | Attack the guard (takes 6 damage) |
| `how()` | `how` | Quest info from guard |
| `crossRoad()` | `crossRoad` | Central hub (N/S/E/W) |
| `north()` | `north` | River area |
| `lAround()` | `LAround` | Look around after leaving river |
| `drink()` | `drink` | Drink river water (+5 HP) |
| `rest()` | `rest` | Rest anywhere (+2 HP, capped at maxHP) |
| `south()` | `south` | Herb/training area |
| `southLookAround()` | `southLAround` | Shows drop chance tables |
| `trainMaxHP()` | `trainHP` | Train: maxHP += 3–7, HP fully healed |
| `sr()` | `sr` | Random weapon search |
| `cHerb()` | `CHerb` | Collect herb (random grass drop) |
| `east()` | `east` | Monster encounter zone |
| `fight()` | `fight` | Combat screen |
| `attack()` | `attack` | Player attacks monster |
| `defend()` | `defend` | Player defends (reduced damage) |
| `monatt()` | `monatt` | Monster counter-attack |
| `win()` | `win` | Monster defeated + drops |
| `death()` | `death` | Game Over screen |
| `end()` | `end` | Victory ending screen |
| `setting()` | `setting` | Settings menu |
| `status()` | `status` | Player stats display |
| `inventory()` | `inventory` | Inventory screen |

### 3.4 `SoundManager.java` — Sound Effects Engine (shared v1 & v2)

**Purpose:** Synthesizes and plays sound effects using `javax.sound.sampled`. No external audio files.

| Method | Sound | Duration | Technique |
|---|---|---|---|
| `playSlash()` | Sword slash | 150ms | White noise burst with fast decay |
| `playGulp()` | Drink/eat | 250ms | Low freq sweep with sinusoidal modulation |
| `playClick()` | UI click | 40ms | 800Hz sine with quadratic decay |
| `playHit()` | Damage | 120ms | 120Hz sine + noise with linear decay |
| `playVictory()` | Win fanfare | 400ms | Ascending 4-note (C5→E5→G5→C6) |

| Detail | Value |
|---|---|
| Sample Rate | 22,050 Hz |
| Format | 8-bit, mono, signed, little-endian |
| Threading | Each sound on new `SoundThread` |
| Cleanup | Auto-close via `LineListener` on STOP |

### 3.5 Legacy `Game.java` — Monolithic Swing Controller (v1)

**Purpose:** The original all-in-one class (1,501 lines) containing UI setup, game logic, and 8 inner classes.

#### Inner Classes (v1 only — replaced in v2)

| Inner Class | Type | Purpose |
|---|---|---|
| `AnimatedChoiceButton` | `JButton` subclass | Rounded buttons with scale hover/popup animations |
| `TypewriterTextArea` | `JTextArea` subclass | Character-by-character text (9ms/char) |
| `BagIcon` | `Icon` impl | Procedural backpack icon via Graphics2D |
| `GearIcon` | `Icon` impl | Procedural gear icon with 8 teeth |
| `ChoiceHandler` | `ActionListener` | Giant switch-case routing all clicks |
| `TitleScreenHandler` | `ActionListener` | START → `createGameScreen()` |
| `InventoryButtonHandler` | `ActionListener` | Bag → `openInventoryShortcut()` |
| `SettingButtonHandler` | `ActionListener` | Gear → `setting()` |

### 3.6 Legacy Helper Files (v1 only)

| File | Lines | Purpose |
|---|---|---|
| `AnimatedChoiceButton.java` | 123 | Custom JButton with `playPopup()`, `setButtonColors()`, `animateTo()` |
| `TypewriterTextArea.java` | 54 | Overrides `setText()` for typewriter, `setImmediateText()` for instant |
| `BagIcon.java` | 52 | Procedural bag icon via Graphics2D arcs and lines |
| `GearIcon.java` | 54 | Procedural gear — circle + 8 rotated tooth rectangles |
| `playersetup.java` | 30 | Stub class with `pHP`, `Wp`, sword flags — mostly commented out |

---

## 4. FXML Layouts

### 4.1 `title.fxml`

```
┌─────────────────────────────────┐
│           ADVANTURE             │  ← #titleLabel (80px)
│           [ START ]             │  ← #startButton (28px, rounded)
└─────────────────────────────────┘
```

Root: `StackPane` (1000×680), Controller: `TitleController`

### 4.2 `game.fxml`

```
┌──────────────────────────────────────┐
│ ⚙  HP: 15 / 15     Weapon: Knife    │ ← TOP: Player HUD
│              Orc ████████░░ 42/50    │ ← Monster HUD (hidden default)
├──────────────────────────────────────┤
│  You are at the Gate of the Town.    │ ← CENTER: TextArea
├──────────────────────────────────────┤
│     [ Talk to the Guard ]    🎒     │ ← BOTTOM: Choices + Inventory
│     [ Attack the Guard  ]            │
│     [       Leave       ]            │
└──────────────────────────────────────┘
```

Root: `BorderPane`, Controller: `GameController`

---

## 5. CSS Stylesheet (`style.css`)

| Token | Value |
|---|---|
| Background | `#000000` |
| Text | `white` |
| Font | `Times New Roman` |
| Button Fill | `#121212` |
| Button Border | `rgba(255,255,255,0.8)`, 2px, radius 20px |
| Monster HP Bar | Gradient `#dc3232` → `#ff5050` |
| Monster Name | `#ff5050` |
| Dimmed Button | 50% opacity |

### Animations

| Element | Trigger | Effect |
|---|---|---|
| `.choice-button:hover` | Enter | Scale 1.05× + shadow |
| `.choice-button:pressed` | Click | Scale 0.97× |
| `#startButton:hover` | Enter | Scale 1.08× + glow |
| `.icon-button:hover` | Enter | Scale 1.15× |

---

## 6. Game Mechanics

### 6.1 Monster System

| Monster | HP | Max Dmg | Spawn % | Drop |
|---|---|---|---|---|
| Slime | 15 | 5 | 60% | Slime Gel (+5 HP) |
| Goblin | 30 | 9 | 30% | Vitality Stone (+3 Max HP) |
| Orc | 50 | 14 | 10% | Silver Ring (quest) |

### 6.2 Weapon Table

| Weapon | Dmg Range | Durability | Drop % |
|---|---|---|---|
| Knife | 2–6 | ∞ | default |
| Old Sword | 6–12 | 10 | 50% |
| Steel Sword | 10–18 | 15 | 20% |
| Dynian Sword | 18–32 | 20 | 28% |
| Void NG Sword | 30–55 | 50 | 2% |

### 6.3 Grass Healing Items

| Grass | Heal | Drop % |
|---|---|---|
| Nature Glass | +5 HP | 50% |
| Rare Nature Glass | +15 HP | 20% |
| Super Rare Nature Glass | +30 HP | 28% |
| Absolute Nature Glass | +100% current HP | 2% |

### 6.4 Other Healing

| Source | HP | Location |
|---|---|---|
| Rest | +2 (capped) | North, South, East |
| Drink | +5 (capped) | North |
| Train | Full heal + maxHP +3–7 | South |

### 6.5 Kalkulasi Matematis (Algoritma Sistem)

Bagian ini mendefinisikan rumus di balik layar untuk setiap mekanik utama game:

#### Kalkulasi Serangan (Player Attack)

| Senjata | Rumus Java | Range Aktual |
|---|---|---|
| Knife | `playerD = 2 + new Random().nextInt(5)` | 2–6 |
| Old Sword | `playerD = 6 + new Random().nextInt(7)` | 6–12 |
| Steel Sword | `playerD = 10 + new Random().nextInt(9)` | 10–18 |
| Dynian Sword | `playerD = 18 + new Random().nextInt(15)` | 18–32 |
| Void NG Sword | `playerD = 30 + new Random().nextInt(26)` | 30–55 |

> **Pola umum:** `Damage = minDmg + new Random().nextInt(maxDmg - minDmg + 1)`
> Menghasilkan angka acak antara Min Damage dan Max Damage sesuai statistik senjata yang sedang digunakan.

#### Defend (Bertahan)

```java
// Damage monster saat player defend:
monD = new Random().nextInt(Math.max(1, monMaxDmg / 3));
```

| Monster | `monMaxDmg` | `monMaxDmg / 3` | Defend Damage Range |
|---|---|---|---|
| Slime | 5 | 1 | 0–0 |
| Goblin | 9 | 3 | 0–2 |
| Orc | 14 | 4 | 0–3 |

> **Catatan:** Saat pemain memilih "Defend", damage monster direduksi menjadi `1/3` dari damage maksimal normalnya. `Math.max(1, ...)` mencegah pembagian nol.

#### Serangan Monster Normal (Monster Attack)

```java
// Damage monster saat menyerang normal (setelah player attack):
monD = new Random().nextInt(Math.max(1, monMaxDmg));
```

| Monster | `monMaxDmg` | Normal Damage Range |
|---|---|---|
| Slime | 5 | 0–4 |
| Goblin | 9 | 0–8 |
| Orc | 14 | 0–13 |

#### Monster Encounter (Spawn Chance)

```java
double roll = Math.random();
if (roll < 0.10)      → Orc    (HP: 50, MaxDmg: 14)   // 10%
else if (roll < 0.40)  → Goblin (HP: 30, MaxDmg: 9)    // 30%
else                   → Slime  (HP: 15, MaxDmg: 5)    // 60%
```

> Probabilitas dievaluasi secara sekuensial. Monster dipilih secara acak setiap kali pemain memasuki area East.

#### Kalkulasi Drop Rate (Weapon & Grass)

```java
double chan = Math.random();  // menghasilkan 0.0 – 0.999...
if (chan < 0.02)       → Legendary (2%)    // Void NG Sword / Absolute Nature Glass
else if (chan < 0.30)  → Rare (28%)        // Dynian Sword / Super Rare Nature Glass
else if (chan < 0.50)  → Uncommon (20%)    // Steel Sword / Rare Nature Glass
else                   → Common (50%)      // Old Sword / Nature Glass
```

> **Catatan:** Probabilitas dievaluasi secara sekuensial dari persentase terkecil (paling langka) ke terbesar. Sistem drop rate ini **identik** untuk weapon dan grass — hanya item yang berbeda.

#### Training (South — Max HP)

```java
int gain = 3 + new Random().nextInt(5);  // gain = 3, 4, 5, 6, atau 7
maxHP += gain;
pHP = maxHP;  // HP dipulihkan penuh 100%
```

> Memulihkan HP secara penuh ke maxHP dan menambah Max HP secara permanen antara **3 hingga 7 poin** per sesi latihan.

#### Healing Cap (Semua Sumber)

```java
pHP = Math.min(pHP + healAmount, maxHP);
```

> **Semua healing** (Rest, Drink, Grass, Train, Slime drop) dibatasi oleh `maxHP`. HP tidak pernah melebihi Max HP. Satu-satunya pengecualian: Training juga **menaikkan** maxHP sebelum heal.

#### Weapon Durability

```java
// Setiap kali player menyerang:
if (wpMaxDurability > 0) {
    wpDurability--;
    if (wpDurability <= 0) {
        Wp = "Knife";           // Revert ke senjata default
        wpDurability = 0;
        wpMaxDurability = 0;
    }
}
```

> Setiap serangan mengurangi durability sebesar 1. Saat durability mencapai 0, senjata **hancur** dan otomatis kembali ke Knife (durability tak terbatas). Knife tidak memiliki durability (`wpMaxDurability = 0`), sehingga pengecekan di-skip.

#### Absolute Nature Glass (Healing Spesial)

```java
heal = pHP;                          // heal = HP saat ini
pHP = Math.min(pHP + heal, maxHP);   // efektif: pHP × 2 (capped at maxHP)
```

> Item ini mengheal sebesar **100% HP saat ini** — secara efektif menggandakan HP pemain (dibatasi maxHP). Semakin tinggi HP saat digunakan, semakin besar heal-nya.

---

## 7. v1 → v2 Changelog

### Added in v2

| Feature | Detail |
|---|---|
| Multiple monsters | Slime/Goblin/Orc with unique stats and drops |
| Monster HUD | HP bar + name during combat |
| Weapon durability | Weapons break → revert to Knife |
| Max HP training | South "Train" option |
| Stackable grass | Count-based instead of binary flags |
| Restart button | "Play Again" on death/end |
| Fullscreen toggle | Settings menu |
| Status screen | HP, weapon, durability, Silver Ring |
| Drop chance display | South "Look Around" |
| FXML layouts | Declarative UI in XML |
| CSS theming | External stylesheet |
| Sound effects | 5 synthesized SFX |

### Removed in v2

| Feature | Replaced By |
|---|---|
| Typewriter text | Plain TextArea.setText() |
| Custom button painting | CSS pseudo-classes |
| Procedural icons | Emoji (🎒, ⚙) |
| Inner classes | Controller methods |
| Absolute positioning | JavaFX layout managers |

### Fixed in v2

| Bug | Fix |
|---|---|
| Drink showed "+6" but gave +5 | Now correctly shows "+5" |
| defend→monatt fall-through | Proper break in switch |
| No restart on death | "Play Again" resets state |
| Unused Scanner/playersetup | Removed |

---

## 8. World Navigation Map

```
Title Screen → [START] → Town Gate
    ├── Talk Guard → How to enter? → [Silver Ring?] → THE END
    ├── Attack Guard → takes 6 dmg → "I'm sorry" → Town Gate
    └── Leave → Crossroad
        ├── North → Drink (+5) / Rest (+2) / Look Around
        ├── South → Collect Herb / Search Weapon / Train (+MaxHP)
        ├── East → Monster Encounter → Fight / Rest / Run
        └── West → Town Gate
```

---

## 9. Feature Status (v2)

| Feature | Status |
|---|---|
| Title Screen | ✅ |
| Town Gate + Guard | ✅ |
| Crossroad navigation | ✅ |
| North/South/East areas | ✅ |
| 3 monster types | ✅ |
| Monster HUD | ✅ |
| Combat system | ✅ |
| Weapon durability | ✅ |
| Inventory (stackable) | ✅ |
| Settings + Fullscreen | ✅ |
| Sound effects | ✅ |
| Restart on death/end | ✅ |
| CSS theming | ✅ |
| Save/Load | ❌ |
| Background music | ❌ |
| Typewriter text | ❌ (removed) |

---

## 10. How to Build & Run

```bash
# v2 (current) — JavaFX + Gradle
gradlew.bat run

# v1 (legacy) — Swing + Ant
cd Advanture && ant -f build.xml game
```

---

## 11. Known Bugs (v2)

### Bug #1 — Inventory "Go Back" Selalu Mengarah ke Town Gate

| Detail | Keterangan |
|---|---|
| **Severity** | 🔴 High |
| **Reproduksi** | Buka inventory (🎒) → tekan "Go back" |
| **Expected** | Kembali ke posisi sebelumnya (crossroad, north, dst.) |
| **Actual** | Langsung ke Town Gate (`tg()`) |
| **File** | [GameController.java:287-296](file:///c:/Users/Asus/Documents/Project/GameDev/AdvantureGUI/src/main/java/pack/GameController.java#L287-L296) |

**Root Cause:** Di `handleChoice()` case `"inventory"`, tombol "Go back" (cho1) mengecek `prevp.equals("shortcutInventory")`. Jika kondisi ini gagal (misalnya `prevp` ter-overwrite oleh aksi navigasi lain sebelumnya), fallback-nya adalah `tg()`:

```java
case "cho1":
    if (prevp.equals("shortcutInventory")) { restoreFromInventoryShortcut(); }
    else { tg(); }  // ← Fallback selalu ke Town Gate
    break;
```

**Kemungkinan penyebab `prevp` ter-overwrite:** Beberapa handler di `handleChoice()` mengubah `prevp` (contoh: `prevp = "north"`, `prevp = "attack"`) sebelum inventory dibuka, dan `onInventoryClicked()` mungkin tidak selalu berhasil meng-set `prevp = "shortcutInventory"` jika ada race condition atau edge case.

---

### Bug #2 — HP Otomatis Max Setelah Keluar dari Inventory

| Detail | Keterangan |
|---|---|
| **Severity** | 🔴 High |
| **Reproduksi** | Buka inventory (🎒) → tekan "Go back" |
| **Expected** | HP tetap sama seperti sebelum membuka inventory |
| **Actual** | HP otomatis menjadi maxHP |
| **File** | [GameController.java:637-643](file:///c:/Users/Asus/Documents/Project/GameDev/AdvantureGUI/src/main/java/pack/GameController.java#L637-L643) |

**Root Cause:** Kemungkinan terkait dengan Bug #1. Jika "Go back" jatuh ke fallback `tg()`, maka `tg()` memanggil `updateMonsterHUD()` (line 312) tetapi tidak memodifikasi HP. Namun jika alur jatuh ke `restoreFromInventoryShortcut()`, method ini hanya mengembalikan `position`, `text`, dan `choices` — tanpa memverifikasi bahwa HP yang ditampilkan di HUD masih sinkron dengan `pHP` aktual:

```java
private void restoreFromInventoryShortcut() {
    position = inventoryReturnPosition;
    mainTextArea.setText(inventoryReturnText);
    setChoices(...);
    inventoryReturnPosition = "";
    // ← Tidak ada updateHUD() di sini
}
```

Jika pemain memakan grass di dalam inventory (menambah HP), lalu kembali, HUD sudah ter-update oleh `eatGrass()`. Tetapi jika terjadi state corruption, HP display bisa menjadi tidak sinkron.

---

### Bug #3 — Infinite Loop Drink di North

| Detail | Keterangan |
|---|---|
| **Severity** | 🟡 Medium (Game Design Issue) |
| **Reproduksi** | North → Drink → Look Around → Drink → Look Around → ... |
| **Expected** | Ada batasan atau cooldown pada aksi Drink |
| **Actual** | Pemain bisa minum air tanpa batas, heal +5 HP setiap kali |
| **File** | [GameController.java:234-239](file:///c:/Users/Asus/Documents/Project/GameDev/AdvantureGUI/src/main/java/pack/GameController.java#L234-L239) dan [lines 265-270](file:///c:/Users/Asus/Documents/Project/GameDev/AdvantureGUI/src/main/java/pack/GameController.java#L265-L270) |

**Root Cause:** Setelah `drink()`, pilihan "Look Around" mengarah ke `lAround()`. Dari `lAround()`, pilihan "Drink it" mengarah kembali ke `drink()`. Loop ini tidak pernah terputus secara otomatis:

```
drink() → choices: [Crossroad, Look Around, Rest]
                          │
                          ▼
              lAround() → choices: [Drink it, Let's not, Rest]
                          │
                          ▼
              drink()  → +5 HP setiap cycle (capped at maxHP)
                          │
                          ▼
                        ... (berulang tanpa batas)
```

Pemain **bisa** keluar dengan memilih "Go back to Crossroad" atau "Let's not", tetapi tidak ada mekanisme yang mencegah healing tak terbatas. Ini berarti pemain selalu bisa full heal sebelum bertarung, mengurangi tantangan game.
