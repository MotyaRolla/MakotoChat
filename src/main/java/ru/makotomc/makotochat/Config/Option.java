package ru.makotomc.makotochat.Config;


public enum Option{
    mentions("chat.mentions.enabled","boolean"),
    mention_alarm_sound("chat.mentions.alarm.sound", "String"),
    blur_alarm_sound("chat.automod.blur.alarm.sound", "String"),
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
    locale_badwordremove("locale.commands.pm.badwords.remove", "String"),
    locale_noreply("locale.commands.pm.noreply", "String"),
    locale_messagesent("locale.commands.pm.messagesent", "String"),
    locale_newmessage("locale.commands.pm.newmessage", "String"),
    locale_wrongusage_pm("locale.commands.pm.wrong_usage_pm", "String"),
    locale_newmessage_title("locale.commands.pm.messagesent_title", "String"),
    locale_player_not_found("locale.commands.pm.player_not_found", "String"),
    discord_alarm("chat.automod.discord_alarms.enabled","boolean"),
    discord_webhook("chat.automod.discord_alarms.webhook","String"),
    embed_title("chat.automod.discord_alarms.embed.title", "String"),
    embed_desc("chat.automod.discord_alarms.embed.description", "String"),
    embed_footer("chat.automod.discord_alarms.embed.footer", "String"),
    death_messages("chat.system_msg.death.enabled","boolean"),
    blur_format("chat.automod.blur.format", "String"),
    use_makoto_private_messages("chat.use_makoto_private_messages", "boolean");
    public final String path;
    public final String type;
    Option(String path, String type){
        this.path = path;
        this.type = type;
    }

}