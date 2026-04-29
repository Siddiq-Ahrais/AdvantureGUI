package pack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Main game controller — handles all game state and UI updates.
 * Ported from Swing Game.java to JavaFX FXML controller.
 */
public class GameController {

    // FXML-bound UI elements
    @FXML private BorderPane rootPane;
    @FXML private Label hpLabel, maxHpLabel, weaponLabel;
    @FXML private Label monsterName, monsterHPText;
    @FXML private ProgressBar monsterHPBar;
    @FXML private HBox monsterHUD;
    @FXML private TextArea mainTextArea;
    @FXML private Button c1, c2, c3, c4, invButton, settingButton;

    // Game state
    private int pHP = 15, maxHP = 15;
    private String Wp = "Knife";
    private String position = "";
    private int wpDurability = 0, wpMaxDurability = 0;
    private String prevp = "";
    private int playerD, monD, ng, ngm, ngh, nghs;
    private int vsword, osword, sword, dsword, sRing;
    private int monHP = 25;
    private String monNameStr = "Monster";
    private int monMaxDmg = 8;
    private boolean isFullscreen = false;
    private int selectedGrassIndex = 0;
    private int vertigoStacks = 0;
    private int slipperyStacks = 0;
    private int slipperyActionCount = 0;

    // Inventory shortcut state
    private String inventoryReturnPosition = "";
    private String inventoryReturnText = "";
    private String[] inventoryReturnChoices = {"", "", "", ""};

    // Settings shortcut state
    private String settingReturnPosition = "";
    private String settingReturnText = "";
    private String[] settingReturnChoices = {"", "", "", ""};

    @FXML
    public void initialize() {
        tg();
        updateHUD();
    }

    // ========== HUD Updates ==========

    private void updateHUD() {
        hpLabel.setText("" + pHP);
        maxHpLabel.setText("" + maxHP);
        String showName = Wp;
        if (wpMaxDurability > 0) {
            showName += " (" + wpDurability + "/" + wpMaxDurability + ")";
        }
        weaponLabel.setText(showName);
    }

    private void updateMonsterHUD() {
        boolean inCombat = position.equals("fight") || position.equals("attack")
                || position.equals("defend") || position.equals("monatt");
        monsterHUD.setVisible(inCombat);
        monsterHUD.setManaged(inCombat);
        if (inCombat) {
            monsterName.setText(monNameStr);
            int maxMon = monNameStr.equals("Orc") ? 50 : monNameStr.equals("Goblin") ? 30 : 15;
            monsterHPBar.setProgress(Math.max(0, (double) monHP / maxMon));
            monsterHPText.setText(Math.max(0, monHP) + " / " + maxMon);
        }
    }

    private void advanceSlipperyOnAction() {
        if (slipperyStacks <= 0) {
            return;
        }
        slipperyActionCount++;
        if (slipperyActionCount >= 3) {
            slipperyActionCount -= 3;
            slipperyStacks = Math.max(0, slipperyStacks - 1);
            if (slipperyStacks == 0) {
                slipperyActionCount = 0;
            }
        }
    }

    private void setChoices(String t1, String t2, String t3, String t4) {
        setButton(c1, t1);
        setButton(c2, t2);
        setButton(c3, t3);
        setButton(c4, t4);
    }

    private void setButton(Button btn, String text) {
        btn.setText(text);
        if (text == null || text.isEmpty()) {
            btn.setVisible(false);
            btn.setManaged(false);
        } else {
            btn.setVisible(true);
            btn.setManaged(true);
            btn.getStyleClass().removeAll("choice-button-dimmed");
            if (!btn.getStyleClass().contains("choice-button")) {
                btn.getStyleClass().add("choice-button");
            }
            btn.setDisable(false);
        }
    }

    // ========== Choice Handlers ==========

    @FXML private void onChoice1() { handleChoice("cho1"); }
    @FXML private void onChoice2() { handleChoice("cho2"); }
    @FXML private void onChoice3() { handleChoice("cho3"); }
    @FXML private void onChoice4() { handleChoice("cho4"); }

