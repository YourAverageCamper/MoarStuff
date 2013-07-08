
package me.zeus.MoarStuff.Objects;


import java.util.Random;

import me.zeus.MoarStuff.Enumeration.EffectType;
import me.zeus.MoarStuff.Enumeration.TargetType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;



public class SpecialEffect
{
    
    
    EffectType type;
    TargetType targetType;
    PotionEffectType potionType;
    int potionDuration;
    int potionAmplifier;
    int healthAmount;
    String command;
    String broadcast;
    Location location;
    float power;
    double radius;
    EntityType entityType;
    boolean fire;
    Vector vector;
    Material material;
    int chance;
    int amount;
    float exp;
    
    
    
    /*
     * Grab the type of effect
     */
    public EffectType getEffectType()
    {
        return type;
    }
    
    
    
    /*
     * Grab the target type
     */
    public TargetType getTargetType()
    {
        return targetType;
    }
    
    
    
    /*
     * Grab the potion type
     */
    public PotionEffectType getPotionType()
    {
        return potionType;
    }
    
    
    
    /*
     * Grab the potion duration
     */
    public int getPotionDuration()
    {
        return potionDuration;
    }
    
    
    
    /*
     * Grab the potion amplifier
     */
    public int getPotionAmplifier()
    {
        return potionAmplifier;
    }
    
    
    
    /*
     * Grab health amount to heal, set, or add
     */
    public int getHealthAmount()
    {
        return healthAmount;
    }
    
    
    
    /*
     * Grab the command to execute
     */
    public String getCommand()
    {
        return command;
    }
    
    
    
    /*
     * Grab the message to broadcast
     */
    public String getMessage()
    {
        return broadcast;
    }
    
    
    
    /*
     * Grab the location
     */
    public Location getLocation()
    {
        return location;
    }
    
    
    
    /*
     * Grab the explosion power
     */
    public float getPower()
    {
        return power;
    }
    
    
    
    /*
     * Grab the radius of effect
     */
    public double getRadius()
    {
        return radius;
    }
    
    
    
    /*
     * Grab the entity type to spawn
     */
    public EntityType getEntityType()
    {
        return entityType;
    }
    
    
    
    /*
     * Returns if fire happens
     */
    public boolean doesFire()
    {
        return fire;
    }
    
    
    
    /*
     * Grab material
     */
    public Material getMaterial()
    {
        return material;
    }
    
    
    
    /*
     * Grab chance of event happening
     */
    public int getChance()
    {
        return chance;
    }
    
    
    
    /*
     * Grab the amount of X drop
     */
    public int getAmount()
    {
        return amount;
    }
    
    
    
    /*
     * Grab the shoot vector
     */
    public Vector getVector()
    {
        return vector;
    }
    
    
    
    /*
     * Grab exp to use in transaction
     */
    public float getExp()
    {
        return exp;
    }
    
    
    
    /*
     * Default constructor -- unusable
     */
    @Deprecated
    public SpecialEffect(EffectType type)
    {
        this.type = type;
    }
    
    
    
    /*
     * Constructor for potion effects
     */
    public SpecialEffect(EffectType type, PotionEffectType potion, TargetType target, int duration, int amplifier)
    {
        this.type = type;
        this.potionType = potion;
        this.targetType = target;
        this.potionDuration = duration;
        this.potionAmplifier = amplifier;
    }
    
    
    
    /*
     * Constructor for health effects
     */
    public SpecialEffect(EffectType valueOf, TargetType valueOf2, int parseInt, double radius)
    {
        this.type = valueOf;
        this.targetType = valueOf2;
        this.healthAmount = parseInt;
        this.radius = radius;
    }
    
    
    
    /*
     * Constructor for flight
     */
    public SpecialEffect(EffectType et, TargetType tt, double d)
    {
        this.type = et;
        this.targetType = tt;
        this.radius = d;
    }
    
    
    
    /*
     * Constructor for command(s)
     */
    public SpecialEffect(EffectType executeCommand, TargetType valueOf, String string, double d)
    {
        this.type = executeCommand;
        this.targetType = valueOf;
        this.command = string;
        this.radius = d;
    }
    
    
    
    /*
     * Constructor for explosion(s)
     */
    public SpecialEffect(EffectType explode, TargetType valueOf, float parseFloat, double d)
    {
        this.type = explode;
        this.targetType = valueOf;
        this.power = parseFloat;
        this.radius = d;
    }
    
    
    
    /*
     * Constructor for Broadcasts
     */
    public SpecialEffect(EffectType ett, String msgg)
    {
        this.type = ett;
        this.broadcast = msgg;
    }
    
    
    
    /*
     * Constructor for shooting entities
     */
    public SpecialEffect(EffectType shootEntity, TargetType targetType, EntityType valueOf, Vector vector)
    {
        this.type = shootEntity;
        this.entityType = valueOf;
        this.vector = vector;
        this.targetType = targetType;
    }
    
    
    
