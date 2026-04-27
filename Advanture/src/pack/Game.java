package pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class Game {
	
	
	
	JFrame gwindow;
	Container con;
	JPanel tNPanel, mTPanel, cBPanel, pPanel, monPanel;
	//tNPanel(title Name Panel)
	//mTPanel(main Text Panel), cBPanel(choice Button Panel), pPanel(player Panel)
	//monPanel(monster HUD Panel)
	JLabel tNLabel, hLabel,hNLabel,wLabel, wNLabel, resLabel;
	//tNLabel(title name Label), hLabel(HP Label),hNLabel(HP Name Label),wLabel(Weapon Label )
	//wNLabel(Weapon Number Label), resLabel()
	Font tFont = new Font("Times New Roman", Font.PLAIN, 90);//title Font
	Font nFont = new Font("Times New Roman", Font.PLAIN, 30);//normal Font
	Font sFont = new Font("Times New Roman", Font.PLAIN, 40);//start Font
	AnimatedChoiceButton sButton;
	AnimatedChoiceButton c1,c2,c3,c4,invButton,settingButton;
	JTextArea mTArea;//main text area
	JLabel monNameLabel, monHPLabel;
	JProgressBar monHPBar;
	TitleScreenHandler TSHandler = new TitleScreenHandler();
	ChoiceHandler cHandler = new ChoiceHandler();
	InventoryButtonHandler iHandler = new InventoryButtonHandler();
	SettingButtonHandler setHandler = new SettingButtonHandler();
	int pHP=15;//player HP
	int maxHP=15;//max HP
	String Wp;//weapon
	String position;
	int wpDurability=0;//current weapon durability
	int wpMaxDurability=0;//max durability for current weapon

	String prevp = "";
	String inventoryReturnPosition = "";
	String inventoryReturnText = "";
	String inventoryReturnC1 = "";
	String inventoryReturnC2 = "";
	String inventoryReturnC3 = "";
	String inventoryReturnC4 = "";
	int selectedGrassIndex = 0;
	int playerD,monD,ng;//nature glass
	int ngm;//rare nature glass
	int ngh;//super rare nature glass
	int nghs;//absolute nature glass
	int vsword,osword,sword,dsword,sRing;
	int monHP=25;
	String monName="Monster";//current enemy name
	int monMaxDmg=8;//current enemy max damage
	boolean isFullscreen = false;
	
	
	
	public static void main(String[] args) {
		new Game();
		
	}
	public Game() {
		
		gwindow = new JFrame();
		gwindow.setSize(1000, 680);
		gwindow.setMinimumSize(new Dimension(800, 500));
		gwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gwindow.getContentPane().setBackground(Color.black);
		gwindow.setLayout(null);
		con = gwindow.getContentPane();
		
		tNPanel = new JPanel();
		tNPanel.setBounds(120,120,720,180);
		tNPanel.setBackground(Color.black);
		tNLabel =new JLabel("ADVANTURE");
		tNLabel.setForeground(Color.white);
		tNLabel.setFont(tFont);
		
		sButton = new AnimatedChoiceButton();
		sButton.setText("START");
		sButton.setBackground(Color.white);
		sButton.setForeground(Color.black);
		sButton.setFont(sFont);
		sButton.setButtonColors(Color.white, Color.black, Color.white);
		sButton.addActionListener(TSHandler);
		sButton.setFocusPainted(false);
		
		tNPanel.add(tNLabel);
		con.add(sButton);
		con.add(tNPanel);

		gwindow.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateLayout();
			}
		});

		updateLayout();
		gwindow.revalidate();
		gwindow.repaint();
		gwindow.setVisible(true);
		
		
		
	}

	public void updateLayout() {
		int winW = gwindow.getContentPane().getWidth();
		int winH = gwindow.getContentPane().getHeight();
		if(winW<=0 || winH<=0) {
			return;
		}

		int mainW = (int)(winW * 0.76);
		int mainX = (winW - mainW) / 2;
		int titleY = (int)(winH * 0.18);
		int titleH = (int)(winH * 0.26);
		int mainY = (int)(winH * 0.18);
		int mainH = (int)(winH * 0.40);
		int choiceW = (int)(winW * 0.42);
		int choiceH = (int)(winH * 0.27);
		int cellSizeForLayout = Math.max(30, choiceH / 4);
		int gapForLayout = Math.max(12, winW / 80);
		int maxChoiceW = winW - cellSizeForLayout - gapForLayout - 80;
		if(choiceW > maxChoiceW) {
			choiceW = maxChoiceW;
		}
		int choiceX = (winW - choiceW) / 2;
		int choiceY = (int)(winH * 0.64);
		int startW = (int)(winW * 0.168);
		int startH = (int)(winH * 0.126);
		int startX = (winW - startW) / 2;
		int startY = (int)(winH * 0.74);
		int playerY = (int)(winH * 0.03);
		int playerH = (int)(winH * 0.09);

		if(tNPanel!=null) tNPanel.setBounds(mainX, titleY, mainW, titleH);
		if(sButton!=null && sButton.getParent()==con) sButton.setBounds(startX, startY, startW, startH);
		if(mTPanel!=null) mTPanel.setBounds(mainX, mainY, mainW, mainH);
		if(cBPanel!=null) cBPanel.setBounds(choiceX, choiceY, choiceW, choiceH);
		if(pPanel!=null) pPanel.setBounds(mainX, playerY, mainW, playerH);

		// Monster HUD layout - positioned below player panel, right-aligned
		if(monPanel!=null) {
			int monW = Math.min((int)(winW * 0.35), 340);
			int monH = (int)(winH * 0.07);
			int monX = winW - monW - mainX;
			int monY = playerY + playerH + 4;
			monPanel.setBounds(monX, monY, monW, monH);
			int nameW = monW / 3;
			int barW = monW - nameW - 8;
			if(monNameLabel!=null) {
				monNameLabel.setBounds(4, 0, nameW, monH);
			}
			if(monHPBar!=null) {
				monHPBar.setBounds(nameW + 4, monH / 4, barW * 2 / 3, monH / 2);
			}
			if(monHPLabel!=null) {
				monHPLabel.setBounds(nameW + 4 + barW * 2 / 3 + 4, 0, barW / 3, monH);
			}
		}

		int dynamicNormal = Math.max(16, Math.min(36, winW / 32));
		int dynamicTitle = Math.max(40, Math.min(120, winW / 11));
		int dynamicStart = Math.max(14, Math.min(startH * 2 / 3, Math.min(startW / 5, winW / 22)));
		nFont = new Font("Times New Roman", Font.PLAIN, dynamicNormal);
		tFont = new Font("Times New Roman", Font.PLAIN, dynamicTitle);
		sFont = new Font("Times New Roman", Font.PLAIN, dynamicStart);
		if(tNLabel!=null) tNLabel.setFont(tFont);
		if(mTArea!=null) mTArea.setFont(nFont);
		if(c1!=null) c1.setFont(nFont);
		if(c2!=null) c2.setFont(nFont);
		if(c3!=null) c3.setFont(nFont);
		if(c4!=null) c4.setFont(nFont);
		if(hLabel!=null) hLabel.setFont(nFont);
		if(hNLabel!=null) hNLabel.setFont(nFont);
		if(wLabel!=null) wLabel.setFont(nFont);
		if(wNLabel!=null) wNLabel.setFont(nFont);
		if(pPanel!=null && hLabel!=null && hNLabel!=null && wLabel!=null && wNLabel!=null) {
			int pW = pPanel.getWidth();
			int pH = pPanel.getHeight();
			int midY = Math.max(2, pH / 2 - dynamicNormal / 2);
			hLabel.setBounds(6, midY, Math.max(80, pW / 8), dynamicNormal + 10);
			hNLabel.setBounds(Math.max(90, pW / 8), midY, Math.max(90, pW / 10), dynamicNormal + 10);
			wLabel.setBounds(Math.max(200, pW / 3), midY, Math.max(170, pW / 5), dynamicNormal + 10);
			wNLabel.setBounds(Math.max(380, pW / 2), midY, Math.max(280, pW / 3), dynamicNormal + 10);
		}
		if(sButton!=null) sButton.setFont(sFont);

		if(invButton!=null && cBPanel!=null) {
			int cellSize = cellSizeForLayout;
			int gap = gapForLayout;
			int invX = choiceX + choiceW + gap;
			if(invX + cellSize > winW - 20) {
				invX = winW - cellSize - 20;
			}
			invButton.setBounds(invX, choiceY, cellSize, cellSize);
			updateInventoryButtonIcon(cellSize);
		}

		if(settingButton!=null && pPanel!=null) {
			int setSize = Math.max(24, playerH - 6);
			int setX = 10;
			int setY = 10;
			settingButton.setBounds(setX, setY, setSize, setSize);
			updateSettingButtonIcon(setSize);
		}

		con.revalidate();
		con.repaint();
	}

	public void updateMonsterHUD() {
		boolean inCombat = position != null && 
			(position.equals("fight") || position.equals("attack") || 
			 position.equals("defend") || position.equals("monatt"));
		if(monPanel != null) {
			monPanel.setVisible(inCombat);
			if(inCombat) {
				monNameLabel.setText(monName);
				int maxMon = monName.equals("Orc") ? 50 : monName.equals("Goblin") ? 30 : 15;
				monHPBar.setMaximum(maxMon);
				monHPBar.setValue(Math.max(0, monHP));
				monHPLabel.setText(Math.max(0, monHP) + " / " + maxMon);
			}
		}
	}

	public void createGameScreen() {
		
		tNPanel.setVisible(false);
		sButton.setVisible(false);
		
		mTPanel = new JPanel();
		mTPanel.setBounds(120,120,720,300);
		mTPanel.setBackground(Color.black);
		mTPanel.setLayout(new BorderLayout());
		con.add(mTPanel);
		
		mTArea = new TypewriterTextArea();
		mTArea.setBounds(120,120, 720,300);
		mTArea.setBackground(Color.black);
		mTArea.setForeground(Color.white);
		mTArea.setFont(nFont);
		mTArea.setLineWrap(true);
		mTArea.setWrapStyleWord(true);
		mTArea.setBorder(new EmptyBorder(8, 10, 8, 10));
		mTArea.setEditable(false);
		mTPanel.add(mTArea, BorderLayout.CENTER);
		
		cBPanel = new JPanel();
		cBPanel.setBounds(300,420,360,180);
		cBPanel.setBackground(Color.black);
		cBPanel.setLayout(new GridLayout(4,1,0,8));
		
		con.add(cBPanel);

		c1 = new AnimatedChoiceButton();
		c1.setBackground(Color.black);
		c1.setForeground(Color.white);
		c1.setFont(nFont);
		c1.setFocusPainted(false);
		c1.addActionListener(cHandler);
		c1.setActionCommand("cho1");
		cBPanel.add(c1);
		
		c2 = new AnimatedChoiceButton();
		c2.setBackground(Color.black);
		c2.setForeground(Color.white);
		c2.setFont(nFont);
		c2.setFocusPainted(false);
		c2.addActionListener(cHandler);
		c2.setActionCommand("cho2");
		cBPanel.add(c2);
		
		c3 = new AnimatedChoiceButton();
		c3.setBackground(Color.black);
		c3.setForeground(Color.white);
		c3.setFont(nFont);
		c3.setFocusPainted(false);
		c3.addActionListener(cHandler);
		c3.setActionCommand("cho3");
		cBPanel.add(c3);

		c4 = new AnimatedChoiceButton();
		c4.setBackground(Color.black);
		c4.setForeground(Color.white);
		c4.setFont(nFont);
		c4.setFocusPainted(false);
		c4.addActionListener(cHandler);
		c4.setActionCommand("cho4");
		cBPanel.add(c4);

		invButton = new AnimatedChoiceButton();
		invButton.setBackground(Color.black);
		invButton.setForeground(Color.white);
		invButton.setFont(nFont);
		invButton.setFocusPainted(false);
		invButton.setBorderPainted(false);
		invButton.setContentAreaFilled(false);
		invButton.addActionListener(iHandler);
		con.add(invButton);
		invButton.setVisible(true);
		invButton.setEnabled(true);

		settingButton = new AnimatedChoiceButton();
		settingButton.setBackground(Color.black);
		settingButton.setForeground(Color.white);
		settingButton.setFont(nFont);
		settingButton.setFocusPainted(false);
		settingButton.setBorderPainted(false);
		settingButton.setContentAreaFilled(false);
		settingButton.addActionListener(setHandler);
		con.add(settingButton);
		settingButton.setVisible(true);
		settingButton.setEnabled(true);
		
		pPanel = new JPanel();
		pPanel.setBounds(120,18,720,60);
		pPanel.setBackground(Color.black);
		pPanel.setLayout(null);
		con.add(pPanel);
		hLabel = new JLabel("HP : ");
		hLabel.setFont(nFont);
		hLabel.setForeground(Color.white);
		pPanel.add(hLabel);
		
		hNLabel = new JLabel();
		hNLabel.setFont(nFont);
		hNLabel.setForeground(Color.white);
		pPanel.add(hNLabel);
		
		wLabel = new JLabel("Weapon : ");
		wLabel.setFont(nFont);
		wLabel.setForeground(Color.white);
		pPanel.add(wLabel);
		
		wNLabel = new JLabel();
		wNLabel.setFont(nFont);
		wNLabel.setForeground(Color.white);
		pPanel.add(wNLabel);

		// Monster HUD
		monPanel = new JPanel();
		monPanel.setBounds(120, 130, 360, 50);
		monPanel.setBackground(new Color(20, 20, 20, 200));
		monPanel.setLayout(null);
		monPanel.setVisible(false);
		con.add(monPanel);

		monNameLabel = new JLabel("Monster");
		monNameLabel.setFont(nFont);
		monNameLabel.setForeground(new Color(255, 80, 80));
		monPanel.add(monNameLabel);

		monHPBar = new JProgressBar(0, 100);
		monHPBar.setValue(100);
		monHPBar.setStringPainted(false);
		monHPBar.setBackground(new Color(60, 20, 20));
		monHPBar.setForeground(new Color(220, 50, 50));
		monHPBar.setBorderPainted(false);
		monPanel.add(monHPBar);

		monHPLabel = new JLabel("");
		monHPLabel.setFont(nFont);
		monHPLabel.setForeground(Color.white);
		monPanel.add(monHPLabel);

		updateLayout();
		playChoicePopup();
		sButton.playPopup();
		
		player();		
	}

	public void playChoicePopup() {
		updateChoiceButtonState(c1);
		updateChoiceButtonState(c2);
		updateChoiceButtonState(c3);
		updateChoiceButtonState(c4);
		if(c1!=null && c1.isVisible()) c1.playPopup();
		if(c2!=null && c2.isVisible()) c2.playPopup();
		if(c3!=null && c3.isVisible()) c3.playPopup();
		if(c4!=null && c4.isVisible()) c4.playPopup();
	}

	public void updateChoiceButtonState(AnimatedChoiceButton button) {
		if(button==null) {
			return;
		}
		if("death".equals(position) || "end".equals(position)) {
			if(button==c1) {
				button.setVisible(true);
				button.setEnabled(true);
				button.setButtonColors(new Color(18, 18, 18), Color.white, new Color(220, 220, 220));
			}else {
				button.setVisible(false);
				button.setEnabled(false);
			}
			return;
		}

		button.setVisible(true);
		String txt = button.getText()==null ? "" : button.getText().trim();

		// Keep Silver Ring requirement styling intact in the guard requirement scene.
		if(button==c1 && "how".equals(position) && sRing!=1) {
			button.setEnabled(false);
			button.setButtonColors(new Color(18, 18, 18, 128), new Color(255, 255, 255, 128), new Color(220, 220, 220, 128));
			return;
		}

		if(txt.length()==0) {
			button.setEnabled(false);
			button.setButtonColors(new Color(18, 18, 18, 0), new Color(255, 255, 255, 0), new Color(220, 220, 220, 0));
		}else if(txt.length()<2) {
			if(txt.equals("<") || txt.equals(">")) {
				button.setEnabled(true);
				button.setButtonColors(new Color(18, 18, 18), Color.white, new Color(220, 220, 220));
			}else {
				button.setEnabled(false);
				button.setButtonColors(new Color(18, 18, 18, 0), new Color(255, 255, 255, 0), new Color(220, 220, 220, 0));
			}
		}else {
			button.setEnabled(true);
			button.setButtonColors(new Color(18, 18, 18), Color.white, new Color(220, 220, 220));
		}
	}

	public void updateInventoryButtonIcon(int buttonSize) {
		int iconSize = Math.max(18, buttonSize - 8);
		invButton.setIcon(new BagIcon(iconSize, Color.white));
		invButton.setText("");
	}

	public void updateSettingButtonIcon(int buttonSize) {
		int iconSize = Math.max(16, buttonSize - 8);
		settingButton.setIcon(new GearIcon(iconSize, Color.white));
		settingButton.setText("");
	}

	public void openInventoryShortcut() {
		if(position==null || position.equals("inventory")) {
			return;
		}

		inventoryReturnPosition = position;
		inventoryReturnText = mTArea.getText();
		inventoryReturnC1 = c1.getText();
		inventoryReturnC2 = c2.getText();
		inventoryReturnC3 = c3.getText();
		inventoryReturnC4 = c4.getText();
		prevp = "shortcutInventory";
		inventory();
		playChoicePopup();
	}

	public void restoreFromInventoryShortcut() {
		if(inventoryReturnPosition.equals("")) {
			return;
		}
		position = inventoryReturnPosition;
		if(mTArea instanceof TypewriterTextArea) {
			((TypewriterTextArea)mTArea).setImmediateText(inventoryReturnText);
		}else {
			mTArea.setText(inventoryReturnText);
		}
		c1.setText(inventoryReturnC1);
		c2.setText(inventoryReturnC2);
		c3.setText(inventoryReturnC3);
		c4.setText(inventoryReturnC4);
		inventoryReturnPosition = "";
	}
	public void player(){
		
		Wp="Knife";
		
		setWeaponLabel();
		hNLabel.setText("" + pHP);
		
		tg();
		playChoicePopup();
	}

	public void setWeaponLabel() {
		String showName = Wp;
		if(Wp.equals("Void Nature Glass Sword")) {
			showName = "Void NG Sword";
		} else if(Wp.equals("Dynian Sword")) {
			showName = "Dynian Sword";
		} else if(Wp.equals("Steel Sword")) {
			showName = "Steel Sword";
		} else if(Wp.equals("Old Sword")) {
			showName = "Old Sword";
		}
		if(wpMaxDurability > 0) {
			showName += " (" + wpDurability + "/" + wpMaxDurability + ")";
		}
		wNLabel.setText(showName);
	}
	public void tg() {//townGate
		position ="tg";
		mTArea.setText("You are at the Gate of the Town. \nA Guard is standing in front of you. \n\nWhat do you do?");
		c1.setText("Talk to the Guard");
		c2.setText("Attack the Guard");
		c3.setText("Leave");
		c4.setText("");
		
	}	
	public void setting() {
		position ="setting";
		mTArea.setText("MENU");
		c1.setText("Status");
		c2.setText(isFullscreen ? "Windowed" : "Fullscreen");
		c3.setText("Go back");
		c4.setText("");
		
	}
	public void toggleFullscreen() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if(!isFullscreen) {
			gwindow.dispose();
			gwindow.setUndecorated(true);
			gwindow.setVisible(true);
			gwindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
			isFullscreen = true;
		} else {
			gwindow.dispose();
			gwindow.setUndecorated(false);
			gwindow.setExtendedState(JFrame.NORMAL);
			gwindow.setSize(1000, 680);
			gwindow.setLocationRelativeTo(null);
			gwindow.setVisible(true);
			isFullscreen = false;
		}
		updateLayout();
		setting();
		playChoicePopup();
	}
	public void inventory() {//townGate
		position ="inventory";
		ensureSelectedGrassAvailable();
		StringBuilder invText = new StringBuilder();
		if(ng>=1) {
			invText.append("Nature Glass Lower-Grade x" + ng + "\n");
		}
		if(ngm>=1) {
			invText.append("Nature Glass Medium-Grade x" + ngm + "\n");
		}
		if(ngh>=1) {
			invText.append("Super Rare Nature Glass High-Grade x" + ngh + "\n");
		}
		if(nghs>=1) {
			invText.append("Absolute Nature Glass Highest-Grade x" + nghs + "\n");
		}
		position ="inventory";
		if(vsword>=1) {
			invText.append("Void Nature Glass Sword\n");
		}
		if(dsword>=1) {
			invText.append("Dynian Sword\n");
		}
		if(sword>=1) {
			invText.append("Steel Sword\n");
		}
		if(osword>=1) {
			invText.append("Old Sword\n");
		}
		if(invText.length()==0) {
			mTArea.setText("Inventory is empty");
		}else {
			if(hasAnyGrass()) {
				invText.append("\nSelected Grass: ").append(getGrassName(selectedGrassIndex));
			}
			mTArea.setText(invText.toString().trim());
		}
		
		
		
		
		
		c1.setText("Go back");
		if(ng>=1 || ngm>=1 || ngh>=1 || nghs>=1) {
			c2.setText("Eat Grass");
			c3.setText("<");
			c4.setText(">");
		}else {
			c2.setText("");
			c3.setText("");
			c4.setText("");
		}
		
	}	

	public void eatGrass() {
		position = "inventory";
		ensureSelectedGrassAvailable();
		int heal = 0;
		String itemName = "";
		if(selectedGrassIndex==3 && nghs>=1) {
			int baseHP = pHP;
			heal = baseHP;
			pHP = Math.min(pHP + heal, maxHP);
			nghs--;
			itemName = "Absolute Nature Glass";
		} else if(selectedGrassIndex==2 && ngh>=1) {
			heal = 30;
			pHP = Math.min(pHP + heal, maxHP);
			ngh--;
			itemName = "Super Rare Nature Glass";
		} else if(selectedGrassIndex==1 && ngm>=1) {
			heal = 15;
			pHP = Math.min(pHP + heal, maxHP);
			ngm--;
			itemName = "Rare Nature Glass";
		} else if(selectedGrassIndex==0 && ng>=1) {
			heal = 5;
			pHP = Math.min(pHP + heal, maxHP);
			ng--;
			itemName = "Nature Glass";
		}

		hNLabel.setText("" + pHP);
		if(heal>0) {
			SoundManager.playGulp();
			mTArea.setText("You eat " + itemName + " and recover " + heal + " HP");
		} else {
			mTArea.setText("No grass item to eat");
		}
		ensureSelectedGrassAvailable();

		c1.setText("Go back");
		if(ng>=1 || ngm>=1 || ngh>=1 || nghs>=1) {
			c2.setText("Eat Grass");
			c3.setText("<");
			c4.setText(">");
		}else {
			c2.setText("");
			c3.setText("");
			c4.setText("");
		}
	}

	public boolean hasAnyGrass() {
		return ng>=1 || ngm>=1 || ngh>=1 || nghs>=1;
	}

	public boolean hasGrassByIndex(int idx) {
		if(idx==0) return ng>=1;
		if(idx==1) return ngm>=1;
		if(idx==2) return ngh>=1;
		if(idx==3) return nghs>=1;
		return false;
	}

	public String getGrassName(int idx) {
		if(idx==0) return "Nature Glass";
		if(idx==1) return "Rare Nature Glass";
		if(idx==2) return "Super Rare Nature Glass";
		if(idx==3) return "Absolute Nature Glass";
		return "Nature Glass";
	}

	public void ensureSelectedGrassAvailable() {
		if(!hasAnyGrass()) {
			selectedGrassIndex = 0;
			return;
		}
		if(hasGrassByIndex(selectedGrassIndex)) {
			return;
		}
		for(int i=0;i<4;i++) {
			if(hasGrassByIndex(i)) {
				selectedGrassIndex = i;
				break;
			}
		}
	}

	public void grassLeft() {
		if(!hasAnyGrass()) {
			return;
		}
		for(int i=0;i<4;i++) {
			selectedGrassIndex = (selectedGrassIndex + 3) % 4;
			if(hasGrassByIndex(selectedGrassIndex)) {
				break;
			}
		}
		updateInventorySelectionOnly();
	}

	public void grassRight() {
		if(!hasAnyGrass()) {
			return;
		}
		for(int i=0;i<4;i++) {
			selectedGrassIndex = (selectedGrassIndex + 1) % 4;
			if(hasGrassByIndex(selectedGrassIndex)) {
				break;
			}
		}
		updateInventorySelectionOnly();
	}

	public void updateInventorySelectionOnly() {
		String currentText = mTArea.getText();
		String selectedLine = "Selected Grass: " + getGrassName(selectedGrassIndex);
		String updatedText;
		if(currentText.contains("Selected Grass:")) {
			updatedText = currentText.replaceAll("Selected Grass:.*", selectedLine);
		} else {
			updatedText = currentText + "\n\n" + selectedLine;
		}
		if(mTArea instanceof TypewriterTextArea) {
			((TypewriterTextArea)mTArea).setImmediateText(updatedText);
		}else {
			mTArea.setText(updatedText);
		}
	}
	
	public void talkGuard(){
		position ="talkGuard";//talk guard
		mTArea.setText("Guard : Hello stranger. i have never seen your face\n i'm sorry but we cannot let stranger enter our town");
		c1.setText("How to enter Town?");
		c2.setText("Go back");
		c3.setText("Crossroad");
		c4.setText("");
		
		
	}
	public void attg() {
		position ="attg";//talk guard
		pHP=pHP-6;
		hNLabel.setText("" + pHP);
		mTArea.setText("You are attacking the Guard\n\nMISS\n\nGuard : Who the fuck are you?\nGuard attack you and dealt 6 Damage");
		
		
		c1.setText("I'm sorry");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}
	public void how(){
		position ="how";//talk guard
		mTArea.setText("Guard : You need to kill the Orc on\n the East and collect the Silver Ring");
		c1.setText("I have the Silver Ring");
		c2.setText("Go back");
		c3.setText("Crossroad");
		c4.setText("");
		if(sRing==1) {
			c1.setEnabled(true);
			c1.setButtonColors(new Color(18, 18, 18), Color.white, new Color(220, 220, 220));
		}else {
			c1.setEnabled(false);
			c1.setButtonColors(new Color(18, 18, 18, 128), new Color(255, 255, 255, 128), new Color(220, 220, 220, 128));
		}
		
		
	}
	public void crossRoad() {
		position ="crossRoad";//talk guard
		mTArea.setText("You are in the crossroad\n if You go to the West, You wil go back to the TownGate");
		c1.setText("North");
		c2.setText("South");
		c3.setText("West");
		c4.setText("East");
		
	}
	public void north() {
		position ="north";//talk guard
		mTArea.setText("You are in the North\n There is a river near you");
		c1.setText("Drink it");
		c2.setText("Let's not");
		c3.setText("Rest");
		c4.setText("Stay");
		
	}
	public void LAround() {
		position ="LAround";//talk guard
		mTArea.setText("You are in the North\n There is a river you left before");
		c1.setText("Drink it");
		c2.setText("Let's not");
		c3.setText("Rest");
		c4.setText("");
		
	}
	public void rest() {
		position ="rest";//talk guard
		pHP = Math.min(pHP + 2, maxHP);
		hNLabel.setText("" + pHP);
		mTArea.setText("You sit and take a little break!\n\nHP + 2");
		
		
		c1.setText("Go back to Crossroad");
		c2.setText("Look Around");
		c3.setText("");
		c4.setText("");
		
	}
	
	public void drink() {
		position ="drink";//talk guard
		pHP = Math.min(pHP + 5, maxHP);
		hNLabel.setText("" + pHP);
		SoundManager.playGulp();
		mTArea.setText("You drink River Water and feel refreshed!\n\nHP + 5");
		
		
		c1.setText("Go back to Crossroad");
		c2.setText("Look Around");
		c3.setText("Rest");
		c4.setText("");
		
	}
	public void south() {
		position ="south";
		mTArea.setText("You are at the South\n South is place for some Advanture \nCollecting Herb \n");
		c1.setText("Collect Herb");
		c2.setText("Search random item (?)");
		c3.setText("Train (+Max HP)");
		c4.setText("Go back");
		
	}
	public void trainMaxHP() {
		position ="trainHP";
		int gain = 3 + new java.util.Random().nextInt(5);
		maxHP += gain;
		pHP = maxHP;
		hNLabel.setText("" + pHP);
		mTArea.setText("You train hard and push your limits!\n\nMax HP + " + gain + "\nMax HP is now " + maxHP + "\nHP restored to " + pHP);
		c1.setText("Go back");
		c2.setText("Crossroad");
		c3.setText("");
		c4.setText("");
	}
	public void southLookAround() {
		position = "southLAround";
		mTArea.setText("You look around in the South.\n\n"
				+ "Grass Drop Chance          Weapon Drop Chance\n"
				+ "Absolute Nature: 2%        Void NG Sword: 2%\n"
				+ "Super Rare Nature: 28%     Dynian Sword: 28%\n"
				+ "Rare Nature: 20%           Steel Sword: 20%\n"
				+ "Nature: 50%                Old Sword: 50%");
		c1.setText("Collect Herb");
		c2.setText("Search random item (?)");
		c3.setText("Train (+Max HP)");
		c4.setText("Go back");
	}
	public void sr() {
		position ="sr";
		double chan = Math.random();
		if(chan<0.02) {
			vsword = 1;
			Wp="Void Nature Glass Sword";
			wpDurability=50; wpMaxDurability=50;
			mTArea.setText("You get a Void Nature Glass Sword! (Durability: 50)");
			setWeaponLabel();
		}else if(chan<0.3) {
			dsword =1;
			Wp="Dynian Sword";
			wpDurability=20; wpMaxDurability=20;
			setWeaponLabel();
			mTArea.setText("You get a Dynian Sword! (Durability: 20)");
		}else if(chan<0.5) {
			sword=1;
			Wp="Steel Sword";
			wpDurability=15; wpMaxDurability=15;
			setWeaponLabel();
			mTArea.setText("You get a Steel Sword! (Durability: 15)");
		}else{
			osword=1;
			Wp="Old Sword";
			wpDurability=10; wpMaxDurability=10;
			setWeaponLabel();
			mTArea.setText("You get a Old Sword! (Durability: 10)");
		}
		c1.setText("Collect Herb");
		c2.setText("Rest");
		c3.setText("Go back");
		c4.setText("Crossroad");
		
	}
	public void CHerb() {
		position ="CHerb";//talk guard
		
		double chan = Math.random();
		if(chan<0.02) {
			nghs++;
			mTArea.setText("You collect a Absolute Nature Glass Highest Grade");
		}else if(chan<0.3) {
			ngh++;
			mTArea.setText("You collect a Super Rare Nature Glass High-Grade");
		}else if(chan<0.5) {
			ngm++;
			mTArea.setText("You collect a Rare Nature Glass Medium-Grade");
		}else{
			ng++;
			mTArea.setText("You collect a Nature Glass Low-Grade");
		}
		
			
		c1.setText("Go back");
		c2.setText("Rest");
		c3.setText("Crossroad");
		c4.setText("");
		
	}
	public void status() {
		position ="status";
		String durText = wpMaxDurability > 0 ? wpDurability + "/" + wpMaxDurability : "N/A";
		mTArea.setText("=== STATUS ===\n"
			+ "HP: " + pHP + " / " + maxHP + "\n"
			+ "Weapon: " + Wp + "\n"
			+ "Durability: " + durText + "\n"
			+ "Silver Ring: " + (sRing==1 ? "Yes" : "No"));
		c1.setText("Go back");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}	
	public void fight() {
		if(monHP<1) {
			monHP = 25;
			monName = "Monster";
			monMaxDmg = 8;
		}
		position ="fight";
		mTArea.setText("You choose to fight the " + monName + "\n" + monName + " HP: "+monHP);
		updateMonsterHUD();
		
		c1.setText("Attack");
		c2.setText("Defend");
		c3.setText("Run");
		c4.setText("");
		
	}
	public void defend() {
		position ="defend";
		playerD=0;
		monD=0;
		monD = new java.util.Random().nextInt(Math.max(1, monMaxDmg / 3));
		pHP=pHP-monD;
		hNLabel.setText(""+pHP);
		SoundManager.playHit();
		mTArea.setText("You choose to defend the " + monName + " attack\nDefend UP 1 Turn\n" + monName + " attack You and dealt "+monD+" Damage!");
		updateMonsterHUD();
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}
	
	public void attack() {
		playerD=0;
		position ="attack";
		SoundManager.playSlash();
		if (Wp.equals("Dynian Sword")) {
			playerD = 18 + new java.util.Random().nextInt(15);
		} else if(Wp.equals("Void Nature Glass Sword")) {
			playerD = 30 + new java.util.Random().nextInt(26);
		} else if(Wp.equals("Steel Sword")) {
			playerD = 10 + new java.util.Random().nextInt(9);
		} else if(Wp.equals("Old Sword")) {
			playerD = 6 + new java.util.Random().nextInt(7);
		}else {
			playerD = 2 + new java.util.Random().nextInt(5);
		}
		// Weapon durability
		String breakMsg = "";
		if(wpMaxDurability > 0) {
			wpDurability--;
			if(wpDurability <= 0) {
				breakMsg = "\n\nYour " + Wp + " broke!";
				Wp = "Knife";
				wpDurability = 0;
				wpMaxDurability = 0;
			}
			setWeaponLabel();
		}
		monHP = monHP-playerD;
		mTArea.setText("You attack the " + monName + " and dealt "+playerD+" Damage!\n" + monName + " HP : "+monHP + breakMsg);
		updateMonsterHUD();
		
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}
	public void monatt() {
		position ="monatt";
		monD=0;
		monD = new java.util.Random().nextInt(Math.max(1, monMaxDmg));
		pHP=pHP-monD;
		SoundManager.playHit();
		mTArea.setText(monName + " attack You and dealt "+monD+" Damage!\n" + monName + " HP : "+monHP);
		hNLabel.setText(""+pHP);
		updateMonsterHUD();
		if(pHP<=0) {
			death();
		}
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		}
	
	
	public void east() {
		position ="east";
		// Random enemy encounter
		double roll = Math.random();
		if(roll < 0.10) {
			monName = "Orc";
			monHP = 50;
			monMaxDmg = 14;
		} else if(roll < 0.40) {
			monName = "Goblin";
			monHP = 30;
			monMaxDmg = 9;
		} else {
			monName = "Slime";
			monHP = 15;
			monMaxDmg = 5;
		}
		mTArea.setText("You are at the East.\nA " + monName + " appears! (HP: " + monHP + ")");
		c1.setText("Fight");
		c2.setText("Rest");
		c3.setText("Run");
		c4.setText("");
		
	}

	public void death() {
		position ="death";
		SoundManager.playHit();
		updateMonsterHUD();
		mTArea.setText("Your HP 0\nYou Dead\n\n- GAME OVER -");
		c1.setText("Play Again");
		c1.setVisible(true);
		c1.setEnabled(true);
		c1.setButtonColors(new Color(18, 18, 18), Color.white, new Color(220, 220, 220));
		c2.setVisible(false);
		c3.setVisible(false);
		c4.setVisible(false);
		invButton.setEnabled(false);
		invButton.setVisible(false);
		settingButton.setEnabled(false);
		settingButton.setVisible(false);
		
	}
	public void win() {
		position ="win";
		SoundManager.playVictory();
		updateMonsterHUD();
		String dropMsg = "You killed the " + monName + "!";
		if(monName.equals("Orc")) {
			sRing=1;
			dropMsg += "\nYou obtain a Magic Tool: Silver Ring";
		} else if(monName.equals("Goblin")) {
			int hpGain = 3;
			maxHP += hpGain;
			pHP += hpGain;
			hNLabel.setText(""+pHP);
			dropMsg += "\nGoblin dropped a Vitality Stone! Max HP +" + hpGain;
		} else {
			int heal = 5;
			pHP = Math.min(pHP + heal, maxHP);
			hNLabel.setText(""+pHP);
			dropMsg += "\nSlime dropped Slime Gel! HP +" + heal;
		}
		mTArea.setText(dropMsg);
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
	}
	public void end() {
		position ="end";
		mTArea.setText("Eh? You already have Silver Ring?\nYou can go in to the town Advanture.\n\n- THE END -");
		
		c1.setText("Play Again");
		c1.setVisible(true);
		c1.setEnabled(true);
		c1.setButtonColors(new Color(18, 18, 18), Color.white, new Color(220, 220, 220));
		c2.setVisible(false);
		c3.setVisible(false);
		c4.setVisible(false);
		invButton.setEnabled(false);
		invButton.setVisible(false);
		settingButton.setEnabled(false);
		settingButton.setVisible(false);
		
	}
	public void restartGame() {
		pHP = 15;
		maxHP = 15;
		monHP = 25;
		monName = "Monster";
		monMaxDmg = 8;
		Wp = "Knife";
		wpDurability = 0;
		wpMaxDurability = 0;
		playerD = 0;
		monD = 0;
		ng = 0;
		ngm = 0;
		ngh = 0;
		nghs = 0;
		vsword = 0;
		osword = 0;
		sword = 0;
		dsword = 0;
		sRing = 0;
		selectedGrassIndex = 0;
		prevp = "";
		inventoryReturnPosition = "";
		inventoryReturnText = "";
		inventoryReturnC1 = "";
		inventoryReturnC2 = "";
		inventoryReturnC3 = "";
		inventoryReturnC4 = "";
		
		c1.setVisible(true);
		c2.setVisible(true);
		c3.setVisible(true);
		c4.setVisible(true);
		invButton.setEnabled(true);
		invButton.setVisible(true);
		settingButton.setEnabled(true);
		settingButton.setVisible(true);
		
		setWeaponLabel();
		hNLabel.setText("" + pHP);
		tg();
		playChoicePopup();
	}
		
	
		
	
	public class TitleScreenHandler implements ActionListener{
	
		public void actionPerformed(ActionEvent event){
			
			createGameScreen();
			
			
		}
	}
	public class InventoryButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			openInventoryShortcut();
		}
	}
	public class SettingButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(position!=null && !position.equals("end") && !position.equals("death")) {
				setting();
				playChoicePopup();
			}
		}
	}


	public class ChoiceHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			String yChoice = event.getActionCommand();
			SoundManager.playClick();
			
			
			switch(position) {
			case "tg"://towngate
				switch(yChoice) {
				case "cho1": talkGuard(); break;
				case "cho2": attg();break; 
				case "cho3": crossRoad();break;
				case "cho4": break;
				}
				break;
			case "talkGuard":
				switch(yChoice) {
				case "cho1": how(); break;
				case "cho2": tg(); break; 
				case "cho3": crossRoad();break;
				case "cho4":break;
				}
				break;
			case "fight":
				switch(yChoice) {
				case "cho1": 
					attack(); 
					prevp="attack";
					break;
				case "cho2": 
					defend(); 
					prevp="defend";
					break; 
				case "cho3": crossRoad();break;
				case "cho4":break;
				}
				break;
			case "attack":
				switch(yChoice) {
				case "cho1":
					if(monHP<1) {
						win();
					}else {
					
					monatt();
					}
				break;
				case "cho2":break; 
				case "cho3":break;
				case "cho4":break;
				}
			break;
			case "win":
				switch(yChoice) {
				case "cho1":east(); break;
				case "cho2":break; 
				case "cho3":break;
				case "cho4":break;
				}
			break;
			case "defend":
				switch(yChoice) {
				case "cho1": fight();break;
				case "cho2": break; 
				case "cho3": break;
				case "cho4": break;
				}
				break;
			case "monatt":
				switch(yChoice) {
				case "cho1": 
					if(pHP<1) {
						death();
					}else {
					fight();
					}
					break;
				case "cho2":break; 
				case "cho3":break;
				case "cho4":break;
				}
			
			
				break;
			case "how":
				switch(yChoice) {
				case "cho1": 
					
					if(sRing==1) {
						end();
					}
				break;
				case "cho2": talkGuard(); break; 
				case "cho3": crossRoad();break;
				case "cho4":break;
				}
				break;
			case "setting":
				switch(yChoice) {
				case "cho1": status(); break;
				case "cho2": toggleFullscreen(); break;
				case "cho3": tg();break;
				case "cho4": break;
				}
				break;

			case "crossRoad":
				switch(yChoice) {
				case "cho1": north();
				prevp="north";
				break;
				case "cho2": south(); 
				prevp="south";
				break;
				case "cho3": tg(); 
				prevp="tg";
				break;
				case "cho4": east(); 
				prevp="east";
				break;
				}
				break;
			case "attg":
				switch(yChoice) {
				case "cho1": 
					if(pHP<1) {
						death();
					}else{
						tg(); 
					}
					
					break;
				case "cho2": break; 
				case "cho3": break;
				case "cho4": break;
				}
				break;

			case "south":
				switch(yChoice) {
				case "cho1": CHerb(); break;
				case "cho2": sr();break; 
				case "cho3": trainMaxHP(); break;
				case "cho4": crossRoad();break;
				}
				break;

			case "east":
				switch(yChoice) {
				case "cho1": fight(); break;
				case "cho2": 
					rest();
					prevp="east";
					break; 
				case "cho3": crossRoad();break;
				case "cho4": break;
				}
				break;

			case "north":
				switch(yChoice) {
				case "cho1": drink(); break;
				case "cho2": LAround();break; 
				case "cho3": 
					rest();
					prevp="north";
				break;
				case "cho4": LAround(); break;
				}
				break;

			case "drink":
				switch(yChoice) {
				case "cho1": crossRoad(); break;
				case "cho2": LAround();break; 
				case "cho3": rest();break;
				case "cho4": break;
				}
				break;

			case "rest":
				switch(yChoice) {
				case "cho1": crossRoad(); break;
				case "cho2": 
					if(prevp.equals("east")) {
						east();
					}
					else if(prevp.equals("north")) {
						north();
					}
					else if(prevp.equals("south")) {
						southLookAround();
					}
				break; 
				case "cho3": break;
				case "cho4": break;
				}
				break;

			case "southLAround":
				switch(yChoice) {
				case "cho1": CHerb(); break;
				case "cho2": sr(); break;
				case "cho3": trainMaxHP(); break;
				case "cho4": crossRoad(); break;
				}
				break;

			case "trainHP":
				switch(yChoice) {
				case "cho1": south(); break;
				case "cho2": crossRoad(); break;
				case "cho3": break;
				case "cho4": break;
				}
				break;

			case "LAround":
				switch(yChoice) {
				case "cho1": drink(); break;
				case "cho2": north();break; 
				case "cho3": rest();break;
				case "cho4": break;
				}
				break;

			case "status":
				switch(yChoice) {
				case "cho1": setting(); break;
				case "cho2": break; 
				case "cho3": break;
				case "cho4": break;
				}
				break;
//			case "inventory":
//				switch(yChoice) {
//				case "cho1": break;
//				case "cho2": break; 
//				case "cho3": break;
//				case "cho4": break;
//				}
//				break;
				
			case "CHerb":
				switch(yChoice) {
				case "cho1": south(); break;
				case "cho2": 
					prevp ="south";
					rest(); 
					break; 
				case "cho3": crossRoad(); break;
				case "cho4": break;
				}
		break;
			case "sr":
				switch(yChoice) {
				case "cho1": CHerb(); break;
				case "cho2": 
					prevp ="south";
					rest(); 
					break; 
				case "cho3": south(); break; 
				case "cho4": crossRoad();break;
				}
		break;
			
				
				
		case "inventory":
			switch(yChoice) {
			case "cho1": 
				if(prevp.equals("CHerb")) {
					south(); 
				}else if(prevp.equals("setting")) {
					setting();
				}else if(prevp.equals("shortcutInventory")) {
					restoreFromInventoryShortcut();
				}
				break;
			
			case "cho2": 
				if(hasAnyGrass()) {
					eatGrass();
				}else {
					inventory();
				}
				break; 
			case "cho3": grassLeft(); break;
			case "cho4": grassRight(); break;
			}
			break;

			case "death":
			case "end":
				switch(yChoice) {
				case "cho1": restartGame(); break;
				case "cho2": break;
				case "cho3": break;
				case "cho4": break;
				}
				break;

			}
			playChoicePopup();
			
		}
	}
	
}
	


