package ru.makotomc.makotochat.Config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.makotomc.makotochat.MakotoChat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Config {
    public static Config config;
    private static FileConfiguration fileConfiguration;
    @Getter
    private final Map<String, Boolean> booleanConf = new HashMap<>();
    @Getter
    private final Map<String, String> stringConf = new HashMap<>();

    @Getter
    private final Map<String, List<String>> listConf = new HashMap<>();

    public String getString(Option option) {
        return stringConf.get(option.name());
    }

    public boolean getBool(Option option) {
        return booleanConf.get(option.name());
    }

    public List<String> getList(Option option) {
        return listConf.get(option.name());
    }

    public static void addBadWord(String str){
        FileConfiguration config = MakotoChat.getInstance().getConfig();
        List<String> words = Config.config.getList(Option.badwords);
        words.add(str);
        config.set(Option.badwords.path, words);
        try {
            config.save(MakotoChat.getInstance().getDataFolder()+ File.separator+"config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void removeBadWord(String str){
        FileConfiguration config = MakotoChat.getInstance().getConfig();
        List<String> words = Config.config.getList(Option.badwords);
        words.remove(str);
        config.set(Option.badwords.path, words);
        try {
            config.save(MakotoChat.getInstance().getDataFolder()+ File.separator+"config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        config = new Config();
        config.loadOptions();
    }
    public static String configString(String path){
        return fileConfiguration.getString(path);
    }

    public void loadOptions() {
        File configFile = new File(MakotoChat.getInstance().getDataFolder(), "config.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        stringConf.clear();
        booleanConf.clear();
        listConf.clear();
        try {
            for (Option option : Option.values()) {
                switch (option.type) {
                    case "String" -> stringConf.put(option.name(), fileConfiguration.getString(option.path));
                    case "boolean" -> booleanConf.put(option.name(), fileConfiguration.getBoolean(option.path));
                    case "List" -> listConf.put(option.name(), fileConfiguration.getStringList(option.path));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getGlobal().info("Config initialisation error");
        }
    }
}
