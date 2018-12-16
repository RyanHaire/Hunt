package model;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Game {

	public static ArrayList<Character> actors = new ArrayList<Character>();
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<ItemFloor> itemsFloor = new ArrayList<ItemFloor>();
	private static Dungeon[] dungeon = {new Dungeon(0), new Dungeon(1), new Dungeon(2),
										new Dungeon(3), new Dungeon(4), new Dungeon(5),
										new Dungeon(6), new Dungeon(7), new Dungeon(8),
										new Dungeon(9)};
	
	//dungeon[0] = new Dungeon(0);




	public  Game() {
		  //  for (int i = 0; i < 10; i++) {
    	//dungeon[i] = new Dungeon(i);
   // }
		// this.player = player;
		// actors.add(player);
	}
	
	
	// static Player player = new Player("Dave", 10, 10, 10, '@', "#FFFF00"
	// , 0, 1);
	
	public static String log = ("");
	public static int storeMagnitude;
	public static int menu = 0;
	public static int select = 0;
	public static int innerSelect = 0;
	static boolean ifUsed = false;
	static int timeFreeze = 0;
	
	public static ArrayList<ItemFloor> getItemsFloor() {
		return itemsFloor;
	}

	public static void setItemsFloor(ArrayList<ItemFloor> itemsFloor) {
		Game.itemsFloor = itemsFloor;
	}


	static final String buffColor = "#00baff";
	static final String debuffColor = "#ff0000";
	public static final String magicEffectColor = "#9e00d8";
	public static int floor;
	public static Aiming aiming = new Aiming(0, 0);
    


	// effects methods
	public static void effects(int target, int magnitude, Effect effect) {
		// boolean ifUsed = false;
		switch (effect) {
		case RESTORE_HEALTH:
			Game.menu = 2;
			if (Game.actors.get(target).getHp() == Game.actors.get(target).getCurHp()) {

				// show log if player
				if (Game.actors.get(target) instanceof Player) {
					Game.log = ("You are already fully healed.");
					ifUsed = false;
				}

				System.out.println("invalid");
			} else {
				// heal actor

				// show log if player
				if (Game.actors.get(target) instanceof Player) {
					Game.log = ("You restore " + (magnitude) + "HP");
				}
				System.out.println("heal");
				Game.actors.get(target).setCurHp(Game.actors.get(target).getCurHp() + magnitude);
				// makes sure the hp does not overflow
				if (Game.actors.get(target).getCurHp() > Game.actors.get(target).getHp()) {
					Game.actors.get(target).setCurHp(Game.actors.get(target).getHp());
				}
				ifUsed = true;
			}
			break;
		case DAMAGE_HEALTH:
			Game.menu = 2;

			//Game.menu = 1;
			Game.actors.get(target).setCurHp(Game.actors.get(target).getCurHp() - magnitude);
			System.out.println("Cur hp " + actors.get(target).getCurHp());
			System.out.println("damage");
			Game.log = "You poison yourself for " + magnitude + "HP";
			ifUsed = true;
			break;
		case RANDOM_RAISE:
			Game.menu = 2;
			int rand = (int) (Math.random() * 2) + 0;
			switch (rand) {
			case 0: // raise vitality
				Game.actors.get(target).setVitality(Game.actors.get(target).getVitality() + magnitude);
				Game.actors.get(target).calcHP();
				Game.actors.get(target).setCurHp(Game.actors.get(target).getHp()); 
				if (magnitude > 1) {
					Game.log= "Vitality raised by " + magnitude + " points";
				}
				else {
					Game.log= "Vitality raised by " + magnitude + " point";
				}
				break;
			case 1:
				
				Game.actors.get(target).setStrength(Game.actors.get(target).getStrength() + magnitude);
				Game.actors.get(target).calcDamage(); 
				if (magnitude > 1) {
					Game.log= "Strength raised by " + magnitude + " points";
				}
				else {
					Game.log= "Strength raised by " + magnitude + " point";
				}
				break;
			case 2:
				
				Game.actors.get(target).setAgility(Game.actors.get(target).getAgility() + magnitude);
				Game.actors.get(target).calcMoveSpeed(); 
				if (magnitude > 1) {
					Game.log= "Agility raised by " + magnitude + " points";
				}
				else {
					Game.log= "Agility raised by " + magnitude + " point";
				break;
			}
			ifUsed = true;
			break;
			}
			break;
		case TELEPORT:
			Game.aiming.setRestrict(false);
			Game.menu = 3;
			Game.log = "[AIMING!]";

			Game.aiming.setX(Game.actors.get(target).getX());
			Game.aiming.setY(Game.actors.get(target).getY());
			Game.aiming.setShow(true);
			Game.aiming.setEffect(Effect.TELEPORT);
			ifUsed = true;
			break;
		case RANGED:
			Game.aiming.setRestrict(true);
			Game.menu = 3;
			Game.log = "[TARGETING]";
			Game.aiming.setX(Game.actors.get(target).getX());
			Game.aiming.setY(Game.actors.get(target).getY());
			Game.aiming.setShow(true);
			Game.aiming.setEffect(Effect.RANGED);
			storeMagnitude = magnitude;
			ifUsed = true;
			break;
		case STOP_TIME:
			Game.menu = 2;
			if (timeFreeze == 0) {
				Game.log = "Time has stopped!";
				timeFreeze = magnitude;
				ifUsed = true;
			}
			else {
				Game.log = "Time is already frozen!";
				ifUsed = false;
			}
			break;
		default:
			ifUsed = false;
			System.out.println("PLACEHOLDER");
			break;
			
			
		}
	}

	public static void setActors(ArrayList<Character> actors) {
		Game.actors = actors;
	}

	public void createPlayer() {
		// Player player = new Player("Dave", 10, 10, 10, '@', "#FFFF00"
		// , 0, 1);
		// actors.add(player);
	}

	public static double turnRate(int agi) {

		return (double) agi / (double) actors.get(0).getAgility();
	}

	public static boolean coinFlip () {
		int coinflip = (int) (Math.random() * 2) + 0;
		return (coinflip == 0);
	}
	
	public static void update() {

		int turn = 1;

		for (int i = 0; i < actors.size(); i++) {
			Game.getDungeon()[Game.floor].changeEntities(actors.get(i).getY(), actors.get(i).getX(), actors.get(i).geticon(),
					actors.get(i).getColor());
		}
		System.out.println("UPDATE! " + actors.size());

		// check for dead monsters
		for (int n = 1; n < actors.size(); n++) {
			if (actors.get(n).getCurHp() <= 0) {
				Game.getDungeon()[Game.floor].changeEntities(actors.get(n).getY(), actors.get(n).getX(),
						Game.getDungeon()[Game.floor].getTile(actors.get(n).getY(), actors.get(n).getX()).getIcon(),
						Game.getDungeon()[Game.floor].getTile(actors.get(n).getY(), actors.get(n).getX()).getColor());
				((Player) actors.get(0)).gainExp(((Monster) actors.get(n)).getExpOnKill());
				if (coinFlip()) {
					Game.itemsFloor.add(new ItemFloor (((Monster) actors.get(n)).getLootItem(), actors.get(n).getX(), actors.get(n).getY()));
					System.out.println("Loot");
				}
				actors.remove(n);
			}

		}
		if (timeFreeze == 0) {

			if (!projectiles.isEmpty()) {
				for (int p = 0; p < projectiles.size(); p++) {
					// move projectiles
					projectiles.get(p)
							.setTurnCount((projectiles.get(p)).getTurnCount() + turnRate(projectiles.get(p).agility));
					if (projectiles.get(p).getTurnCount() >= turn) {
						for (int t = 0; t < turnRate(projectiles.get(p).getAgility()); t++) {
							projectiles.get(p).move();
							System.out.println("Move Ball");
							// if (projectiles.get(p).isSetDestroy()) {
							// projectiles.remove(p);
						}
					}
					if (projectiles.get(p).isSetDestroy()) {
						projectiles.remove(p);
					}

				}
			}

			for (int n = 1; n < actors.size(); n++) {
				// System.out.println("First: " + turnRate(n) + " " + actors.get(n).getName());
				// System.out.println(actors.get(n).getName() + " AGI = " +
				// actors.get(n).getAgility());

				// find how close the monster is to move in a turn
				((Monster) actors.get(n))
						.setTurnCount(((Monster) actors.get(n)).getTurnCount() + turnRate(actors.get(n).getAgility()));

				if (((Monster) actors.get(n)).getTurnCount() >= turn) {
					for (int t = 0; t < turnRate(actors.get(n).getAgility()); t++) {

						switch (((Monster) actors.get(n)).getState()) {
						default:
							break;
						case IDLE:
							int rand = (int) (Math.random() * 4) + 0;
							// System.out.println("choice: " + rand );

							switch (rand) {
							case 0:
								actors.get(n).move(0, 1);
								break;
							case 1:
								actors.get(n).move(0, -1);
								break;
							case 2:
								actors.get(n).move(1, 0);
								break;
							case 3:
								actors.get(n).move(-1, 0);
								break;
							default:
								// stand still
								break;
							}
							if (((Monster) actors.get(n)).withinRange(actors.get(0), 3)) {
								((Monster) actors.get(n)).setState(MonsterStates.TEST);
							}
							break;
						case TEST:
							if (((Monster) actors.get(n)).withinRange(actors.get(0), 3)) {
								Game.log = "Within Range";
							} else {
								((Monster) actors.get(n)).setState(MonsterStates.IDLE);
							}
							break;
						}
						// System.out.println(turnRate(n) + " " + actors.get(n).getName());

					}
					((Monster) actors.get(n)).setTurnCount(0);
				}

			}
		}
		else {
			timeFreeze--;
			if (timeFreeze == 0) {
				Game.log = "Time has begun to move again!";
			}
		}
		
		// }

		if (!itemsFloor.isEmpty()) {
			for (int i = 0; i < itemsFloor.size(); i++) {
				if (itemsFloor.get(i).getX() == actors.get(0).getX()
						&& itemsFloor.get(i).getY() == actors.get(0).getY()) {
					log = "Item: " + itemsFloor.get(i).getItem().getName();
					System.out.println("Item: " + i);
					System.out.println("Item Name " + itemsFloor.get(i).getItem());
					System.out.println("Item Name2 " + itemsFloor.get(i).getItem().getName());
					

				}
			}
		}
	}

	public static void start() {

		for (int i = 0; i < itemsFloor.size(); i++) {
			dungeon[floor].changeEntities(itemsFloor.get(i).getY(), itemsFloor.get(i).getX(), itemsFloor.get(i).getIcon(),
					itemsFloor.get(i).getColor());
		}
		for (int i = 0; i < actors.size(); i++) {
			dungeon[floor].changeEntities(actors.get(i).getY(), actors.get(i).getX(), actors.get(i).geticon(),
					actors.get(i).getColor());
		}
		// }

	};

	ArrayList<Character> getActors() {
		return actors;
	}

	public static void addActors(Character c) {
		actors.add(c);
	}
	
	public static void addPlayer(Player p) {
		actors.add(0,p);
	}

	public static void display(PrintWriter writer) {

		String insert = "";

		switch (menu) {
		case 0: // no menu
			writer.print("<tr>");
			insert = "";
			break;
		case 1:
			insert = "<td></td>";
			switch (select) {
			case 0:
				// inventory selected
				writer.print(
						"<tr><td>[Inventory]<br>&nbsp;Save&nbsp;<br>&nbsp;Stats&nbsp;<br>&nbsp;Sign Out&nbsp;</td>");
				break;
			case 1:
				// save selected
				writer.print(
						"<tr><td>&nbsp;Inventory&nbsp;<br>[Save]<br>&nbsp;Stats&nbsp;<br>&nbsp;Sign Out&nbsp;</td>");
				break;
			case 2:
				// Stats selected
				writer.print(
						"<tr><td>&nbsp;Inventory&nbsp;<br>&nbsp;Save&nbsp;<br>[Stats]<br>&nbsp;Sign Out&nbsp;</td>");
				break;
			case 3:
				// sign out selected
				writer.print(
						"<tr><td>&nbsp;Inventory&nbsp;<br>&nbsp;Save&nbsp;<br>&nbsp;Stats&nbsp;<br>[Sign Out]</td>");
				break;
			}
			break;
		case 2:
			insert = "<td></td>";
			switch (select) {
			// test

			case 0: // inventory screen
				writer.print("<tr><td>" + ((Player) actors.get(0)).displayBackpack() + "</td>");
				break;
			case 1: /// ADD SAVE HERE ---
				break;
			case 2:
				writer.print("<tr><td>" + actors.get(0).toString() + "</td>");
				break;
			default:
				writer.print("<tr><td>PLACEHOLDER</td>");
				break;
			}
			break;
		default:
			writer.print("<tr>");
			insert = "";
			break;
		}
		Game.getDungeon()[Game.floor].firstPrint(writer);
		// draw game hud
		writer.println("<tr>" + insert + "<td>&thinsp;&thinsp;" + actors.get(0).getName() + " | LV: "
				+ ((Player) actors.get(0)).getLevel() + " | HP: " + actors.get(0).getCurHp() + "/"
				+ actors.get(0).getHp() + " | EXP: " + ((Player) actors.get(0)).getExp() + "/"
				+ ((Player) actors.get(0)).getExpForNextLevel() + " | Floor: " + (Game.getDungeon().length - Game.floor) + "</td></tr>");
		// draw game log
		writer.println("<tr>" + insert + "<td>&thinsp;&thinsp;" + log + "</td></tr>");
	}
	
	public static void clear(PrintWriter writer) {

		String insert = "";

		switch (menu) {
		case 0: // no menu
			writer.print("<tr>");
			insert = "";
			break;
		case 1:
			insert = "<td></td>";
			switch (select) {
			case 0:
				// inventory selected
				writer.print(
						"<tr><td>[Inventory]<br>&nbsp;Magic&nbsp;<br>&nbsp;Stats&nbsp;<br>&nbsp;Sign Out&nbsp;</td>");
				break;
			case 1:
				// magic selected
				writer.print(
						"<tr><td>&nbsp;Inventory&nbsp;<br>[Magic]<br>&nbsp;Stats&nbsp;<br>&nbsp;Sign Out&nbsp;</td>");
				break;
			case 2:
				// Stats selected
				writer.print(
						"<tr><td>&nbsp;Inventory&nbsp;<br>&nbsp;Magic&nbsp;<br>[Stats]<br>&nbsp;Sign Out&nbsp;</td>");
				break;
			case 3:
				// sign out selected
				writer.print(
						"<tr><td>&nbsp;Inventory&nbsp;<br>&nbsp;Magic&nbsp;<br>&nbsp;Stats&nbsp;<br>[Sign Out]</td>");
				break;
			}
			break;
		case 2:
			insert = "<td></td>";
			switch (select) {

			case 0: // inventory screen
				writer.print("<tr><td>" + ((Player) actors.get(0)).displayBackpack() + "</td>");
				break;
			case 2:
				writer.print("<tr><td>" + actors.get(0).toString() + "</td>");
				break;
			default:
				writer.print("<tr><td>PLACEHOLDER</td>");
				break;
			}
			break;
		default:
			writer.print("<tr>");
			insert = "";
			break;
		}
		//Game.getDungeon()[Game.floor].firstPrint(writer);
		writer.print("<td>");
		writer.println("</div>");
		writer.print("</td></tr>");
		// draw game hud
		writer.println("<tr>" + insert + "<td>&thinsp;&thinsp;" + actors.get(0).getName() + " | LV: "
				+ ((Player) actors.get(0)).getLevel() + " | HP: " + actors.get(0).getCurHp() + "/"
				+ actors.get(0).getHp() + " | EXP: " + ((Player) actors.get(0)).getExp() + "/"
				+ ((Player) actors.get(0)).getExpForNextLevel() + " | Floor: " + (Game.getDungeon().length - Game.floor) + "</td></tr>");
		// draw game log
		writer.println("<tr>" + insert + "<td>&thinsp;&thinsp;" + log + "</td></tr>");
	}
	
	
	//public static void makeDungeons () {
  /*  Dungeon dungeon1 = new Dungeon();
    Dungeon dungeon2 = new Dungeon();
    Dungeon dungeon3 = new Dungeon();
    Dungeon dungeon4 = new Dungeon();
    Dungeon dungeon5 = new Dungeon();
    Dungeon dungeon6 = new Dungeon();
    Dungeon dungeon7 = new Dungeon();
    Dungeon dungeon8 = new Dungeon();
    Dungeon dungeon9 = new Dungeon();
    Dungeon dungeon10 = new Dungeon(); */
	 //   for (int i = 0; i < 10; i++) {
	//dungeon[i] = new Dungeon(i);
//}
    
  //  dungeon[] = {dungeon1,dungeon2,dungeon3,dungeon4,dungeon5,dungeon6,dungeon7,dungeon8,dungeon9,dungeon10};
  //  floor = 0;
//	}

	public static Dungeon[] getDungeon() {
		return dungeon;
	}

	public static void setDungeon(Dungeon[] dungeon) {
		Game.dungeon = dungeon;
	}
	
	public static boolean onDown(Player player) {
		return (player.getX() == Game.getDungeon()[Game.floor].getDownFloor()[1] && player.getY() == Game.getDungeon()[Game.floor].getDownFloor()[0]);
	}
	
	public static boolean onUp(Player player) {
		return (player.getX() == Game.getDungeon()[Game.floor].getUpFloor()[1] && player.getY() == Game.getDungeon()[Game.floor].getUpFloor()[0]);
	}
	
	public static Item[] itemTable(int index) {
		Item[] itemTable = null;
		//itemTable[0] = Game.largePotion;
		 switch (index) {
		 	case 0:
			/* Item[] itemTable0 = {Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion,
					Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion,
					Game.scrollFireball, Game.scrollFireball, Game.scrollFireball, Game.scrollFireball, Game.scrollFireball, Game.club, Game.club, Game.lightLeather, Game.lightLeather, Game.lightLeather,
					Game.smallPoison, Game.chainmail, Game.chainmail, Game.ironPlate, Game.scrollFrozenTime, Game.scrollFrozenTime, Game.scrollFrozenTime, Game.potionMinorOfImprovement, Game.scrollGreaterFireball, Game.scrollGreaterFireball};
			 	*/
		 	Item[] itemTable0 = {Items.smallPoison};
		 		itemTable0[0] = Items.smallPoison;
			 	System.out.println("another item test " + itemTable0[0].getName());
			// return  itemTable0;
			 	break;
			 
		default:
		/*	Item[] itemTableDefault = {Items.smallPotion, Items.smallPotion, Items.smallPotion, Items.smallPotion, Items.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion,
					Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion, Game.smallPotion,
					Game.scrollFireball, Game.scrollFireball, Game.scrollFireball, Game.scrollFireball, Game.scrollFireball, Game.club, Game.club, Game.lightLeather, Game.lightLeather, Game.lightLeather,
					Game.smallPoison, Game.chainmail, Game.chainmail, Game.ironPlate, Game.scrollFrozenTime, Game.scrollFrozenTime, Game.scrollFrozenTime, Game.potionMinorOfImprovement, Game.scrollGreaterFireball, Game.scrollGreaterFireball};
			*/
			break;
			//return  itemTableDefault;
			
		}
		 //Item[] test = {Game.scrollFrozenTime};
		 //itemTable = test;
		 return itemTable;
		 
	}

}
