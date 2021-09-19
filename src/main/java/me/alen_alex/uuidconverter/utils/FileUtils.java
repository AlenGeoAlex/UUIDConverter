package me.alen_alex.uuidconverter.utils;

import de.leonhard.storage.Config;
import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.alen_alex.uuidconverter.UUIDConverter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public void generateParentFolder(UUIDConverter plugin){
        if(plugin.getDataFolder().exists())
            return;
        plugin.getDataFolder().mkdirs();
        plugin.getLogger().info("Successfully created plugin folder");
    }

    public Config createConfiguration(UUIDConverter plugin){
        generateParentFolder(plugin);
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        Config createConfig = LightningBuilder
                .fromFile(configFile)
                .addInputStream(plugin.getResource("config.yml"))
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.MANUALLY)
                .createConfig();
        Config config = new Config("config.yml","plugins"+File.separator+plugin.getDescription().getName());
        config.setDefault("version",plugin.getDescription().getVersion());
        plugin.getLogger().info("Configuration has been generated/found");
        plugin.getLogger().info("Configuration version - "+config.get("version"));
        return config;
    }


    public boolean generateFolder(UUIDConverter plugin,String folderName){
        File file = new File(plugin.getDataFolder()+File.separator+folderName);
        if(file.exists())
            return false;
        else{
            file.mkdirs();
            plugin.getLogger().info("Successfully created folder with path "+file.getPath());
            return true;
        }
    }

    public Yaml createFile(UUIDConverter plugin, InputStream is, String fileName){
        return new Yaml(fileName,plugin.getDataFolder().getPath(),is);
    }

    public Yaml createFile(UUIDConverter plugin,String fileName){
        return new Yaml(fileName,plugin.getDataFolder().getPath());
    }

    public Yaml createFile(UUIDConverter plugin,InputStream is,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Yaml(fileName,plugin.getDataFolder()+File.separator+folderName,is);
    }

    public Yaml createFile(UUIDConverter plugin,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Yaml(fileName,plugin.getDataFolder()+File.separator+folderName);
    }

    public Json createJSONFile(UUIDConverter plugin,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Json(fileName,plugin.getDataFolder()+File.separator+folderName);

    }

    public Json createJSONFile(UUIDConverter plugin,InputStream is,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Json(fileName,plugin.getDataFolder()+File.separator+folderName,is);

    }

    public boolean deleteFile(UUIDConverter plugin,String fileName){
        File file = new File(plugin.getDataFolder(),fileName);
        if(file.exists()){
            file.delete();
            return true;
        }else{
            plugin.getLogger().warning("A file with the name "+fileName+" does not exist");
            return false;
        }
    }

    public boolean deleteFile(UUIDConverter plugin,String fileName,String folderName){
        File file = new File(plugin.getDataFolder()+File.separator+folderName,fileName);
        if(file.exists()){
            file.delete();
            return true;
        }else{
            plugin.getLogger().warning("A file with the name "+fileName+" does not exist on folder "+folderName);
            return false;
        }
    }


}
