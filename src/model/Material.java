package model;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * Material Class.
 * @author sebastianpasker
 *
 */
public enum Material {
	
	/** Bloques --> BEDROCK-OBSIDIAN Comida --> WATER_BUCKET-BEEF Herramienta --> IRON_SHOVEL-IRON_PICKAGE Arma --> WOOD_SWORD-IRON_SWORD. */
	/**
	 * Bedrock enumeration
	 */
	BEDROCK(-1, '*'), 
	
	/** Chest enumeration. */
	CHEST(0.1, 'C'), 
	
	/** Sand enumeration. */
	SAND(0.5, 'n'),
	
	/** Dirt enumeration. */
	DIRT(0.5, 'd'),
	
	/** Grass enumeration. */
	GRASS(0.6,'g'),
	
	/** Stone enumeration. */
	STONE(1.5,'s'),
	
	/** Granite enumeration. */
	GRANITE(1.5,'r'),
	
	/** Obsidian enumeration. */
	OBSIDIAN(5,'o'),
	
	/** Water enumeration. */
	WATER_BUCKET(1,'W'),
	
	/** Apple enumeration. */
	APPLE(4,'A'),
	
	/** Bread enumeration. */
	BREAD(5,'B'),
	
	/** Beef enumeration. */
	BEEF(8,'F'),
	
	/** Iron enumeration. */
	IRON_SHOVEL(0.2, '>'),
	
	/** Iron enumeration. */
	IRON_PICKAXE(0.5,'^'),
	
	/** Wood sword enumeration. */
	WOOD_SWORD(1, 'i'),
	
	/** Iron enumeration. */
	IRON_SWORD(2, 'I'),
	
	/** Lava enumeration. */
	LAVA(1.0, '#'),
	
	/** Water enumeration. */
	WATER(0.0, '@'),
	
	/** Air enumeration. */
	AIR(0,' ');
	
	/**
	 * Material value documentation.
	 * 
	 * Material de bloque --> dureza material.
	 * Comida --> Puntos de comida/salud que ganas al comerla.
	 * Herramienta --> Dureza de la herramienta.
	 * Arma --> Cantidad de daño que produce al atacar con ella.
	 *
	 */
	private double value;
	
	/**
	 * The symbol.
	 *
	 */
	private char symbol;
	
	/**
	 * The Constant CONST_VALUES.
	 *
	 */
	private static final Material[] CONST_VALUES = values();
	
	/**
	 * The rng.
	 *
	 */
	static Random rng = new Random(1L);
	
	/**
	 * Constructor .
	 *
	 * @param value Passed double value
	 * @param symbol Passed char symbol
	 */
	Material(double value, char symbol) {
		this.value = value;
		this.symbol = symbol; 
	}
	
	/**
	 * This class search if the materialType that enters is correctly the same type that material pertains.
	 * @param materialType String that is the type of material it want to know it pertains. 
	 * @return Equality boolean.
	 */
	private boolean materialComprobation(String materialType) {
		boolean equals = false;
		
		if(materialType.equals("Block")) {
			if(this.symbol == '*'  ||
				this.symbol == 'C' ||
				this.symbol == 'n' ||
				this.symbol == 'd' ||
				this.symbol == 'g' ||
				this.symbol == 's' ||
				this.symbol == 'r' ||
				this.symbol == 'o' ||
				this.symbol == '#' ||
				this.symbol == '@' ||
				this.symbol == ' ') {
				equals = true;
			}
		} else if(materialType.equals("Edible")) {
			if(this.symbol == 'W'  ||
			    this.symbol == 'A' ||
				this.symbol == 'B' ||
				this.symbol == 'F' ) {
				
				equals = true;
			}
		} else if(materialType.equals("Weapon")) { 
			if(this.symbol == 'i' ||
				this.symbol == 'I') {
				
				equals = true;
			}
		} else if(materialType.equals("Tool")) {
			if(this.symbol == '^' ||
				this.symbol == '>' ) {
				
				equals = true;
			}
		} else if(materialType.equals("Liquid")) {
			if(this.symbol == '#' ||
				this.symbol == '@' ||
				this.symbol == ' ') {
				equals = true;
			}
		}
		
		return equals;
	}
	
	/**
	 * Verifies a Material is a block type.
	 * @return True/False In case symbol is a block material returns true, else false.
	 */
	public boolean isBlock() {
		return this.materialComprobation("Block");		
	}
	
	/**
	 * Verifies a Material is a Edible type.
	 * @return True/False boolean
	 */
	public boolean isEdible() {
		return this.materialComprobation("Edible");
	}
	
	/**
	 * Verifies a Material is a Weapon type.
	 * @return True/False boolean
	 */
	public boolean isWeapon() {
		return this.materialComprobation("Weapon");
	}
	
	/**
	 * Verifies a Material is a Tool type.
	 * @return True/False boolean
	 */
	public boolean isTool() {
		return this.materialComprobation("Tool");
	}
	
	/**
	 * Verifies a Material is Liquid type.
	 *
	 * @return True/False boolean
	 */
	public boolean isLiquid() {
		return this.materialComprobation("Liquid");
	}
	
	/**
	 * Getter.
	 * @return value, integer parameter.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Getter.
	 * @return symbol, character parameter.
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Return a random material.
	 * @param first Where we start in enumeration list.
	 * @param last The last material limit in enumeration list.
	 * @return Material, random material object from array with all materials.
	 */
	public static Material getRandomItem(int first, int last) {
		
		int randomNumber = rng.nextInt(last-first+1)+first;
		return CONST_VALUES[randomNumber];
	}
}