    @FXML
    private void onInventoryClicked() {
        if (position.isEmpty() || position.equals("inventory")) return;
        if (!position.equals("setting") && !position.equals("status")) {
            advanceSlipperyOnAction();
        }
        inventoryReturnPosition = position;
        inventoryReturnText = mainTextArea.getText();
        inventoryReturnChoices = new String[]{c1.getText(), c2.getText(), c3.getText(), c4.getText()};
        prevp = "shortcutInventory";
        inventory();
    }

    @FXML
    private void onSettingClicked() {
        if (position.equals("end") || position.equals("death")) {
            return;
        }
        if (!position.equals("setting") && !position.equals("status")) {
            settingReturnPosition = position;
            settingReturnText = mainTextArea.getText();
            settingReturnChoices = new String[]{c1.getText(), c2.getText(), c3.getText(), c4.getText()};
        }
        setting();
    }

    private void handleChoice(String choice) {
        SoundManager.playClick();
        if (!position.equals("setting") && !position.equals("status")) {
            advanceSlipperyOnAction();
        }
        switch (position) {
            case "tg":
                switch (choice) {
                    case "cho1": talkGuard(); break;
                    case "cho2": attg(); break;
                    case "cho3": crossRoad(); break;
                }
                break;
            case "talkGuard":
                switch (choice) {
                    case "cho1": how(); break;
                    case "cho2": tg(); break;
                    case "cho3": crossRoad(); break;
                }
                break;
            case "fight":
                switch (choice) {
                    case "cho1": attack(); prevp = "attack"; break;
                    case "cho2": defend(); prevp = "defend"; break;
                    case "cho3": crossRoad(); break;
                }
                break;
            case "attack":
                switch (choice) {
                    case "cho1":
                        if (monHP < 1) { win(); }
                        else { monatt(); }
                        break;
                }
                break;
            case "defend":
                switch (choice) {
                    case "cho1":
                        if (pHP < 1) { death(); }
                        else { fight(); }
                        break;
                }
                break;
            case "monatt":
                switch (choice) {
                    case "cho1":
                        if (pHP < 1) { death(); }
                        else { fight(); }
                        break;
                }
                break;
            case "win":
                switch (choice) {
                    case "cho1": crossRoad(); break;
                }
                break;
            case "attg":
                switch (choice) {
                    case "cho1":
                        if (pHP < 1) { death(); } else { tg(); }
                        break;
                }
                break;
            case "how":
                switch (choice) {
                    case "cho1": if (sRing == 1) { end(); } break;
                    case "cho2": talkGuard(); break;
                    case "cho3": crossRoad(); break;
                }
                break;
            case "setting":
                switch (choice) {
                    case "cho1": status(); break;
                    case "cho2": toggleFullscreen(); break;
                    case "cho3": restoreFromSettingShortcut(); break;
                }
                break;
            case "status":
                switch (choice) {
                    case "cho1": setting(); break;
                }
                break;
            case "crossRoad":
                switch (choice) {
                    case "cho1": north(); prevp = "north"; break;
                    case "cho2": south(); prevp = "south"; break;
                    case "cho3": tg(); prevp = "tg"; break;
                    case "cho4": east(); prevp = "east"; break;
                }
                break;
            case "south":
                switch (choice) {
                    case "cho1": cHerb(); break;
                    case "cho2": sr(); break;
                    case "cho3": trainMaxHP(); break;
                    case "cho4": crossRoad(); break;
                }
                break;
            case "east":
                switch (choice) {
                    case "cho1": fight(); break;
                    case "cho2": rest(); prevp = "east"; break;
                    case "cho3": crossRoad(); break;
                }
                break;
            case "north":
                switch (choice) {
                    case "cho1": drink(); break;
                    case "cho2": lAround(); break;
                    case "cho3": rest(); prevp = "north"; break;
                    case "cho4": lAround(); break;
                }
                break;
            case "drink":
                switch (choice) {
                    case "cho1": crossRoad(); break;
                    case "cho2": rest(); prevp = "north"; break;
                }
                break;
            case "rest":
                switch (choice) {
                    case "cho1": crossRoad(); break;
                    case "cho2":
                        if (prevp.equals("east")) { east(); }
                        else if (prevp.equals("north")) { north(); }
                        else if (prevp.equals("south")) { southLookAround(); }
                        break;
                }
                break;
            case "southLAround":
                switch (choice) {
                    case "cho1": cHerb(); break;
                    case "cho2": sr(); break;
                    case "cho3": trainMaxHP(); break;
                    case "cho4": crossRoad(); break;
                }
                break;
            case "trainHP":
                switch (choice) {
                    case "cho1": south(); break;
                    case "cho2": crossRoad(); break;
                }
                break;
            case "LAround":
                switch (choice) {
                    case "cho1": drink(); break;
                    case "cho2": crossRoad(); break;
                    case "cho3": rest(); prevp = "north"; break;
                }
                break;
            case "sr":
                switch (choice) {
                    case "cho1": cHerb(); break;
                    case "cho2": rest(); prevp = "south"; break;
                    case "cho3": south(); break;
                    case "cho4": crossRoad(); break;
                }
                break;
            case "CHerb":
                switch (choice) {
                    case "cho1": south(); break;
                    case "cho2": rest(); prevp = "south"; break;
                    case "cho3": crossRoad(); break;
                }
                break;
            case "inventory":
                switch (choice) {
                    case "cho1":
                        if (!inventoryReturnPosition.isEmpty()) { restoreFromInventoryShortcut(); }
                        else { tg(); }
                        break;
                    case "cho2": eatGrass(); break;
                    case "cho3": grassLeft(); break;
                    case "cho4": grassRight(); break;
                }
                break;
            case "death": case "end":
                switch (choice) {
                    case "cho1": restartGame(); break;
                }
                break;
        }
    }

