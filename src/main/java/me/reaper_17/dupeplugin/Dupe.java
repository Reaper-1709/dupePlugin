package me.reaper_17.dupeplugin;

import me.reaper_17.dupeplugin.commands.DupeCommand;
import me.reaper_17.dupeplugin.commands.DupeThriceCommand;
import me.reaper_17.dupeplugin.commands.DupeTwiceCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dupe extends JavaPlugin {

    public static Dupe instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("dupe").setExecutor(new DupeCommand());
        getCommand("dupeTwice").setExecutor(new DupeTwiceCommand());
        getCommand("dupeThrice").setExecutor(new DupeThriceCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
