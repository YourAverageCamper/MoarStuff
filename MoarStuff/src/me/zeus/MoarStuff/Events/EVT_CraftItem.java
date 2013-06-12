

package me.zeus.MoarStuff.Events;


import me.zeus.MoarStuff.MoarStuff;
import me.zeus.MoarStuff.Objects.MSItem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;



public class EVT_CraftItem implements Listener
{
    
    @EventHandler
    public void onCraft(CraftItemEvent e)
    {
        Recipe r = e.getRecipe();
        ItemStack is = r.getResult();
        Player p = (Player) e.getWhoClicked();
        
        if (is == null)
            return;
        
        for (MSItem item : MoarStuff.getInstance().getLoadedItems())
        {
            if (is.getType().equals(item.getType()) && is.hasItemMeta())
            {
                if (!p.hasPermission(item.getPermission()))
                {
                    e.setCancelled(true);
                    p.sendMessage("§8[§e§oMoarStuff§r§8]§c You do not have permission to craft this!");
                }
            }
        }
    }
}
