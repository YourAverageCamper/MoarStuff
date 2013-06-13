
package me.zeus.MoarStuff;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.zeus.MoarStuff.Events.EVT_CraftItem;
import me.zeus.MoarStuff.Events.EVT_EntityShootBow;
import me.zeus.MoarStuff.Events.EVT_PlayerFish;
import me.zeus.MoarStuff.Events.EVT_PlayerItemConsume;
import me.zeus.MoarStuff.Events.EVT_ProjectileHit;
import me.zeus.MoarStuff.Objects.MSItem;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class MoarStuff extends JavaPlugin
{
	
	
	private static MoarStuff instance;
	PluginManager pm;
	File rootDir;
	File bowDir;
	File drinksDir;
	File foodsDir;
	File rodsDir;
	List<MSItem> customItems;
	
	
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable()
	{
		instance = this;
		customItems = new ArrayList<MSItem>();
		init_directories();
		init_events();
		init_loadFiles(bowDir);
		init_loadFiles(foodsDir);
		init_loadFiles(drinksDir);
		init_loadFiles(rodsDir);
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable()
	{
		instance = null;
	}
	
	
	
	/*
	 * Create directories if they don't exist
	 */
	private void init_directories()
	{
		boolean defaults = false;
		rootDir = new File(getDataFolder() + "");
		if (!rootDir.exists())
		{
			rootDir.mkdirs();
			defaults = true;
		}
		rodsDir = new File(getDataFolder() + "/rods");
		if (!rodsDir.exists())
			rodsDir.mkdirs();
		foodsDir = new File(getDataFolder() + "/foods");
		if (!foodsDir.exists())
			foodsDir.mkdirs();
		drinksDir = new File(getDataFolder() + "/drinks");
		if (!drinksDir.exists())
			drinksDir.mkdirs();
		bowDir = new File(getDataFolder() + "/bows");
		if (!bowDir.exists())
			bowDir.mkdirs();
		if (defaults)
			init_defaults();
	}
	
	
	
	/*
	 * Register all events necessary
	 */
	private void init_events()
	{
		pm = getServer().getPluginManager();
		pm.registerEvents(new EVT_EntityShootBow(), this);
		pm.registerEvents(new EVT_CraftItem(), this);
		pm.registerEvents(new EVT_PlayerFish(), this);
		pm.registerEvents(new EVT_PlayerItemConsume(), this);
		pm.registerEvents(new EVT_ProjectileHit(), this);
	}
	
	
	
	/*
	 * Load all custom items from X directory
	 */
	private void init_loadFiles(File directory)
	{
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			File f = files[i];
			CustomItemLoader.setup(f);
		}
	}
	
	
	
	/*
	 * Load all default files
	 */
	private void init_defaults()
	{
		String[] files = new String[] { "bows/DefaultBow.yml", "bows/EnderBow.yml", "bows/WitherBow.yml", "drinks/Mineshine.yml", "foods/CarrotSoup.yml", "foods/DefaultFood.yml",
		        "foods/PotatoSoup.yml", "rods/Gill Griller.yml", "rods/Default Rod.yml" };
		for (int i = 0; i < files.length; i++)
			this.saveResource("defaults/" + files[i], false);
	}
	
	
	
	//======================================================================================//
	/*
	 * Grab the main instance
	 */
	public static MoarStuff getInstance()
	{
		return instance;
	}
	
	
	
	/*
	 * Grab all loaded items
	 */
	public List<MSItem> getLoadedItems()
	{
		return customItems;
	}
}
