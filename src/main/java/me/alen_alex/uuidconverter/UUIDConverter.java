package me.alen_alex.uuidconverter;

import de.leonhard.storage.Yaml;
import me.alen_alex.uuidconverter.Data.DataManager;
import me.alen_alex.uuidconverter.Data.LuckData;
import me.alen_alex.uuidconverter.config.Configuration;
import me.alen_alex.uuidconverter.utils.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public final class UUIDConverter extends JavaPlugin {

    private static UUIDConverter plugin;
    private FileUtils fileUtils = new FileUtils();;
    private Configuration configuration;
    private DataManager dataManager;
    private Yaml cache;
    @Override
    public void onEnable() {
        plugin = this;
        this.configuration = new Configuration(this);
        configuration.createConfiguration();
        cache = fileUtils.createFile(this,"cache.yml");
        fileUtils.generateFolder(this,"olddata");
        dataManager = new DataManager(this,this.getDataFolder()+ File.separator+"olddata");
        dataManager.loadUserData();
        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<String,LuckData>> iterator = dataManager.getFileData().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String,LuckData> currentData = iterator.next();
                    currentData.getValue().checkForUserData();
                }
                plugin.getLogger().info("Succesfully completed data checking on all cached results!");
            }
        },60);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static UUIDConverter getPlugin() {
        return plugin;
    }

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Yaml getCache() {
        return cache;
    }
}
