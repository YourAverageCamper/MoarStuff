
package me.zeus.MoarStuff.Objects;


import java.util.ArrayList;
import java.util.List;

import me.zeus.MoarStuff.Enumeration.EffectType;
import me.zeus.MoarStuff.Enumeration.TargetType;
import me.zeus.MoarStuff.Interfaces.EventInterface;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;



public class MSItem implements EventInterface
{
	
	
	// All variables
	String name;
	List<String> lore;
	String permission;
	Material type;
	List<SpecialEffect> effects;
	
	
	
	// constructor
	public MSItem()
	{
		this.effects = new ArrayList<SpecialEffect>();
	}
	
	
	
	/*
	 * =============================================*
	 * 
	 * Global Getters for the item
	 * 
	 * =============================================
	 */
	/*
	 * Grab the name
	 */
	public String getName()
	{
		return name;
	}
	
	
	
	/*
	 * Grab the lore
	 */
	public List<String> getLore()
	{
		return lore;
	}
	
	
	
	/*
	 * Grabs the itemstack
	 */
	public ItemStack getAsItem()
	{
		ItemStack is = new ItemStack(type, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	
	
	/*
	 * Grab the material of the item
	 */
	public Material getType()
	{
		return type;
	}
	
	
	
	/*
	 * Grab all special effects
	 */
	public List<SpecialEffect> getEffects()
	{
		return effects;
	}
	
	
	
	/*
	 * Grab the permission
	 */
	public String getPermission()
	{
		return permission;
	}
	
	
	
	/*
	 * =============================================*
	 * 
	 * Global setters for the item
	 * 
	 * =============================================
	 */
	/*
	 * Set the name of the item
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
	/*
	 * Set the lores of the item
	 */
	public void setLore(List<String> lore)
	{
		this.lore = lore;
	}
	
	
	
	/*
	 * Set the permission required to craft the item
	 */
	public void setPermission(String permission)
	{
		this.permission = permission;
	}
	
	
	
	/*
	 * Set the item type
	 */
	public void setType(Material mat)
	{
		this.type = mat;
	}
	
	
	
	/*
	 * Add effect to item
	 */
	public void addEffect(SpecialEffect effect)
	{
		this.effects.add(effect);
	}
	
	
	
	/*
	 * =============================================*
	 * 
	 * 			What happens (EFFECTS)
	 * 
	 * =============================================
	 */
	@Override
	public void executeShoot(EntityShootBowEvent event)
	{
		handleEffect((Player) event.getEntity(), event.getProjectile(), event.getEntity().getLocation(), event.getProjectile().getVelocity(), event);
	}
	
	
	
	@Override
	public void executeFish(PlayerFishEvent event)
	{
		handleEffect(event.getPlayer(), event.getCaught(), event.getCaught().getLocation(), new Vector(0, 0, 0), null);
	}
	
	
	
	@Override
	public void executeHit(ProjectileHitEvent event)
	{
		handleEffect((Player) event.getEntity().getShooter(), event.getEntity(), event.getEntity().getLocation(), new Vector(0, 0, 0), null);
	}
	
	
	
	@Override
	public void executeConsume(PlayerItemConsumeEvent event)
	{
		handleEffect(event.getPlayer(), null, event.getPlayer().getLocation(), new Vector(0, 0, 0), null);
	}
	
	
	
	/*
	 * Handle all effects
	 */
	private void handleEffect(Player player, Entity entity, Location loc, Vector velocity, EntityShootBowEvent shootBowEvent)
	{
		for (SpecialEffect eff : this.getEffects())
		{
			if (eff.getTargetType().equals(TargetType.NEARBY_TARGET))
			{
				for (Entity e : entity.getNearbyEntities(eff.getRadius(), eff.getRadius(), eff.getRadius()))
				{
					switch (eff.type)
					{
						case ADD_HEALTH:
							eff.performHealth(EffectType.ADD_HEALTH, (Player) e, eff.getHealthAmount());
							break;
						case ADD_POTION:
							eff.performPotion(EffectType.ADD_POTION, e, eff.getPotionType(), eff.getPotionAmplifier(), eff.getPotionDuration());
							break;
						case ALLOW_FLIGHT:
							eff.performFlight(EffectType.ALLOW_FLIGHT, (Player) e);
							break;
						case BROADCAST_MESSAGE:
							eff.performBroadcast(eff.getMessage());
							break;
						case DENY_FLIGHT:
							eff.performFlight(EffectType.DENY_FLIGHT, (Player) e);
							break;
						case EXECUTE_COMMAND:
							eff.performCommand(player, eff.getCommand());
							break;
						case EXPLODE:
							eff.performExplosion(loc, eff.getPower(), eff.doesFire());
							break;
						case REMOVE_HEALTH:
							eff.performHealth(EffectType.REMOVE_HEALTH, (Player) e, eff.getHealthAmount());
							break;
						case REMOVE_POTION:
							eff.performPotion(EffectType.REMOVE_POTION, e, eff.getPotionType(), 0, 0);
							break;
						case SET_HEALTH:
							eff.performHealth(EffectType.SET_HEALTH, (Player) e, eff.getHealthAmount());
							break;
						case SPAWN_ENTITY:
							shootBowEvent.setCancelled(true);
							eff.performSpawnEntity(loc, eff.getEntityType());
							break;
						case SHOOT_ENTITY:
							shootBowEvent.setCancelled(true);
							if (eff.getVector().getX() > 1)
							{
								eff.performShootEntity(player, eff.getEntityType(), eff.getVector());
							}
							else
							{
								eff.performShootEntity(player, eff.getEntityType(), shootBowEvent.getProjectile().getVelocity());
							}
							break;
					}
				}
			}
			else if (eff.getTargetType().equals(TargetType.PLAYER))
			{
				switch (eff.type)
				{
					case ADD_HEALTH:
						eff.performHealth(EffectType.ADD_HEALTH, player, eff.getHealthAmount());
						break;
					case ADD_POTION:
						eff.performPotion(EffectType.ADD_POTION, player, eff.getPotionType(), eff.getPotionAmplifier(), eff.getPotionDuration());
						break;
					case ALLOW_FLIGHT:
						eff.performFlight(EffectType.ALLOW_FLIGHT, player);
						break;
					case BROADCAST_MESSAGE:
						eff.performBroadcast(eff.getMessage());
						break;
					case DENY_FLIGHT:
						eff.performFlight(EffectType.DENY_FLIGHT, player);
						break;
					case EXECUTE_COMMAND:
						eff.performCommand(player, eff.getCommand());
						break;
					case EXPLODE:
						eff.performExplosion(loc, eff.getPower(), eff.doesFire());
						break;
					case REMOVE_HEALTH:
						eff.performHealth(EffectType.REMOVE_HEALTH, player, eff.getHealthAmount());
						break;
					case REMOVE_POTION:
						eff.performPotion(EffectType.REMOVE_POTION, player, eff.getPotionType(), 0, 0);
						break;
					case SET_HEALTH:
						eff.performHealth(EffectType.SET_HEALTH, player, eff.getHealthAmount());
						break;
					case SPAWN_ENTITY:
						shootBowEvent.setCancelled(true);
						eff.performSpawnEntity(loc, eff.getEntityType());
						break;
					case SHOOT_ENTITY:
						shootBowEvent.setCancelled(true);
						if (eff.getVector().getX() > 1)
						{
							eff.performShootEntity(player, eff.getEntityType(), eff.getVector());
						}
						else
						{
							eff.performShootEntity(player, eff.getEntityType(), shootBowEvent.getProjectile().getVelocity());
						}
						break;
					default:
						break;
				}
			}
			else if (eff.getTargetType().equals(TargetType.NULL))
			{
				switch (eff.type)
				{
					case BROADCAST_MESSAGE:
						eff.performBroadcast(eff.getMessage());
						break;
					case EXPLODE:
						eff.performExplosion(loc, eff.getPower(), eff.doesFire());
						break;
					case SPAWN_ENTITY:
						shootBowEvent.setCancelled(true);
						eff.performSpawnEntity(loc, eff.getEntityType());
						break;
					case SHOOT_ENTITY:
						shootBowEvent.setCancelled(true);
						if (eff.getVector().getX() > 1)
						{
							eff.performShootEntity(player, eff.getEntityType(), eff.getVector());
						}
						else
						{
							eff.performShootEntity(player, eff.getEntityType(), shootBowEvent.getProjectile().getVelocity());
						}
						break;
					default:
						break;
				}
			}
		}
	}
}
