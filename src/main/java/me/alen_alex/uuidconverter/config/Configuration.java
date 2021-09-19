package me.alen_alex.uuidconverter.config;

import de.leonhard.storage.Config;
import me.alen_alex.uuidconverter.UUIDConverter;
import me.alen_alex.uuidconverter.utils.FileUtils;

public class Configuration {

    private UUIDConverter plugin;
    private Config config;
    private String folderPath;

    public Configuration(UUIDConverter plugin){
        this.plugin = plugin;
    }

    public boolean createConfiguration(){
        config = plugin.getFileUtils().createConfiguration(plugin);
        if(config != null)
            return true;
        else
            return false;
    }

    private void loadFile(){
        folderPath = config.getString("folder-location");

    }

    public String getFolderPath() {
        return folderPath;
    }
}
