package net.lotfmc.epixauce.diceroll;

import org.bukkit.plugin.java.JavaPlugin;

public class DiceRoll extends JavaPlugin{

@Override
public void onEnable(){
	this.getCommand("roll").setExecutor(new Commands());
}
public void onDisable(){
}
}
