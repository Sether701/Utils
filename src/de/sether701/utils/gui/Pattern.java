package de.sether701.utils.gui;

import java.util.HashMap;
import java.util.Map;

public class Pattern {
	
	private final String[] pattern;
	private final String fullPattern;
	
	public Pattern(String... lines) throws IllegalArgumentException { 
		
		this.pattern = lines;
		String fullPattern = "";
		for(String s : lines) {
			fullPattern = fullPattern + s;
		}
		this.fullPattern = fullPattern;
		
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].split(" ").length != 9) {
				new IllegalArgumentException("lines have to contain exactly 9 characters");
				return;
			} else {
				for(String ch : lines[i].split(" ")) {
					if(ch.length() > 1) {
						new IllegalArgumentException("spacer has to be exactly one character");
						return;
					}
				}
			}
		}
	}
	
	public int getSize() {
		return this.pattern.length;
	}
	
	public Map<Integer, Spacer> exert(Map<Character, Spacer> spacer) {
		
		Map<Integer, Spacer> map = new HashMap<>();
		
		String pattern = this.fullPattern.replace(" ", "");
		char[] characters = pattern.toCharArray();
		for(int i = 0; i < characters.length; i++) {
			if(spacer.containsKey(characters[i])) {
				map.put(i, spacer.get(characters[i]));
				continue;
			} 
			map.put(i, null);
		}
		
		return map;
	}
	
	
}
