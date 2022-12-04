package ru.makotomc.makotochat;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

enum Option{
    mentions("chat.mentions.enabled","boolean"),
    customNames("chat.useCustomName","boolean"),
    mentions_prefix("chat.mentions.prefix", "String"),
    alarm("chat.mentions.alarm.enabled", "boolean"),
    alarm_message("chat.mentions.alarm.message", "String"),
    automod("chat.automod.enabled", "boolean"),
    automod_alarm("chat.automod.blur.alarm.enabled", "boolean"),
    automod_alarm_message("chat.automod.blur.alarm.message", "String"),
    blur("chat.automod.blur.enabled", "boolean"),
    good_words("chat.automod.blur.good_words","List"),
    global_chat("chat.hasGlobalChat", "boolean"),
    badwords("badwords","List"),
    blur_format("chat.automod.blur.format", "String");
    public final String path;
    public final String type;
    Option(String path, String type){
        this.path = path;
        this.type = type;
    }

}
public class Config {
    public static Config config;
    @Getter
    private final Map<String, Boolean> booleanConf = new HashMap<>();
    @Getter
    private final Map<String, String> stringConf = new HashMap<>();

    @Getter
    private final Map<String,List<String>> listConf = new HashMap<>();

//    public Object getOption(Option option){
//        if(booleanConf.containsKey(option.name()))
//            return booleanConf.get(option.name());
//
//        if(stringConf.containsKey(option.name()))
//            return stringConf.get(option.name());
//
//        if(listConf.containsKey(option.name()))
//            return listConf.get(option.name());
//
//        return null;
//    }
    public String getString(Option option){
        return stringConf.get(option.name());
    }
    public boolean getBool(Option option) {
        return booleanConf.get(option.name());
    }
    public List<String> getList(Option option){
        return listConf.get(option.name());
    }

    public static void init(){
        config = new Config();
        config.loadOptions();
    }

    public void loadOptions(){
        FileConfiguration config = MakotoChat.getInstance().getConfig();
        try {
            for(Option option : Option.values()){
                switch (option.type){
                    case "String" -> stringConf.put(option.name(), config.getString(option.path));
                    case "boolean" -> booleanConf.put(option.name(),config.getBoolean(option.path));
                    case "List" -> listConf.put(option.name(),config.getStringList(option.path));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Logger.getGlobal().info("Config initialisation error");
        }
    }
}
