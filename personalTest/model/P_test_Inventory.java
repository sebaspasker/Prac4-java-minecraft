package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import model.exceptions.StackSizeException;

public class P_test_Inventory {
	@Test 
	public void Cpy_Constructor() throws StackSizeException {
		Inventory i, i2;
		i = new Inventory();
		i.addItem(new ItemStack(Material.APPLE, 20));
		i.addItem(new ItemStack(Material.BEDROCK, 11));
		i.addItem(new ItemStack(Material.APPLE, 20));
		i.addItem(new ItemStack(Material.BEDROCK, 11));
		i.addItem(new ItemStack(Material.APPLE, 20));
		i.addItem(new ItemStack(Material.BEDROCK, 11));
		
		i2 = new Inventory(i);
		
		assertNotSame(i, i2);
		
		for(int j=0; j<i.getSize(); j++) {
			if(j%2 == 1) {
				assertEquals(new ItemStack(Material.BEDROCK,11), i.getItem(j));
			} else {
				assertEquals(new ItemStack(Material.APPLE,20), i.getItem(j));
			}
		}
		
		assertEquals(i.getSize(), 6);
	}
}
