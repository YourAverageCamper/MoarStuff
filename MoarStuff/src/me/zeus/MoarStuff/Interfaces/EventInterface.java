

package me.zeus.MoarStuff.Interfaces;


import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface EventInterface
{
    public void executeFish(PlayerFishEvent e);
    
    
    
    
    public void executeHit(ProjectileHitEvent e);
    
    
    
    
    public void executeConsume(PlayerItemConsumeEvent e);
    
    
    
    
    public void executeShoot(EntityShootBowEvent event);
}
