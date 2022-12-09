package ru.makotomc.makotochat;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import ru.makotomc.makotochat.Config.Option;

import static ru.makotomc.makotochat.Config.Config.config;

public class WebHook {
    public static WebhookClient client;
    public static void init(){
        if(!config.getBool(Option.discord_alarm))
            return;
        WebhookClientBuilder builder = new WebhookClientBuilder(config.getString(Option.discord_webhook));
        builder.setWait(true);
        client = builder.build();
    }
    public static void sendAlarm(String nickname, String msg, String badwords){
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x99FFFF)
                .setDescription(config.getString(Option.embed_desc).replace("<0>",nickname).replace("<1>",msg))
                .setTitle(new WebhookEmbed.EmbedTitle(config.getString(Option.embed_title),""))
                .setFooter(new WebhookEmbed.EmbedFooter(config.getString(Option.embed_footer).replace("<0>",badwords),"https://i.imgur.com/MPPTjM7.png"))
                .build();
        client.send(embed);
    }
}
