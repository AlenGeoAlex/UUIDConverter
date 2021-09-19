package me.alen_alex.uuidconverter.Data;

import de.leonhard.storage.Yaml;
import me.alen_alex.uuidconverter.MojangRequest;
import me.alen_alex.uuidconverter.UUIDConverter;

import java.io.File;
import java.util.UUID;

public class LuckData {

    private String oldUUID,newUUID;
    private boolean changed;
    private Yaml playerLPData;
    private UUIDConverter plugin;
    private String name;

    public LuckData(UUIDConverter plugin,Yaml file){
        this.plugin = plugin;
        this.playerLPData = file;
        this.oldUUID = (file.getString("uuid"));
        this.newUUID = null;
        this.changed = false;
        this.name = file.getString("name");
        plugin.getLogger().info("Succesfully loaded data for user "+name+" with uuid "+oldUUID+".");
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void checkForUserData(){
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                String returnedUUID = MojangRequest.sendRequest(name);
                if(returnedUUID != null){
                    if(!returnedUUID.isEmpty()){
                        if(returnedUUID.equals(oldUUID)){
                            plugin.getLogger().info("===============================================");
                            plugin.getLogger().info("Online User Found");
                            plugin.getLogger().info("Username: "+name);
                            plugin.getLogger().info("Cracked UUID: "+oldUUID);
                            plugin.getLogger().info("Old File Name: "+playerLPData.getFile().getPath());
                            plugin.getCache().set(oldUUID,"Already Registered As Premium");
                        }else{
                            newUUID = returnedUUID.replace("\"","");
                            playerLPData.set("uuid",newUUID);
                            File newFile = new File(playerLPData.getFile().getParent()+File.separator+newUUID+".yml");
                            playerLPData.getFile().renameTo(newFile);
                            plugin.getCache().set(oldUUID,newUUID);
                            plugin.getLogger().info("===============================================");
                            plugin.getLogger().info("Offline User Found:");
                            plugin.getLogger().info("Username: "+name);
                            plugin.getLogger().info("Cracked UUID: "+oldUUID);
                            plugin.getLogger().info("Orginal UUID: "+newUUID);
                            plugin.getLogger().info("Old File Name: "+playerLPData.getFile().getPath());
                            plugin.getLogger().info("New File Name: "+newFile.getPath());
                        }
                    }
                }else{
                    plugin.getCache().set(oldUUID,"No data has been found for the user on Mojang");
                }
            }
        });
    }

}
