package de.sether701.utils.gui.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.sether701.utils.gui.Button;
import de.sether701.utils.gui.GUI;

public class GUIButtonClickEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private Button button;
	private GUI gui;
	private Player player;
	private boolean cancelled;
	
	public GUIButtonClickEvent(GUI gui, Button button, Player player) {
		this.button = button;
		this.gui = gui;
		this.player = player;
	}
	
	public Button getButton() {
		return button;
	}
	
	public GUI getGUI() {
		return gui;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancelled = arg0;
	}

}