    // ========== Scene Methods ==========

    private void tg() {
        position = "tg";
        mainTextArea.setText("You are at the Gate of the Town. \nA Guard is standing in front of you. \n\nWhat do you do?");
        setChoices("Talk to the Guard", "Attack the Guard", "Leave", "");
        updateMonsterHUD();
    }

    private void talkGuard() {
        position = "talkGuard";
        mainTextArea.setText("Guard : Hello stranger. i have never seen your face\n i'm sorry but we cannot let stranger enter our town");
        setChoices("How to enter Town?", "Go back", "Crossroad", "");
    }

    private void attg() {
        position = "attg";
        pHP -= 6;
        updateHUD();
        mainTextArea.setText("You are attacking the Guard\n\nMISS\n\nGuard : Who the fuck are you?\nGuard attack you and dealt 6 Damage");
        setChoices("I'm sorry", "", "", "");
    }

    private void how() {
        position = "how";
        mainTextArea.setText("Guard : You need to kill the Orc on\n the East and collect the Silver Ring");
        setChoices("I have the Silver Ring", "Go back", "Crossroad", "");
        if (sRing != 1) {
            c1.setDisable(true);
            c1.getStyleClass().removeAll("choice-button");
            c1.getStyleClass().add("choice-button-dimmed");
        }
    }

    private void crossRoad() {
        position = "crossRoad";
        mainTextArea.setText("You are in the crossroad\n if You go to the West, You wil go back to the TownGate");
        setChoices("North", "South", "West", "East");
        updateMonsterHUD();
    }

    private void north() {
        position = "north";
        mainTextArea.setText("You are in the North\n There is a clean river near you.\nYou can see the riverbed clearly.");
        setChoices("Drink it", "Let's not", "Rest", "Stay");
    }

    private void lAround() {
        position = "LAround";
        mainTextArea.setText("You are in the North\n There is a river you left before");
        setChoices("Drink it", "Crossroad", "Rest", "");
    }

    private void rest() {
        position = "rest";
        pHP = Math.min(pHP + 2, maxHP);
        updateHUD();
        mainTextArea.setText("You sit and take a little break!\n\nHP + 2");
        setChoices("Go back to Crossroad", "Look Around", "", "");
    }

