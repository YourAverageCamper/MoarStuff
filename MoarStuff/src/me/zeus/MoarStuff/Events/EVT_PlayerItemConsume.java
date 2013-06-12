
package me.zeus.MoarStuff.Events;


import me.zeus.MoarStuff.MoarStuff;
import me.zeus.MoarStuff.Objects.MSItem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;



public class EVT_PlayerItemConsume implements Listener
{
	
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e)
	{
		if (!e.getItem().hasItemMeta())
			return;
		for (MSItem item : MoarStuff.getInstance().getLoadedItems())
			if (item.getAsItem().getItemMeta().getDisplayName().equalsIgnoreCase(e.getItem().getItemMeta().getDisplayName()))
				item.executeConsume(e);
	}
}