    /*
     * Constructor for spawning entities
     */
    public SpecialEffect(EffectType spawnEntity, EntityType valueOf)
    {
        this.type = spawnEntity;
        this.entityType = valueOf;
    }
    
    
    
    /*
     * Constructor for dropping items
     */
    public SpecialEffect(EffectType ett, TargetType tt, Material mat, int amt, int chance)
    {
        this.type = ett;
        this.targetType = tt;
        this.material = mat;
        this.amount = amt;
        this.chance = chance;
    }
    
    
    
    /*
     * Perform potion effects
     */
    public void performPotion(EffectType type, Entity e, PotionEffectType pet, int amplifier, int duration)
    {
        if (e instanceof LivingEntity)
            if (type.equals(EffectType.ADD_POTION))
                ((LivingEntity) e).addPotionEffect(new PotionEffect(pet, duration * 20, amplifier));
            else if (type.equals(EffectType.REMOVE_POTION))
                ((LivingEntity) e).removePotionEffect(pet);
    }
    
    
    
    /*
     * Perform health effects
     */
    public void performHealth(EffectType type, Player target, int amt)
    {
        double amount = Double.parseDouble(amt + "");
        if (type.equals(EffectType.ADD_HEALTH))
            target.setHealth(target.getHealth() + amount > target.getMaxHealth() ? target.getMaxHealth() : target.getHealth() + amount);
        else if (type.equals(EffectType.REMOVE_HEALTH))
            target.setHealth(target.getHealth() - amount > 0.0 ? target.getHealth() - amount : 0.0);
        else if (type.equals(EffectType.SET_HEALTH))
            target.setHealth(amount);
    }
    
    
    
    /*
     * Perform flight effects
     */
    public void performFlight(EffectType type, Player target)
    {
        if (type.equals(EffectType.ALLOW_FLIGHT))
            target.setAllowFlight(true);
        else if (type.equals(EffectType.DENY_FLIGHT))
        {
            target.setAllowFlight(false);
            target.setFlying(false);
        }
    }
    
    
    
    /*
     * Perform command
     */
    public void performCommand(Player target, String command)
    {
        if (target != null)
            target.performCommand(command);
        else
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
    }
    
    
    
    /*
     * Perform broadcast
     */
    public void performBroadcast(String message)
    {
        Bukkit.getServer().broadcastMessage(message.toString().replace("&", "§"));
    }
    
    
    
    /*
     * Perform explosion
     */
    public void performExplosion(Location location, float power, boolean fire)
    {
        location.getWorld().createExplosion(location, power, fire);
    }
    
    
    
    /*
     * Perform spawning an entity
     */
    public void performSpawnEntity(Location loc, EntityType type)
    {
        loc.getWorld().spawnEntity(loc, type);
    }
    
    
    
    /*
     * Perform shooting an entity
     */
    public void performShootEntity(Player player, EntityType entityType2, Vector velocity)
    {
        switch (entityType2)
        {
            default:
                break;
            case WITHER_SKULL:
                player.launchProjectile(WitherSkull.class).setVelocity(velocity);
                ;
                break;
            case SNOWBALL:
                player.launchProjectile(Snowball.class).setVelocity(velocity);
                break;
            case EGG:
                player.launchProjectile(Egg.class).setVelocity(velocity);
                break;
            case ARROW:
                player.launchProjectile(Arrow.class).setVelocity(velocity);
                break;
            case ENDER_PEARL:
                player.launchProjectile(EnderPearl.class).setVelocity(velocity);
                break;
        }
    }
    
    
    
    /*
     * Perform dropping an item
     */
    public void performItemDrop(Location loc, ItemStack is, int chance)
    {
        int ran = new Random().nextInt(100) + 1;
        if (ran < chance)
        {
            loc.getWorld().dropItemNaturally(loc, is);
        }
    }
    
    
    
    /*
     * Perform exp transaction
     */
    public void performExpTransaction(EffectType type, Player player, float expAmount)
    {
        if (type.equals(EffectType.ADD_EXP))
        {
            player.setExp(player.getExp() + expAmount);
        }
        else if (type.equals(EffectType.REMOVE_EXP))
        {
            player.setExp(player.getExp() - expAmount < 0.1F ? 0 : player.getExp() - expAmount);
        }
    }
    
    
    
    /*
     * Perform item transaction
     */
    @SuppressWarnings("deprecation")
    public void performItemTransaction(EffectType type, Player player, ItemStack is)
    {
        if (type.equals(EffectType.ADD_ITEM))
        {
            player.getInventory().addItem(new ItemStack[] { is });
            player.updateInventory();
        }
        else if (type.equals(EffectType.REMOVE_ITEM))
        {
            if (!player.getInventory().contains(is))
                return;
            player.getInventory().removeItem(new ItemStack[] { is });
            player.updateInventory();
        }
    }
}