    private void drink() {
        position = "drink";
        StringBuilder effectText = new StringBuilder();
        int baseHeal = 5;
        pHP = Math.min(pHP + baseHeal, maxHP);
        effectText.append("You drink River Water and feel refreshed!\n\nHP + ").append(baseHeal);

        double roll = Math.random();
        if (roll < 0.2) {
            int bonus = 3;
            pHP = Math.min(pHP + bonus, maxHP);
            effectText.append("\nLucky stream: HP + ").append(bonus);
        } else if (roll < 0.4) {
            int upset = Math.max(1, (int)Math.floor(pHP * 0.1));
            pHP = Math.max(1, pHP - upset);
            effectText.append("\nStomach ache: HP - ").append(upset);
        }

        if (Math.random() < 0.3) {
            vertigoStacks++;
            effectText.append("\nVertigo stack +1 (now ").append(vertigoStacks).append(")");
            if (vertigoStacks >= 3) {
                int vomitLoss = Math.max(1, (int)Math.floor(pHP * 0.4));
                pHP = Math.max(1, pHP - vomitLoss);
                vertigoStacks = 0;
                effectText.append("\nYou throw up! HP - ").append(vomitLoss).append(" (vertigo reset)");
            }
        } else {
            effectText.append("\nThe water feels clean.");
        }

        updateHUD();
        SoundManager.playGulp();
        mainTextArea.setText(effectText.toString());
        setChoices("Go back to Crossroad", "Rest", "", "");
    }

    private void south() {
        position = "south";
        mainTextArea.setText("You are at the South\n South is place for some Advanture \nCollecting Herb \n");
        setChoices("Collect Herb", "Search random item (?)", "Train (+Max HP)", "Go back");
    }

    private void southLookAround() {
        position = "southLAround";
        mainTextArea.setText("You look around in the South.\n\n"
                + "Grass Drop Chance          Weapon Drop Chance\n"
                + "Absolute Nature: 2%        Void NG Sword: 2%\n"
                + "Super Rare Nature: 28%     Dynian Sword: 28%\n"
                + "Rare Nature: 20%           Steel Sword: 20%\n"
                + "Nature: 50%                Old Sword: 50%");
        setChoices("Collect Herb", "Search random item (?)", "Train (+Max HP)", "Go back");
    }

    private void trainMaxHP() {
        position = "trainHP";
        int gain = 3 + new java.util.Random().nextInt(5);
        maxHP += gain;
        pHP = maxHP;
        updateHUD();
        mainTextArea.setText("You train hard and push your limits!\n\nMax HP + " + gain + "\nMax HP is now " + maxHP + "\nHP restored to " + pHP);
        setChoices("Go back", "Crossroad", "", "");
    }

    private void sr() {
        position = "sr";
        double chan = Math.random();
        if (chan < 0.02) {
            vsword = 1; Wp = "Void Nature Glass Sword"; wpDurability = 50; wpMaxDurability = 50;
            mainTextArea.setText("You get a Void Nature Glass Sword! (Durability: 50)");
        } else if (chan < 0.3) {
            dsword = 1; Wp = "Dynian Sword"; wpDurability = 20; wpMaxDurability = 20;
            mainTextArea.setText("You get a Dynian Sword! (Durability: 20)");
        } else if (chan < 0.5) {
            sword = 1; Wp = "Steel Sword"; wpDurability = 15; wpMaxDurability = 15;
            mainTextArea.setText("You get a Steel Sword! (Durability: 15)");
        } else {
            osword = 1; Wp = "Old Sword"; wpDurability = 10; wpMaxDurability = 10;
            mainTextArea.setText("You get a Old Sword! (Durability: 10)");
        }
        updateHUD();
        setChoices("Collect Herb", "Rest", "Go back", "Crossroad");
    }

    private void cHerb() {
        position = "CHerb";
        double chan = Math.random();
        if (chan < 0.02) { nghs++; mainTextArea.setText("You collect a Absolute Nature Glass Highest Grade"); }
        else if (chan < 0.3) { ngh++; mainTextArea.setText("You collect a Super Rare Nature Glass High-Grade"); }
        else if (chan < 0.5) { ngm++; mainTextArea.setText("You collect a Rare Nature Glass Medium-Grade"); }
        else { ng++; mainTextArea.setText("You collect a Nature Glass Low-Grade"); }
        setChoices("Go back", "Rest", "Crossroad", "");
    }

    private void east() {
        position = "east";
        double roll = Math.random();
        if (roll < 0.10) { monNameStr = "Orc"; monHP = 50; monMaxDmg = 14; }
        else if (roll < 0.40) { monNameStr = "Goblin"; monHP = 30; monMaxDmg = 9; }
        else { monNameStr = "Slime"; monHP = 15; monMaxDmg = 5; }
        mainTextArea.setText("You are at the East.\nA " + monNameStr + " appears! (HP: " + monHP + ")");
        setChoices("Fight", "Rest", "Run", "");
    }

