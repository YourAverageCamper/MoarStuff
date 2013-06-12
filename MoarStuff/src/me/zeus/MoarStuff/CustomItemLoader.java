
package me.zeus.MoarStuff;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.zeus.MoarStuff.Enumeration.EffectType;
import me.zeus.MoarStuff.Enumeration.TargetType;
import me.zeus.MoarStuff.Objects.MSItem;
import me.zeus.MoarStuff.Objects.SpecialEffect;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;



public class CustomItemLoader
{
	
	
	public static void setup(File f)
	{
		long time1 = System.currentTimeMillis();
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		MSItem item = new MSItem();
		// set the name
		item.setName(fc.getString("name").replace("&", "§"));
		// add lore or description
		ConfigurationSection description = fc.getConfigurationSection("description");
		List<String> lore = new ArrayList<String>();
		lore.add("§e§o*MSI*§r");
		for (int i = 1; i < description.getKeys(false).size() + 1; i++)
			lore.add(description.getString(i + "").replace("&", "§"));
		item.setLore(lore);
		// set the permission
		item.setPermission(fc.getString("permission"));
		// set the type of item it is
		item.setType(Material.getMaterial(fc.getInt("item-type")));
		// set the item special value
		// create and add the recipe
		ConfigurationSection recipe = fc.getConfigurationSection("recipe");
		ShapedRecipe shaped = new ShapedRecipe(item.getAsItem());
		ShapelessRecipe shapeless = new ShapelessRecipe(item.getAsItem());
		if (!recipe.getStringList("shapeless.ingredients").isEmpty())
		{
			for (int i = 0; i < recipe.getStringList("shapeless.ingredients").size(); i++)
				shapeless.addIngredient(1, Material.getMaterial(recipe.getIntegerList("shapeless.ingredients").get(i)));
			Bukkit.getServer().addRecipe(shapeless);
		}
		ConfigurationSection ingredients = recipe.getConfigurationSection("shaped.ingredients");
		if (ingredients.getString("a") != null)
		{
			String[] shape = recipe.getString("shaped.shape").split(",");
			shaped.shape(shape[0], shape[1], shape[2]);
			for (char c : "abcdefghi".toCharArray())
				if (ingredients.get(c + "") != null)
					shaped.setIngredient(c, Material.getMaterial(ingredients.getInt(c + "")));
			Bukkit.getServer().addRecipe(shaped);
		}
		String path = "";
		Set<String> keys;
		ConfigurationSection fc2;
		if (f.getAbsolutePath().contains("bows"))
		{
			path = "on_shoot";
		}
		else if (f.getAbsolutePath().contains("rods"))
		{
			path = "on_fish";
		}
		else if (f.getAbsolutePath().contains("foods") || f.getAbsolutePath().contains("drinks"))
		{
			path = "on_consume";
		}
		fc2 = fc.getConfigurationSection("effects." + path);
		keys = fc2.getKeys(false);
		// check for potions
		for (String key : keys)
		{
			// potions
			for (int i = 0; i < 99; i++)
			{
				if (key.equalsIgnoreCase("potion-" + i))
				{
					String[] info = fc.getString("effects." + path + "." + key).split(";");
					item.addEffect(new SpecialEffect(EffectType.valueOf(info[0]), PotionEffectType.getByName(info[1]), TargetType.valueOf(info[2]), Integer.parseInt(info[3]), Integer
					        .parseInt(info[4])));
				}
			}
			// health
			if (key.equalsIgnoreCase("health"))
			{
				String[] info = fc.getString("effects." + path + "." + key).split(";");
				item.addEffect(new SpecialEffect(EffectType.valueOf(info[0]), TargetType.valueOf(info[1]), Integer.parseInt(info[2]), Double.parseDouble(info[3])));
			}
			// flight
			if (key.equalsIgnoreCase("flight"))
			{
				String[] info = fc.getString("effects." + path + "." + key).split(";");
				item.addEffect(new SpecialEffect(EffectType.valueOf(info[0]), TargetType.valueOf(info[1]), Double.parseDouble(info[2])));
			}
			// command(s)
			for (int i = 0; i < 99; i++)
			{
				if (key.equalsIgnoreCase("command-" + i))
				{
					String[] info = fc.getString("effects." + path + "." + key).split(";");
					item.addEffect(new SpecialEffect(EffectType.EXECUTE_COMMAND, TargetType.valueOf(info[0]), info[1], Double.parseDouble(info[2])));
				}
			}
			if (key.equalsIgnoreCase("explode"))
			{
				String[] info = fc.getString("effects." + path + "." + key).split(";");
				item.addEffect(new SpecialEffect(EffectType.EXPLODE, TargetType.valueOf(info[0]), Float.parseFloat(info[1]), Double.parseDouble(info[2])));
			}
			if (key.equalsIgnoreCase("broadcast"))
			{
				String[] info = fc.getString("effects." + path + "." + key).split(";");
				item.addEffect(new SpecialEffect(EffectType.BROADCAST_MESSAGE, info[0]));
			}
			for (int i = 0; i < 99; i++)
			{
				if (key.equalsIgnoreCase("entity-" + i))
				{
					String[] info = fc.getString("effects." + path + "." + key).split(";");
					if (info[0].equalsIgnoreCase("SPAWN"))
					{
						item.addEffect(new SpecialEffect(EffectType.SPAWN_ENTITY, EntityType.valueOf(info[1])));
					}
					else if (info[0].equalsIgnoreCase("SHOOT"))
					{
						item.addEffect(new SpecialEffect(EffectType.SHOOT_ENTITY, TargetType.NULL, EntityType.valueOf(info[1]), new Vector(Double.parseDouble(info[2]), Double.parseDouble(info[3]),
						        Double.parseDouble(info[4]))));
					}
				}
			}
		}
		MoarStuff.getInstance().getLoadedItems().add(item);
		System.out.println("Loaded: " + item.getName() + " in time: " + (System.currentTimeMillis() - time1) + " milliseconds");
	}
}
