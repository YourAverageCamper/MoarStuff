
package me.zeus.MoarStuff.Events;


import me.zeus.MoarStuff.MoarStuff;
import me.zeus.MoarStuff.Objects.MSItem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;



public class EVT_PlayerFish implements Listener
{
	
	
	@EventHandler
	public void onFish(PlayerFishEvent e)
	{
		Player p = e.getPlayer();
		if (!p.getItemInHand().hasItemMeta())
			return;
		if (e.getCaught() == null)
			return;
		for (MSItem item : MoarStuff.getInstance().getLoadedItems())
		{
			if (item.getAsItem().getItemMeta().getDisplayName().equalsIgnoreCase(p.getItemInHand().getItemMeta().getDisplayName()))
			{
				item.executeFish(e);
			}
		}
	}
}
