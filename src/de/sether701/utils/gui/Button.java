package de.sether701.utils.gui;

import org.bukkit.inventory.ItemStack;

public class Button {
	
	private ItemStack item;
	private Function func;
	
	public Button(ItemStack item, Function func) {
		this.item = item;
		this.func = func;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public Function getFunction() {
		return func;
	}
	
	
	
	public interface Function {
		public void init();
	}
	
}
