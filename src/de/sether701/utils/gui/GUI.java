package de.sether701.utils.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import de.sether701.utils.gui.events.GUIButtonClickEvent;
import de.sether701.utils.gui.events.GUICloseEvent;
import de.sether701.utils.gui.events.GUIOpenEvent;

public class GUI implements Listener {
	
	public static HashMap<Player, GUI> activeGUIs = new HashMap<>();
	
	private String name;
	private Pattern pattern;
	private Map<Character, Button> buttons;
	private Map<Integer, Button> keys;
	private JavaPlugin plugin;
	
	public GUI(String name, Pattern pattern, Map<Character, Button> buttons, JavaPlugin plugin) {
		this.name = name;
		this.pattern =  pattern;
		this.buttons = buttons;
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void open(Player player) {
		
		GUIOpenEvent openEvent = new GUIOpenEvent(this, player);
		
		if(!(openEvent.isCancelled())) {
			openEvent.getPlayer().openInventory(getInventory());
			activeGUIs.put(openEvent.getPlayer(), openEvent.getGUI());
		}
	}
	
	public void close(Player player) {
		if(activeGUIs.containsKey(player)) {
			
			GUICloseEvent closeEvent = new GUICloseEvent(this, player);
			if(!(closeEvent.isCancelled())) {
				activeGUIs.remove(player);
				player.closeInventory();
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	private Inventory getInventory() {
		
		Inventory inv = plugin.getServer().createInventory(null, pattern.getSize()*9, name);
		keys = new HashMap<>();
		
		Map<Integer, Button> items = pattern.exert(buttons);
		for(Map.Entry<Integer, Button> entry : items.entrySet()) {
			inv.setItem(entry.getKey(), entry.getValue().getItem());
			keys.put(entry.getKey(), entry.getValue());
		}
		
		return inv;
	}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getWhoClicked() == null) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getTitle() == null) return;
		
		if(event.getClickedInventory().getTitle().equalsIgnoreCase(name)) event.setCancelled(true);
		
		if(keys.containsKey(event.getSlot())) {
			
			Button button = keys.get(event.getSlot());
			GUIButtonClickEvent buttonEvent = new GUIButtonClickEvent(this, button, (Player) event.getWhoClicked());
			Bukkit.getPluginManager().callEvent(buttonEvent);
			
			if(!buttonEvent.isCancelled()) {
				buttonEvent.getButton().getFunction().init();
			}
		}
		
	}
	
	
	
	
	
	
	/* Cancelling some events */
	
	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		if(event.getInventory() == null) return;
		if(event.getInventory().getTitle() == null) return;
		
		if(event.getInventory().getTitle().equalsIgnoreCase(name)) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPickup(InventoryPickupItemEvent event) {
		if(event.getInventory() == null) return;
		if(event.getInventory().getTitle() == null) return;
		
		if(event.getInventory().getTitle().equalsIgnoreCase(name)) event.setCancelled(true);
	}
	
	@EventHandler
	public void onMove(InventoryMoveItemEvent event) {
		if(event.getSource() != null) {
			if(event.getSource().getTitle() != null) {
				if(event.getSource().getTitle().equalsIgnoreCase(name)) event.setCancelled(true);
			}
		}
		
		if(event.getInitiator() != null) {
			if(event.getInitiator().getTitle() != null) {
				if(event.getInitiator().getTitle().equalsIgnoreCase(name)) event.setCancelled(true);
			}
		}
		
		if(event.getDestination() != null) {
			if(event.getDestination().getTitle() != null) {
				if(event.getDestination().getTitle().equalsIgnoreCase(name)) event.setCancelled(true);
			}
		}
		
	}
	
	
	
}
