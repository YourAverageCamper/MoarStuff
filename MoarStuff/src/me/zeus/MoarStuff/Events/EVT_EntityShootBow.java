

package me.zeus.MoarStuff.Events;


import me.zeus.MoarStuff.MoarStuff;
import me.zeus.MoarStuff.Objects.MSItem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;



public class EVT_EntityShootBow implements Listener
{
    
    @EventHandler
    public void onBowFire(EntityShootBowEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (!event.getBow().hasItemMeta())
                return;
            
            for (MSItem item : MoarStuff.getInstance().getLoadedItems())
            {
                if (item.getAsItem().getItemMeta().getDisplayName().equalsIgnoreCase(event.getBow().getItemMeta().getDisplayName()))
                {
                    item.executeShoot(event);
                }
            }
        }
    }
    
}
