import java.io.Serializable;
import java.util.Random;

public class Monster extends Character implements Serializable {
	
	//private String type;
	private int expOnKill;
	private double turnCount = 0;
	
	public Monster() {
		// makes it a bean
	}
	
	public Monster(String name, int expOnKill, int str, int agi, int vit, Weapon weapon, 
			Armor armor, char icon, String color, int x, int y) {
		
		super(name, str, agi, vit, weapon, armor, icon, color, x, y);
		
		//this.name = name;
		this.expOnKill = expOnKill;
		
		super.calcDamage();
		super.calcHP();
		super.calcMoveSpeed();
	}

/*	public String getName() {
		return name;
	} */
	//public void setType(String type) {
		//this.type = type;
	//}
	
	
	public int getExpOnKill() {
		return expOnKill;
	}

	public void setExpOnKill(int expOnKill) {
		this.expOnKill = expOnKill;
	}

	public void setTurnCount (double turnCount) {
		this.turnCount = turnCount;
	}
	
	public double getTurnCount () {
		return turnCount;
	}
	
	public String toString() {
		StringBuilder monsterString = new StringBuilder();
		monsterString.append("Type: " + getName() + "\n");
		monsterString.append("Strength: " + getStrength() + "\n");
		monsterString.append("Agility: " + getAgility() + "\n");
		monsterString.append("Vitality: " + getVitality() + "\n");
		monsterString.append("Current HP: " + getCurHp() + "\n");
		
		return monsterString.toString();
	}
	
}
