package ru.makotomc.makotochat.Config;


public enum Option{
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
    mention_format("chat.mentions.format","String"),
    player_join("chat.system_msg.player_join.enabled", "boolean"),
    player_exit("chat.system_msg.player_exit.enabled", "boolean"),
    join_messages("chat.system_msg.player_join.messages", "List"),
    exit_messages("chat.system_msg.player_exit.messages","List"),

    locale_noperm("locale.no_permission","String"),
    locale_wrongusage("locale.wrong_usage", "String"),
    locale_makotohelp("locale.commands.makoto.help", "String"),
    locale_makotoreload("locale.commands.makoto.reload", "String"),
    locale_badwordadd("locale.commands.makoto.badwords.add", "String"),
    locale_badwordremove("locale.commands.makoto.badwords.remove", "String"),
    discord_alarm("chat.automod.discord_alarms.enabled","boolean"),
    discord_webhook("chat.automod.discord_alarms.webhook","String"),
    embed_title("chat.automod.discord_alarms.embed.title", "String"),
    embed_desc("chat.automod.discord_alarms.embed.description", "String"),
    embed_footer("chat.automod.discord_alarms.embed.footer", "String"),
    blur_format("chat.automod.blur.format", "String");
    public final String path;
    public final String type;
    Option(String path, String type){
        this.path = path;
        this.type = type;
    }

}