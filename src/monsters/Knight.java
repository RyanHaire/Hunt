package monsters;

import model.Armor;
import model.Game;
import model.Monster;
import model.Weapon;
import model.Items;

public class Knight  extends Monster{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String name = "Knight";
	private static int expOnKill = 20;
	private static int str = 7;
	private static int agi = 2;
	private static int vit = 10;
	private static Weapon weapon = Items.ironLongblade;
	private static Armor armor = null;
	private static char icon = 'K';
	private static String color ="#FFFAF0";



	public Knight(int x, int y, int floor) {
		super(name, expOnKill, str, agi, vit, weapon, armor, icon, color, x, y, floor);

		//this.name = name;
		
		super.calcDamage();
		super.calcHP();
		super.calcMoveSpeed();
	}
	
}