    private void fight() {
        if (monHP < 1) { monHP = 25; monNameStr = "Monster"; monMaxDmg = 8; }
        position = "fight";
        mainTextArea.setText("You choose to fight the " + monNameStr + "\n" + monNameStr + " HP: " + monHP);
        setChoices("Attack", "Defend", "Run", "");
        updateMonsterHUD();
    }

    private void attack() {
        playerD = 0;
        position = "attack";
        SoundManager.playSlash();
        if (Wp.equals("Dynian Sword")) { playerD = 18 + new java.util.Random().nextInt(15); }
        else if (Wp.equals("Void Nature Glass Sword")) { playerD = 30 + new java.util.Random().nextInt(26); }
        else if (Wp.equals("Steel Sword")) { playerD = 10 + new java.util.Random().nextInt(9); }
        else if (Wp.equals("Old Sword")) { playerD = 6 + new java.util.Random().nextInt(7); }
        else { playerD = 2 + new java.util.Random().nextInt(5); }

        String breakMsg = "";
        if (wpMaxDurability > 0) {
            wpDurability--;
            if (wpDurability <= 0) {
                breakMsg = "\n\nYour " + Wp + " broke!";
                Wp = "Knife"; wpDurability = 0; wpMaxDurability = 0;
            }
        }
        double missChance = Math.min(1.0, slipperyStacks * 0.2);
        boolean missed = Math.random() < missChance;
        int dealtDamage = missed ? 0 : playerD;
        monHP -= dealtDamage;
        updateHUD();
        StringBuilder attackText = new StringBuilder();
        if (missed) {
            attackText.append("You swing at the ").append(monNameStr).append(" but miss due to slippery hands!\n");
        } else {
            attackText.append("You attack the ").append(monNameStr).append(" and dealt ").append(dealtDamage).append(" Damage!\n");
        }
        if (!missed && monNameStr.equals("Slime") && Math.random() < 0.04) {
            if (slipperyStacks < 5) {
                slipperyStacks++;
            }
            attackText.append("You feel slippery! (Slippery stack: ").append(slipperyStacks).append(")\n");
        }
        attackText.append(monNameStr).append(" HP : ").append(monHP).append(breakMsg);
        mainTextArea.setText(attackText.toString());
        setChoices(">", "", "", "");
        updateMonsterHUD();
    }

    private void defend() {
        position = "defend";
        monD = new java.util.Random().nextInt(Math.max(1, monMaxDmg / 3));
        pHP -= monD;
        updateHUD();
        SoundManager.playHit();
        StringBuilder defendText = new StringBuilder();
        defendText.append("You choose to defend the ").append(monNameStr).append(" attack\nDefend UP 1 Turn\n")
                .append(monNameStr).append(" attack You and dealt ").append(monD).append(" Damage!");
        if (monNameStr.equals("Slime") && monD > 0 && Math.random() < 0.7) {
            if (slipperyStacks < 5) {
                slipperyStacks++;
            }
            defendText.append("\nYou feel slippery! (Slippery stack: ").append(slipperyStacks).append(")");
        }
        mainTextArea.setText(defendText.toString());
        setChoices(">", "", "", "");
        updateMonsterHUD();
    }

    private void monatt() {
        position = "monatt";
        monD = new java.util.Random().nextInt(Math.max(1, monMaxDmg));
        pHP -= monD;
        SoundManager.playHit();
        updateHUD();
        StringBuilder monText = new StringBuilder();
        monText.append(monNameStr).append(" attack You and dealt ").append(monD).append(" Damage!\n")
                .append(monNameStr).append(" HP : ").append(monHP);
        if (monNameStr.equals("Slime") && monD > 0 && Math.random() < 0.7) {
            if (slipperyStacks < 5) {
                slipperyStacks++;
            }
            monText.append("\nYou feel slippery! (Slippery stack: ").append(slipperyStacks).append(")");
        }
        mainTextArea.setText(monText.toString());
        updateMonsterHUD();
        if (pHP <= 0) { death(); return; }
        setChoices(">", "", "", "");
    }

