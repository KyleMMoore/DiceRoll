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

public class Commands implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] sRay) {
		if (sender instanceof Player) {

			Player player = (Player) sender;
			String name = player.getDisplayName();

			// Random obj
			Random dice = new Random();
			// resultant of dice roll
			int rolled;
			// dice user chooses
			int userDice = 0;
			// stores multiple dice rolls
			List<Integer> rolls = new ArrayList<Integer>();
			switch (sRay.length) {
			// when user fails to pick a dice
			case 0:
				player.sendMessage(ChatColor.RED + "Try /roll d<2-100>");
				return true;
			// when user only enters one dice
			case 1:
				try {
					if (sRay[0].charAt(0) == 'd' || sRay[0].charAt(0) == 'D') {
						sRay[0] = sRay[0].substring(1);
					} else {
						return false;
					}
					// dice must be between 2 and 100
					if (Integer.parseInt(sRay[0]) <= 100 && Integer.parseInt(sRay[0]) >= 2) {

						userDice = Integer.parseInt(sRay[0]); // sets the dice
						rolled = dice.nextInt(userDice) + 1; // "rolls" the dice

					} else {

						// returned if dice is outside range
						player.sendMessage(ChatColor.RED + "That dice has too many/too few sides, " + name + "!");
						return true;

					}
					// DISPLAY RESULTS
					displayResults(player, rolled, userDice);

				} catch (NumberFormatException e) {
					// returned if dice is outside range
					player.sendMessage(ChatColor.RED + "That's no dice, " + name);
					return true;
				}
				return true;
			// when user throws multiple dice
			case 2:
				try {
					// format dice type with 'd' append ex.) /roll d6
					if (sRay[0].charAt(0) == 'd' || sRay[0].charAt(0) == 'D') {
						sRay[0] = sRay[0].substring(1);
					} else {
						return false;
					}
					// dice must be between 2 and 100
					if (Integer.parseInt(sRay[0]) <= 100 && Integer.parseInt(sRay[0]) >= 2) {

						// sets the dice
						userDice = Integer.parseInt(sRay[0]);
						// "rolls" the dice
						rolled = dice.nextInt(userDice) + 1;

					} else {

						// returned if dice is outside range
						player.sendMessage(ChatColor.RED + "That dice has too many/too few sides, " + name + "!");
						return false;

					}
					// dice amount must be be from 1-12
					if (Integer.parseInt(sRay[1]) <= 12 && Integer.parseInt(sRay[1]) >= 2) {

						for (int a = 0; a < Integer.parseInt(sRay[1]); a++) {
							userDice = Integer.parseInt(sRay[0]);
							rolls.add(dice.nextInt(userDice) + 1);
						}
						displayResults(player, rolls, userDice);
						return true;

					} else if (Integer.parseInt(sRay[1]) == 1) {

						player.sendMessage(ChatColor.RED + "This command is for multiple dice! Try /roll <diceType>");
						return true;

					} else {

						// returned if too many/ too few dice are rolled
						player.sendMessage(ChatColor.RED + "Invalid dice amount, try again " + name);
						return false;

					}
				} catch (NumberFormatException e) {
					// returned if dice is outside range
					player.sendMessage(ChatColor.RED + "That's no dice, " + name);
					return true;
				}
				// when user tries /roll without a dice type
			default:
				// when user sends invalid command
				player.sendMessage(ChatColor.RED + "Try /roll d<2-100>");
				return true;
			}
		} else {

			Bukkit.getConsoleSender().sendMessage("Command must be sent from player!");
			return false;

		}
	}

	private void displayResults(Player player, int rolled, int userDice) {
		// player who rolls dice's location
		Location loc = player.getLocation();
		// potential location of players nearby
		Location target;
		// runs through all players online to check their relation to sender
		for (Player p : player.getWorld().getPlayers()) {
			// grabs location of player in list
			target = p.getLocation();
			// 20 is the max distance between sender and recipients
			if (loc.distance(target) <= 20) {

				// if player is you
				if (p.equals(player)) {

					// final product "You rolled a 4 on a D6"
					p.sendMessage(ChatColor.GOLD + "You rolled a " + ChatColor.GREEN + rolled + ChatColor.GOLD + " on a D" + userDice);
				} else {

					// final product "Epixauce rolled a 4 on a D6"
					p.sendMessage(
							ChatColor.GOLD + player.getDisplayName() + " rolled a " + ChatColor.GREEN + rolled + ChatColor.GOLD + " on a D" + userDice);
				}
			}
		}
	}

	private void displayResults(Player player, List<Integer> rolls, int userDice) {
		// player who rolls dice's location
		Location loc = player.getLocation();
		// potential location of players nearby
		Location target;
		// compiled output of all rolls
		String message = "";
		for (int a = 0; a < rolls.size(); a++) {

			if (a != rolls.size() - 1)
				message += rolls.get(a) + ", ";
			else
				message += "and " + rolls.get(a);

		}
		// runs through all players online to check their relation to sender
		for (Player p : player.getWorld().getPlayers()) {
			// grabs location of player in list
			target = p.getLocation();
			// 10 is the max distance between sender and recipients
			if (loc.distance(target) <= 20) {

				// if player is you
				if (p.equals(player)) {

					// final product "You rolled [ n n n n n n ] on 6 D6"
					p.sendMessage(ChatColor.GOLD + "You rolled a " + ChatColor.GREEN + message + ChatColor.GOLD + " on " + numberToWord(rolls.size())
							+ " D" + userDice);
				} else {

					// final product "Epixauce rolled [ n n n n n n ] on 6 D6"
					p.sendMessage(ChatColor.GOLD + player.getDisplayName() + "rolled a " + ChatColor.GREEN + message
							+ ChatColor.GOLD + " on " + numberToWord(rolls.size()) + " D" + userDice);

				}
			}
		}
	}

	private String numberToWord(int n) {
		switch (n) {
		case 2:
			return "two";
		case 3:
			return "three";
		case 4:
			return "four";
		case 5:
			return "five";
		case 6:
			return "six";
		case 7:
			return "seven";
		case 8:
			return "eight";
		case 9:
			return "nine";
		case 10:
			return "ten";
		case 11:
			return "eleven";
		case 12:
			return "twelve";
		default:
			return "zero";
		}
	}
}