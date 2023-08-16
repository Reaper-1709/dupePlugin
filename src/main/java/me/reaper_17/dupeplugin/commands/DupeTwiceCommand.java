package me.reaper_17.dupeplugin.commands;

import me.reaper_17.dupeplugin.Dupe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DupeTwiceCommand implements CommandExecutor {
    private HashMap<Player, Long> cooldowns = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (checkCooldown(p)) {
                p.sendMessage("You must wait 1 second before executing this command again.");
                return true;
            }
            else {
                if (p.getInventory().getItemInMainHand().getType() == Material.AIR || p.getInventory().getItemInMainHand().getType() == null) {
                    p.sendMessage(ChatColor.RED + "Please hold an item in your main hand.");
                }
                else {
                    List<String> materials1 = Dupe.instance.getConfig().getStringList("Items");
                    ArrayList<Material> materials = new ArrayList<>();
                    boolean containsIllegal = false;
                    for (String matStr :
                            materials1) {
                        materials.add(Material.valueOf(matStr));
                    }
                    for (Material m :
                            materials) {
                        if (p.getInventory().getItemInMainHand().getType() == m) {
                            containsIllegal = true;
                        }
                    }
                    if (containsIllegal) {
                        p.sendMessage(ChatColor.RED + "This item cant be duped");
                    } else {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        p.getInventory().addItem(item);
                        p.getInventory().addItem(item);
                    }
                }
                setCooldown(p);
            }
        }
        return true;
    }
    private boolean checkCooldown(Player player) {
        if (cooldowns.containsKey(player)) {
            long lastExecution = cooldowns.get(player);
            long currentTime = System.currentTimeMillis();

            // Check if the cooldown duration has passed (1 second = 1000 milliseconds)
            if (currentTime - lastExecution < 1000) {
                return true; // Cooldown not yet expired
            } else {
                // Cooldown has expired, remove the player's cooldown entry
                cooldowns.remove(player);
            }
        }
        return false; // No cooldown or cooldown expired
    }

    private void setCooldown(Player player) {
        long currentTime = System.currentTimeMillis();
        cooldowns.put(player, currentTime);
    }

}