    private void win() {
        position = "win";
        SoundManager.playVictory();
        updateMonsterHUD();
        String dropMsg = "You killed the " + monNameStr + "!";
        if (monNameStr.equals("Orc")) {
            sRing = 1;
            dropMsg += "\nYou obtain a Magic Tool: Silver Ring";
        } else if (monNameStr.equals("Goblin")) {
            maxHP += 3; pHP += 3;
            dropMsg += "\nGoblin dropped a Vitality Stone! Max HP +3";
        } else {
            pHP = Math.min(pHP + 5, maxHP);
            dropMsg += "\nSlime dropped Slime Gel! HP +5";
        }
        updateHUD();
        mainTextArea.setText(dropMsg);
        setChoices(">", "", "", "");
    }

    private void death() {
        position = "death";
        SoundManager.playHit();
        updateMonsterHUD();
        mainTextArea.setText("Your HP 0\nYou Dead\n\n- GAME OVER -");
        setChoices("Play Again", "", "", "");
        invButton.setVisible(false);
        settingButton.setVisible(false);
    }

    private void end() {
        position = "end";
        mainTextArea.setText("Eh? You already have Silver Ring?\nYou can go in to the town Advanture.\n\n- THE END -");
        setChoices("Play Again", "", "", "");
        invButton.setVisible(false);
        settingButton.setVisible(false);
    }

    private void setting() {
        position = "setting";
        mainTextArea.setText("MENU");
        setChoices("Status", isFullscreen ? "Windowed" : "Fullscreen", "Go back", "");
    }

    private void restoreFromSettingShortcut() {
        if (settingReturnPosition.isEmpty()) {
            setting();
            return;
        }
        position = settingReturnPosition;
        mainTextArea.setText(settingReturnText);
        setChoices(settingReturnChoices[0], settingReturnChoices[1], settingReturnChoices[2], settingReturnChoices[3]);
        settingReturnPosition = "";
        updateHUD();
        updateMonsterHUD();
    }

    private void status() {
        position = "status";
        String durText = wpMaxDurability > 0 ? wpDurability + "/" + wpMaxDurability : "N/A";
        StringBuilder statusText = new StringBuilder();
        statusText.append("=== STATUS ===\n")
            .append("HP: ").append(pHP).append(" / ").append(maxHP).append("\n")
            .append("Weapon: ").append(Wp).append("\n")
            .append("Durability: ").append(durText).append("\n")
            .append("Silver Ring: ").append(sRing == 1 ? "Yes" : "No");
        if (vertigoStacks > 0) {
            statusText.append("\nVertigo: ").append(vertigoStacks).append(" stack(s)");
        }
        if (slipperyStacks > 0) {
            statusText.append("\nSlippery: ").append(slipperyStacks).append(" stack(s)");
        }
        mainTextArea.setText(statusText.toString());
        setChoices("Go back", "", "", "");
    }

    private void toggleFullscreen() {
        Stage stage = App.getPrimaryStage();
        isFullscreen = !isFullscreen;
        stage.setFullScreen(isFullscreen);
        setting();
    }

    // ========== Inventory ==========

    private void inventory() {
        position = "inventory";
        ensureSelectedGrassAvailable();
        StringBuilder invText = new StringBuilder();
        if (ng >= 1) invText.append("Nature Glass Lower-Grade x" + ng + "\n");
        if (ngm >= 1) invText.append("Nature Glass Medium-Grade x" + ngm + "\n");
        if (ngh >= 1) invText.append("Super Rare Nature Glass High-Grade x" + ngh + "\n");
        if (nghs >= 1) invText.append("Absolute Nature Glass Highest-Grade x" + nghs + "\n");
        if (vsword >= 1) invText.append("Void Nature Glass Sword\n");
        if (dsword >= 1) invText.append("Dynian Sword\n");
        if (sword >= 1) invText.append("Steel Sword\n");
        if (osword >= 1) invText.append("Old Sword\n");

        if (invText.length() == 0) {
            mainTextArea.setText("Inventory is empty");
        } else {
            if (hasAnyGrass()) {
                invText.append("\nSelected Grass: ").append(getGrassName(selectedGrassIndex));
            }
            mainTextArea.setText(invText.toString().trim());
        }

        if (hasAnyGrass()) {
            setChoices("Go back", "Eat Grass", "<", ">");
        } else {
            setChoices("Go back", "", "", "");
        }
    }

