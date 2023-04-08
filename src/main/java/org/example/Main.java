package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.util.Properties;

public class Main extends ListenerAdapter {

    private static final String Token = getToken();

    public static void main(String[] args) {
        try {
            JDA jda = JDABuilder.createLight(Token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                    .setRawEventsEnabled(true)
                    .addEventListeners(new onSlashCommand())
                    .setActivity(Activity.playing("/help"))
                    .build();
            jda.awaitReady();
            SlashCommandData Command = Commands.slash("help", "このBotのヘルプとコマンドを表示することができます");
            SlashCommandData Command2 = Commands.slash("stats", "プレイヤーのステータスを表示することができます")
                    .addOptions(new OptionData(OptionType.STRING,"player_name","プレイヤー名を入力してください", true).setRequired(true));
            jda.updateCommands()
                    .addCommands(Command)
                    .addCommands(Command2)
                    .queue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static String getToken() {
        String userName = System.getProperty("user.name");
        String filePath = "C:\\Users\\" + userName + "\\Desktop\\intellij\\config.yml";
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(filePath);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("TOKEN");
    }

    /*
    public static String setFormat(double number) {
        if (number >= 1000 || number <= -1000) {
            return NumberFormat.getNumberInstance().format(number);
        } else {
            return String.valueOf(number);
        }
    }
    */

    public static String setFormat2(double number, String format) {
        DecimalFormat numberFormat = new DecimalFormat(format);
        return numberFormat.format(number);
    }

    public static Color getColor(String color) {
        switch (color) {
            case "&1" -> {
                return Color.decode("#0000AA");
            }
            case "&2" -> {
                return Color.decode("#00AA00");
            }
            case "&3" -> {
                return Color.decode("#00AAAA");
            }
            case "&4" -> {
                return Color.decode("#AA0000");
            }
            case "&5" -> {
                return Color.decode("#AA00AA");
            }
            case "&6" -> {
                return Color.decode("#FFAA00");
            }
            case "&7" -> {
                return Color.decode("#AAAAAA");
            }
            case "&8" -> {
                return Color.decode("#555555");
            }
            case "&9" -> {
                return Color.decode("#5555FF");
            }
            case "&a" -> {
                return Color.decode("#55FF55");
            }
            case "&b" -> {
                return Color.decode("#55FFFF");
            }
            case "&c" -> {
                return Color.decode("#FF5555");
            }
            case "&d" -> {
                return Color.decode("#FF55FF");
            }
            case "&e" -> {
                return Color.decode("#FFFF55");
            }
            case "&f" -> {
                return Color.decode("#FFFFFF");
            }
        }
        return null;
    }
}