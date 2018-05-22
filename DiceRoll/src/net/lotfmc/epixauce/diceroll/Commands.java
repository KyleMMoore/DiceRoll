package net.lotfmc.epixauce.diceroll;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] sRay) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			Random dice = new Random();
			int rolled; //resultant of dice roll
			int userDice = 0; //dice user chooses
			List<Integer>rolls = new ArrayList<Integer>(); //stores multiple dice rolls
			
			if(sRay.length==0){
				player.sendMessage(ChatColor.RED+"Try /roll <diceType>"); //when user fails to pick a dice
				return true;
			}else if(sRay.length ==1){ //if command has at least one additional argument
			//probably could be done better, this try/catch validates Integer.parseInt()	
			try{
				if(Integer.parseInt(sRay[0]) <= 100 && Integer.parseInt(sRay[0]) >= 2){ //dice must be between 2 and 100
					userDice = Integer.parseInt(sRay[0]); //sets the dice
					rolled = dice.nextInt(userDice)+1; //"rolls" the dice
				}else{
					player.sendMessage(ChatColor.RED+"That dice has too many/too few sides, "+player.getDisplayName() + "!"); //returned if dice is outside range
					return true;
				}
				displayResults(player,rolled,userDice); //DISPLAY RESULTS
				
			} catch(NumberFormatException e){
				player.sendMessage(ChatColor.RED+"That's no dice, "+player.getDisplayName()); //returned if dice is outside range
				return true;
			}

			
			
			//A-ok!
			return true;			
			}else if(sRay.length ==2) {
				try {
					if(Integer.parseInt(sRay[0]) <= 100 && Integer.parseInt(sRay[0]) >= 2){ //dice must be between 2 and 100
						userDice = Integer.parseInt(sRay[0]); //sets the dice
						rolled = dice.nextInt(userDice)+1; //"rolls" the dice
					}else {
						player.sendMessage(ChatColor.RED+"That dice has too many/too few sides, "+player.getDisplayName() + "!"); //returned if dice is outside range
						return false;
					}
					if(Integer.parseInt(sRay[1]) <= 12 && Integer.parseInt(sRay[1]) >= 2) { //dice amount must be be from 1-12
						for(int a = 0; a<Integer.parseInt(sRay[1]); a++) {
							userDice = Integer.parseInt(sRay[0]);
							rolls.add(dice.nextInt(userDice)+1);
						}
						displayResults(player, rolls, userDice);
						return true;
					}else if(Integer.parseInt(sRay[1]) == 1){
						player.sendMessage(ChatColor.RED+"This command is for multiple dice! Try /roll <diceType>");
						return true;
					}else {
						player.sendMessage(ChatColor.RED+"Invalid dice amount, try again "+player.getDisplayName()); //returned if too many/ too few dice are rolled
						return false;
					}
				}catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED+"That's no dice, "+player.getDisplayName()); //returned if dice is outside range
					return true;					
				}
			}else{
				player.sendMessage(ChatColor.RED+"Try /roll <diceType>"); //when user sends invalid command
				return true;
			}
			
			}else{ //else for player check
		Bukkit.getConsoleSender().sendMessage("Command must be sent from player!");
			return false;				
			}
	  }
	
	private void displayResults(Player player, int rolled, int userDice) {
		Location loc = player.getLocation(); //player who rolls dice's location
		Location target; //potential location of players nearby
		
		for(Player p: player.getWorld().getPlayers()){ //runs through all players online to check their relation to sender
			target = p.getLocation(); //grabs location of player in list
			if(loc.distance(target)<=20){ //20 is the max distance between sender and recipients
				
				if(p.equals(player)){//if player is you
					//final product "You rolled a 4 on a D6"
					p.sendMessage(ChatColor.GOLD+"You rolled a "+rolled+ " on a D"+userDice);
				}else{
					//final product "Epixauce rolled a 4 on a D6"
					p.sendMessage(ChatColor.GOLD+player.getDisplayName()+" rolled a "+rolled+" on a D"+userDice);						
				}
			}
		}	
	}
	private void displayResults(Player player, List<Integer>rolls, int userDice) {
		Location loc = player.getLocation(); //player who rolls dice's location
		Location target; //potential location of players nearby
		String message = " rolled ["; //compiled output of all rolls
		for(Integer i: rolls) {
			message += " " + i;
		}
		message += " ]";
		
		for(Player p: player.getWorld().getPlayers()){ //runs through all players online to check their relation to sender
			target = p.getLocation(); //grabs location of player in list
			if(loc.distance(target)<=20){ //10 is the max distance between sender and recipients
				
				if(p.equals(player)){//if player is you
					//final product "You rolled [ n n n n n n ] on 6 D6"
					p.sendMessage(ChatColor.GOLD+"You"+message + " on " +rolls.size() + " D"+userDice);
				}else{
					//final product "Epixauce rolled [ n n n n n n ] on 6 D6"
					p.sendMessage(ChatColor.GOLD+player.getDisplayName() + message + " on " +rolls.size() + " D"+userDice);				
				}
			}
		}
	}
}