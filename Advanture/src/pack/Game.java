package pack;

import java.awt.Color;


import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game {
	playersetup PS = new playersetup();
//	choice cc = new choice();
	
	
	
	JFrame gwindow;
	Container con;
	JPanel tNPanel, sBPanel, mTPanel, cBPanel, pPanel;
	//tNPanel(title Name Panel), sBPanel(Start button Panel) 
	//mTPanel(main Text Panel), cBPanel(choice Button Panel), pPanel(player Panel)
	JLabel tNLabel, hLabel,hNLabel,wLabel, wNLabel, resLabel;
	//tNLabel(title name Label), hLabel(HP Label),hNLabel(HP Name Label),wLabel(Weapon Label )
	//wNLabel(Weapon Number Label), resLabel()
	Font tFont = new Font("Times New Roman", Font.PLAIN, 90);//title Font
	Font nFont = new Font("Times New Roman", Font.PLAIN, 30);//normal Font
	JButton sButton,c1,c2,c3,c4;
	JTextArea mTArea;//main text area
	TitleScreenHandler TSHandler = new TitleScreenHandler();
	ChoiceHandler cHandler = new ChoiceHandler();
	int pHP=15;//player HP
	String Wp;//weapon
	String position;
	Scanner scan = new Scanner(System.in);
	String enter;
	String prevp = "";
	int playerD,monD,ng;//absolute nature glass
	int ngm;//super rare nature glass
	int ngh;//rare nature glass
	int nghs;//nature glass
	int vsword,osword,sword,dsword,sRing;
	int monHP=25;
	
	
	
	public static void main(String[] args) {
		new Game();
		
	}
	public Game() {
		
		gwindow = new JFrame();
		gwindow.setSize(1000, 680);
		gwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gwindow.getContentPane().setBackground(Color.black);
		gwindow.setLayout(null);
		gwindow.setVisible(true);
		con = gwindow.getContentPane();
		
		tNPanel = new JPanel();
		tNPanel.setBounds(120,120,720,180);
		tNPanel.setBackground(Color.black);
		tNLabel =new JLabel("ADVANTURE");
		tNLabel.setForeground(Color.white);
		tNLabel.setFont(tFont);
		
		sBPanel = new JPanel();
		sBPanel.setBounds(360,480,240,120);
		sBPanel.setBackground(Color.black);
		
		sButton = new JButton("START");
		sButton.setBackground(Color.black);
		sButton.setForeground(Color.white);
		sButton.setFont(nFont);
		sButton.addActionListener(TSHandler);
		sButton.setFocusPainted(false);
		
		tNPanel.add(tNLabel);
		sBPanel.add(sButton);
		con.add(tNPanel);
		con.add(sBPanel);
		
		
		
	}

	public void createGameScreen() {
		
		tNPanel.setVisible(false);
		sBPanel.setVisible(false);
		
		mTPanel = new JPanel();
		mTPanel.setBounds(120,120,720,300);
		mTPanel.setBackground(Color.black);
		con.add(mTPanel);
		
		mTArea = new JTextArea();
		mTArea.setBounds(120,120, 720,300);
		mTArea.setBackground(Color.black);
		mTArea.setForeground(Color.white);
		mTArea.setFont(nFont);
		mTArea.setLineWrap(true);
		mTPanel.add(mTArea);
		
		cBPanel = new JPanel();
		cBPanel.setBounds(300,420,360,180);
		cBPanel.setBackground(Color.black);
		cBPanel.setLayout(new GridLayout(4,1));
		
		con.add(cBPanel);

		c1 = new JButton();
		c1.setBackground(Color.black);
		c1.setForeground(Color.white);
		c1.setFont(nFont);
		c1.setFocusPainted(false);
		c1.addActionListener(cHandler);
		c1.setActionCommand("cho1");
		cBPanel.add(c1);
		
		c2 = new JButton();
		c2.setBackground(Color.black);
		c2.setForeground(Color.white);
		c2.setFont(nFont);
		c2.setFocusPainted(false);
		c2.addActionListener(cHandler);
		c2.setActionCommand("cho2");
		cBPanel.add(c2);
		
		c3 = new JButton();
		c3.setBackground(Color.black);
		c3.setForeground(Color.white);
		c3.setFont(nFont);
		c3.setFocusPainted(false);
		c3.addActionListener(cHandler);
		c3.setActionCommand("cho3");
		cBPanel.add(c3);

		c4 = new JButton();
		c4.setBackground(Color.black);
		c4.setForeground(Color.white);
		c4.setFont(nFont);
		c4.setFocusPainted(false);
		c4.addActionListener(cHandler);
		c4.setActionCommand("cho4");
		cBPanel.add(c4);
		
		pPanel = new JPanel();
		pPanel.setBounds(120,18,720,60);
		pPanel.setBackground(Color.black);
		pPanel.setLayout(new GridLayout(1,4));
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
		
		player();		
	}
	public void player(){
		
		Wp="Knife";
		
		wNLabel.setText(Wp);
		hNLabel.setText("" + pHP);
		
		tg();
	}
	public void tg() {//townGate
		position ="tg";
		mTArea.setText("You are at the Gate of the Town. \nA Guard is standing in front of you. \n\nWhat do you do?");
		c1.setText("Talk to the Guard");
		c2.setText("Attack the Guard");
		c3.setText("Leave");
		c4.setText("Setting");
		
	}	
	public void setting() {//townGate
		position ="setting";
		mTArea.setText("MENU");
		c1.setText("Status");
		c2.setText("Inventory");
		c3.setText("Go back");
		c4.setText("");
		
	}
	public void inventory() {//townGate
		position ="inventory";
		if(ng>=1) {
			mTArea.setText("Nature Glass Lower-Grade");
		}else {
			
		}
		if(ngm>=1) {
			mTArea.setText("Nature Glass Medium-Grade");
		}else {
			
		}
		if(ngh>=1) {
			mTArea.setText("Super Rare Nature Glass High-Grade");
		}else {
			
		}
		if(nghs>=1) {
			mTArea.setText("Absolute Nature Glass Highest-Grade");
		}else {
			
		}
		position ="inventory";
		if(vsword>=1) {
			mTArea.setText("Void Nature Glass Sword");
		}else {
			
		}
		if(dsword>=1) {
			mTArea.setText("Dynian Sword");
		}else {
			
		}
		if(sword>=1) {
			mTArea.setText("Steel Sword");
		}else {
			
		}
		if(osword>=1) {
			mTArea.setText("Old Sword");
		}else {
			
		}
		
		
		
		
		
		c1.setText("Go back");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}	
	
	public void talkGuard(){
		position ="talkGuard";//talk guard
		mTArea.setText("Guard : Hello stranger. i have never seen your face\n i'm sorry but we cannot let stranger enter our town");
		c1.setText("How to enter Town?");
		c2.setText("Go back");
		c3.setText("Crossroad");
		c4.setText("Setting");
		
		
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
		mTArea.setText("Guard : You need to kill monster on\n west and collect the Silver Ring");
		c1.setText(">");
		c2.setText("Go back");
		c3.setText("Crossroad");
		c4.setText("Setting");
		
		
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
		pHP=pHP+2;
		hNLabel.setText("" + pHP);
		mTArea.setText("You sit and take a little break!\n\nHP + 2");
		
		
		c1.setText("Go back to Crossroad");
		c2.setText("Look Around");
		c3.setText("");
		c4.setText("");
		
	}
	
	public void drink() {
		position ="drink";//talk guard
		pHP=pHP+5;
		hNLabel.setText("" + pHP);
		mTArea.setText("You drink River Water and feel refreshed!\n\nHP + 6");
		
		
		c1.setText("Go back to Crossroad");
		c2.setText("Look Around");
		c3.setText("Rest");
		c4.setText("");
		
	}
	public void south() {
		position ="south";//talk guard
		mTArea.setText("You are at the South\n South is place for some Advanture \nCollecting Herb \n");
		c1.setText("Collect Herb");
		c2.setText("Search random item (?)");
		c3.setText("Rest");
		c4.setText("Go back");
		
	}
	public void sr() {
		position ="sr";//talk guard
		double chan = Math.random();
		if(chan<0.02) {
			vsword = 1;
			mTArea.setText("You get a Void Nature Glass Sword");
			Wp="Void Nature Glass Sword";
			wNLabel.setText(Wp);
		}else if(chan<0.3) {
			dsword =1;
			Wp="Dynian Sword";
			wNLabel.setText(Wp);
			mTArea.setText("You get a Dynian Sword");
		}else if(chan<0.5) {
			sword=1;
			mTArea.setText("You get a Steel Sword");
			Wp="Steel Sword";
			wNLabel.setText(Wp);
		}else{
			osword=1;
			mTArea.setText("You get a Old Sword");
			wNLabel.setText(Wp);
			Wp="Old Sword";
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
			nghs = 1;
			mTArea.setText("You collect a Absolute Nature Glass Highest Grade");
		}else if(chan<0.3) {
			ngh =1;
			mTArea.setText("You collect a Super Rare Nature Glass High-Grade");
		}else if(chan<0.5) {
			ngm=1;
			mTArea.setText("You collect a Rare Nature Glass Medium-Grade");
		}else{
			ng=1;
			mTArea.setText("You collect a Nature Glass Low-Grade");
		}
		
			
		c1.setText("Go back");
		c2.setText("Rest");
		c3.setText("Inventory");
		c4.setText("Crossroad");
		
	}
	public void status() {
		position ="status";
		mTArea.setText("");
		c1.setText("Go back");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}	
	public void fight() {
		position ="fight";
		mTArea.setText("You choose to fight\nMonster HP: "+monHP);
		
		c1.setText("Attack");
		c2.setText("Defend");
		c3.setText("Run");
		c4.setText("");
		
	}
	public void defend() {
		position ="defend";
		playerD=0;
		monD=0;
		monD = new java.util.Random().nextInt(3);
		pHP=pHP-monD;
		mTArea.setText("You choose to defend the Monster attack\nDefend UP 1 Turn\nMonster attack You and dealt "+monD+" Damage!");
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}
	
	public void attack() {
		playerD=0;
		position ="attack";
		if (wNLabel.equals("Dynian Sword")) {
			playerD=20;
			playerD = new java.util.Random().nextInt(20);
			
		} else if(wNLabel.equals("Void Nature Glass Sword")) {
			playerD=30;
			playerD = new java.util.Random().nextInt(99);
			
		} else if(wNLabel.equals("Steel Sword")) {
			playerD=10;
			playerD = new java.util.Random().nextInt(10);
			
		} else if(wNLabel.equals("Old Sword")) {
			playerD=3;
			playerD = new java.util.Random().nextInt(5);
			
		}else {
			playerD=0;
			playerD = new java.util.Random().nextInt(3);
			
		}
		monHP = monHP-playerD;
		mTArea.setText("You attack the Monster and dealt "+playerD+" Damage!\nMonster HP : "+monHP);
		
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}
	public void monatt() {
		position ="monatt";
		monD=0;
		monD = new java.util.Random().nextInt(8);
		pHP=pHP-monD;
		mTArea.setText("Monster attack You and dealt "+monD+" Damage!\n"+"Monster HP : "+monHP);
		hNLabel.setText(""+pHP);
		if(pHP<=0) {
			death();
		}
		c1.setText(">");
		c2.setText("");
		c3.setText("");
		c4.setText("");
		
	}
	
	public void east() {
		position ="east";//talk guard
		mTArea.setText("You are at the East. \nThere is a Monster near You.");
		c1.setText("Fight");
		c2.setText("Rest");
		c3.setText("Run");
		c4.setText("");
		
	}

	public void death() {
		position ="death";
		mTArea.setText("Your HP 0\nYou Dead\n\n- GAME OVER -");
		c1.setVisible(false);
		c2.setVisible(false);
		c3.setVisible(false);
		c4.setVisible(false);
		
	}
	public void win() {
		position ="win";
		mTArea.setText("You killed the Monster an obtain a Magic Tool\n Silver Ring");
		sRing=1;
	}
	public void end() {
		position ="end";
		mTArea.setText("Eh? You already have Silver Ring?\nYou can go in to the town Advanture.\n\n- THE END -");
		
		c1.setVisible(false);
		c2.setVisible(false);
		c3.setVisible(false);
		c4.setVisible(false);
		
	}
		
	
		
	
	public class TitleScreenHandler implements ActionListener{
	
		public void actionPerformed(ActionEvent event){
			
			createGameScreen();
			
			
		}
	}
	public class ChoiceHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			String yChoice = event.getActionCommand();
			
			
			switch(position) {
			case "tg"://towngate
				switch(yChoice) {
				case "cho1": talkGuard(); break;
				case "cho2": attg();break; 
				case "cho3": crossRoad();break;
				case "cho4": setting(); break;
				}
				break;
			case "talkGuard":
				switch(yChoice) {
				case "cho1": how(); break;
				case "cho2": tg(); break; 
				case "cho3": crossRoad();break;
				case "cho4":setting();break;
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
					}else {
						how(); 
					}
				break;
				case "cho2": talkGuard(); break; 
				case "cho3": crossRoad();break;
				case "cho4":setting();break;
				}
				break;
			case "setting":
				switch(yChoice) {
				case "cho1": status(); break;
				case "cho2": 
					prevp="setting";
					inventory(); 
					break; 
				case "cho3": tg();break;
				case "cho4":;break;
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
				case "cho3": rest();
				prevp="south";
				break;
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
						south();
					}
				break; 
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
				case "cho2": rest(); break; 
				case "cho3": 
					prevp ="CHerb";
					inventory(); 
					break;
				}
		break;
			case "sr":
				switch(yChoice) {
				case "cho1": CHerb(); break;
				case "cho2": rest(); break; 
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
				}
				break;
			
			case "cho2": break; 
			case "cho3": break;
			case "cho4": break;
			}
			break;

			}
			
		}
	}
	
}
	


