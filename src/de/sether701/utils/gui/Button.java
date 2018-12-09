package de.sether701.utils.gui;

import org.bukkit.inventory.ItemStack;

public class Button extends Spacer {
	
	private Function func;
	
	public Button(ItemStack item, Function func) {
		super(item);
		this.func = func;
	}
	
	public Function getFunction() {
		return func;
	}
	
	public interface Function {
		public void init();
	}
	
}
