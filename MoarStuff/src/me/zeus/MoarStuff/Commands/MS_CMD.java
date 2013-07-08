
package me.zeus.MoarStuff.Commands;


import me.zeus.MoarStuff.MoarStuff;
import me.zeus.MoarStuff.Objects.MSItem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class MS_CMD implements CommandExecutor
{
    
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("§cThis command can only be used by the console!");
            return false;
        }
        
        if (!sender.hasPermission("MoarStuff.Help"))
        {
            sender.sendMessage("§cYou don't have permission to do this.");
            return false;
        }
        
        
        if (args.length < 1)
        {
            
        }
        else if (args.length == 3)
        {
            if (args[0].equalsIgnoreCase("give"))
            {
                if (sender.hasPermission("MoarStuff.Give"))
                {
                    Player p = Bukkit.getServer().getPlayer(args[1]);
                    if (p == null)
                    {
                        sender.sendMessage("§cThat player is offline!");
                        return false;
                    }
                    for (MSItem item : MoarStuff.getInstance().getLoadedItems())
                    {
                        String name = ChatColor.stripColor(item.getName()).replace(" ", "_");
                        if (args[2].equalsIgnoreCase(name))
                        {
                            p.getInventory().addItem(item.getAsItem());
                            sender.sendMessage("§aGave " + p.getName() + " a " + item.getName() + "!");
                        }
                        else
                        {
                            sender.sendMessage("§cInvalid item! ");
                            return false;
                        }
                    }
                    
                }
            }
        }
        
        
        return false;
    }
    
}
