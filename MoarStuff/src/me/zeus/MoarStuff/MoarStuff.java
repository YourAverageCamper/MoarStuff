
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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
		rootDir = new File(getDataFolder() + "");
		if (!rootDir.exists())
			rootDir.mkdirs();
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
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("moarstuff"))
		{
			if (args.length < 1)
			{
				sender.sendMessage("§4Invalid arguments");
				return false;
			}
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("defaults"))
				{
					if (sender instanceof ConsoleCommandSender)
					{
						init_defaults();
						sender.sendMessage("§aDefault files have been created, please edit/move them to their correct folders, then restart the server to see changes.");
					}
					else
					{
						sender.sendMessage("§4This command can only be executed from console!");
						return false;
					}
				}
			}
		}
		return false;
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