    private void eatGrass() {
        position = "inventory";
        ensureSelectedGrassAvailable();
        int heal = 0;
        String itemName = "";
        if (selectedGrassIndex == 3 && nghs >= 1) { heal = pHP; pHP = Math.min(pHP + heal, maxHP); nghs--; itemName = "Absolute Nature Glass"; }
        else if (selectedGrassIndex == 2 && ngh >= 1) { heal = 30; pHP = Math.min(pHP + heal, maxHP); ngh--; itemName = "Super Rare Nature Glass"; }
        else if (selectedGrassIndex == 1 && ngm >= 1) { heal = 15; pHP = Math.min(pHP + heal, maxHP); ngm--; itemName = "Rare Nature Glass"; }
        else if (selectedGrassIndex == 0 && ng >= 1) { heal = 5; pHP = Math.min(pHP + heal, maxHP); ng--; itemName = "Nature Glass"; }

        updateHUD();
        if (heal > 0) { SoundManager.playGulp(); mainTextArea.setText("You eat " + itemName + " and recover " + heal + " HP"); }
        else { mainTextArea.setText("No grass item to eat"); }
        ensureSelectedGrassAvailable();

        if (hasAnyGrass()) {
            setChoices("Go back", "Eat Grass", "<", ">");
        } else {
            setChoices("Go back", "", "", "");
        }
    }

    private boolean hasAnyGrass() { return ng >= 1 || ngm >= 1 || ngh >= 1 || nghs >= 1; }
    private boolean hasGrassByIndex(int idx) {
        return switch (idx) { case 0 -> ng >= 1; case 1 -> ngm >= 1; case 2 -> ngh >= 1; case 3 -> nghs >= 1; default -> false; };
    }
    private String getGrassName(int idx) {
        return switch (idx) { case 0 -> "Nature Glass"; case 1 -> "Rare Nature Glass"; case 2 -> "Super Rare Nature Glass"; case 3 -> "Absolute Nature Glass"; default -> "Nature Glass"; };
    }
    private void ensureSelectedGrassAvailable() {
        if (!hasAnyGrass()) { selectedGrassIndex = 0; return; }
        if (hasGrassByIndex(selectedGrassIndex)) return;
        for (int i = 0; i < 4; i++) { if (hasGrassByIndex(i)) { selectedGrassIndex = i; break; } }
    }
    private void grassLeft() {
        if (!hasAnyGrass()) return;
        for (int i = 0; i < 4; i++) { selectedGrassIndex = (selectedGrassIndex + 3) % 4; if (hasGrassByIndex(selectedGrassIndex)) break; }
        inventory();
    }
    private void grassRight() {
        if (!hasAnyGrass()) return;
        for (int i = 0; i < 4; i++) { selectedGrassIndex = (selectedGrassIndex + 1) % 4; if (hasGrassByIndex(selectedGrassIndex)) break; }
        inventory();
    }

    private void restoreFromInventoryShortcut() {
        if (inventoryReturnPosition.isEmpty()) return;
        position = inventoryReturnPosition;
        mainTextArea.setText(inventoryReturnText);
        setChoices(inventoryReturnChoices[0], inventoryReturnChoices[1], inventoryReturnChoices[2], inventoryReturnChoices[3]);
        inventoryReturnPosition = "";
        updateHUD();
        updateMonsterHUD();
    }

    // ========== Restart ==========

    private void restartGame() {
        pHP = 15; maxHP = 15; monHP = 25;
        monNameStr = "Monster"; monMaxDmg = 8;
        Wp = "Knife"; wpDurability = 0; wpMaxDurability = 0;
        playerD = 0; monD = 0;
        ng = 0; ngm = 0; ngh = 0; nghs = 0;
        vsword = 0; osword = 0; sword = 0; dsword = 0; sRing = 0;
        selectedGrassIndex = 0;
        vertigoStacks = 0;
        slipperyStacks = 0;
        slipperyActionCount = 0;
        prevp = "";
        inventoryReturnPosition = "";
        settingReturnPosition = "";
        invButton.setVisible(true);
        settingButton.setVisible(true);
        updateHUD();
        tg();
    }
}
