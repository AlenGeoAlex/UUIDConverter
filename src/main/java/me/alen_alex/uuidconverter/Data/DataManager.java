package me.alen_alex.uuidconverter.Data;

import de.leonhard.storage.Yaml;
import me.alen_alex.uuidconverter.UUIDConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

public class DataManager {

    private UUIDConverter plugin;
    private HashMap<String, LuckData> fileData;
    private Path folder;
    public DataManager(UUIDConverter plugin,String absPath) {
        this.plugin = plugin;
        fileData = new HashMap<String,LuckData>();
        folder = Paths.get(absPath);
    }

    public boolean loadUserData(){
        try {
            Files.walk(folder).forEach((path -> {
                    fileData.put(path.getFileName().toString(), new LuckData(plugin, new Yaml(path.toFile())));
                }));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<String, LuckData> getFileData() {
        return fileData;
    }
}
