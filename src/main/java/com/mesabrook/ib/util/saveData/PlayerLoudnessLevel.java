package com.mesabrook.ib.util.saveData;

import java.util.HashMap;

public enum PlayerLoudnessLevel {
	Quietly("quietly", "quiet", "q"),
	Normally("normally", "normal", "n"),
	Loudly("loudly", "loud", "l"),
	Globally("globally", "global", "g");
	
	private String[] aliases;
	private static HashMap<String, PlayerLoudnessLevel> levelsByAlias = new HashMap<>();
	
	private PlayerLoudnessLevel(String... aliases)
	{
		this.aliases = aliases;
	}
	
	public static PlayerLoudnessLevel getForAlias(String alias)
	{
		if (!levelsByAlias.containsKey(alias))
		{
			for(PlayerLoudnessLevel level : PlayerLoudnessLevel.values())
			{
				for(String levelAlias : level.aliases)
				{
					levelsByAlias.put(levelAlias, level);
				}
			}
		}
		
		return levelsByAlias.get(alias);
	}
}
