package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.Main.getColor;
import static org.example.Main.setFormat2;

class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("help")) {
            MessageCreateBuilder Builder = new MessageCreateBuilder();
            EmbedBuilder Embed = new EmbedBuilder();
            Embed.setTitle("このBotについて");
            Embed.setFooter("Version: 1.0.0");
            Embed.setColor(Color.GREEN);
            List<String> HelpList = new ArrayList<>();
            HelpList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
            HelpList.add("プレイヤーのステータスを表示することができます");
            HelpList.add("");
            HelpList.add("**制作者**: たぶ");
            HelpList.add("**スペシャルサンクス**: たぶ鯖の運営陣");
            HelpList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
            HelpList.add("**コマンドリスト①**");
            HelpList.add("**/help**: このメッセージを表示することができます");
            HelpList.add("**/stats (player name)**: プレイヤーのステータスを表示することができます");
            HelpList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
            HelpList.add("**コマンドリスト②**");
            HelpList.add("**/au**: auじゃんけんをすることができます");
            HelpList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
            String Help = String.join("\n", HelpList);
            Embed.addField("", Help, false);
            //event.getChannel().sendMessage(Builder.setEmbeds(Embed.build()).build()).queue();
            event.reply(Builder.setEmbeds(Embed.build()).build()).setEphemeral(true).queue();
        } else if (event.getName().equals("stats")) {
            OptionMapping optionMapping = event.getOption("player_name");
            String playerName = Objects.requireNonNull(optionMapping).getAsString();
            playerName = playerName.toUpperCase();
            if (checkPlayerName(playerName) == null) {
                event.reply("存在しないプレイヤー名です").setEphemeral(true).queue();
            } else {
                String UUID = checkPlayerName(playerName);
                MessageCreateBuilder Builder = new MessageCreateBuilder();
                EmbedBuilder Embed = new EmbedBuilder();
                Embed.setTitle(playerName + "のステータス");
                Embed.setFooter("Version: 1.0.0");
                Color color = getColor("&c");
                String square = getPlayerData2(UUID, "Square");
                if (square != null) {
                    String compile = "&c";
                    if (square.contains("&1")) {
                        compile = "&1";
                    } else if (square.contains("&2")) {
                        compile = "&2";
                    } else if (square.contains("&3")) {
                        compile = "&3";
                    } else if (square.contains("&4")) {
                        compile = "&4";
                    } else if (square.contains("&5")) {
                        compile = "&5";
                    } else if (square.contains("&6")) {
                        compile = "&6";
                    } else if (square.contains("&7")) {
                        compile = "&7";
                    } else if (square.contains("&8")) {
                        compile = "&8";
                    } else if (square.contains("&9")) {
                        compile = "&9";
                    } else if (square.contains("&a")) {
                        compile = "&a";
                    } else if (square.contains("&b")) {
                        compile = "&b";
                    } else if (square.contains("&c")) {
                        compile = "&c";
                    } else if (square.contains("&d")) {
                        compile = "&d";
                    } else if (square.contains("&e")) {
                        compile = "&e";
                    } else if (square.contains("&f")) {
                        compile = "&f";

                    }
                    Pattern pattern = Pattern.compile(compile);
                    Matcher matcher = pattern.matcher(square);
                    if (matcher.find()) {
                        square = matcher.group();
                    }
                    color = getColor(square);
                }
                Embed.setColor(color);
                List<String> StatsList = new ArrayList<>();
                StatsList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
                StatsList.add("**名前**: " + playerName);
                StatsList.add("**UUID**: " + UUID);
                if (getPlayerData2(UUID, "Prefix") != null) {
                    StatsList.add("**称号**: " + getPlayerData2(UUID, "Prefix"));
                } else {
                    StatsList.add("**称号**: 未設定");
                }
                if (getPlayerData2(UUID, "Rank") != null) {
                    StatsList.add("**ランク**: " + getPlayerData2(UUID, "Rank"));
                } else {
                    StatsList.add("**ランク**: ★0");
                }
                double level = getPlayerData(UUID, "Level");
                if (!Double.isNaN(level)) {
                    int level2 = (int) getPlayerData(UUID, "Level");
                    StatsList.add("**レベル**: ✚" + level2 + "/✚100");
                } else {
                    StatsList.add("**レベル**: ✚0/✚100");
                }
                double achievementPoints = getPlayerData(UUID, "AchievementPoints");
                if (!Double.isNaN(achievementPoints)) {
                    StatsList.add("**実績ポイント**: ♫" + setFormat2(achievementPoints, "0.0"));
                } else {
                    StatsList.add("**実績ポイント**: ♫0.0/♫0.0");
                }
                double coins = getPlayerData(UUID, "Coins");
                if (!Double.isNaN(coins)) {
                    StatsList.add("**コイン**: ✦" + setFormat2(coins, "0.0"));
                } else {
                    StatsList.add("**コイン**: ✦0.0/✦0.0");
                }
                double rubies = getPlayerData(UUID, "Rubies");
                if (!Double.isNaN(rubies)) {
                    StatsList.add("**ルビー**: ♦" + setFormat2(rubies, "0.0"));
                } else {
                    StatsList.add("**ルビー**: ♦0.0/♦0.0");
                }
                double crystals = getPlayerData(UUID, "Crystals");
                if (!Double.isNaN(crystals)) {
                    StatsList.add("**クリスタル**: ❇" + setFormat2(crystals, "0.0"));
                } else {
                    StatsList.add("**クリスタル**: ❇0.0/❇0.0");
                }
                String userName = System.getProperty("user.name");
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode json;
                try {
                    json = objectMapper.readTree(Paths.get("C:\\Users\\" + userName + "\\Desktop\\Minigame\\world\\stats\\" + UUID + ".json").toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                long playTimeTick = json.get("stats").get("minecraft:custom").get("minecraft:play_time").longValue();
                playTimeTick = playTimeTick / 20;
                int playTimeDay = (int) Math.floor((float) playTimeTick / 86400);
                long playTimeSecond = playTimeTick - 86400L * playTimeDay;
                int playTimeHour = (int) Math.floor((float) playTimeSecond / 3600);
                playTimeSecond = playTimeSecond - 3600L * playTimeHour;
                int playTimeMinute = (int) Math.floor((float) playTimeSecond / 60);
                playTimeSecond = playTimeSecond - 60L * playTimeMinute;
                String playTime = playTimeDay + "日" + playTimeHour + "時間" + playTimeMinute + "分" + playTimeSecond + "秒";
                StatsList.add("**プレイ時間**: " + playTime);
                StatsList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
                String Stats = String.join("\n", StatsList);
                Embed.addField("", Stats, false);
                event.reply(Builder.setEmbeds(Embed.build()).build()).queue();
            }
        } else if (event.getName().equals("au")) {
            MessageCreateBuilder Builder = new MessageCreateBuilder();
            EmbedBuilder Embed = new EmbedBuilder();
            Embed.setTitle("auじゃんけん");
            Embed.setFooter("Version: 1.0.0");
            Embed.setColor(Color.ORANGE);
            List<String> auList = new ArrayList<>();
            auList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
            auList.add("**ボタンをクリック!**");
            auList.add("<:tabuuuu:793720801563246612>: グー (✊)");
            auList.add("<:Kenta3578:793720567223812146>: チョキ (✌️)");
            auList.add("<:margarineO:793720633439420436>: パー (\uD83D\uDD90)");
            auList.add("<:pien:791868411587067935>: 絶対にクリックするな (❌)");
            auList.add("~~━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━~~");
            String au = String.join("\n", auList);
            Embed.addField("", au, false);
            //event.getChannel().sendMessage(Builder.setEmbeds(Embed.build()).build()).queue();
            event.reply(Builder.setEmbeds(Embed.build()).build())
                .addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("rock", Emoji.fromFormatted("<:tabuuuu:793720801563246612>")),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("scissors", Emoji.fromFormatted("<:Kenta3578:793720567223812146>")),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("paper", Emoji.fromFormatted("<:margarineO:793720633439420436>")),
                        net.dv8tion.jda.api.interactions.components.buttons.Button.danger("don't click me", Emoji.fromFormatted("<:pien:791868411587067935>")))
                .queue();
        } else {
            event.reply("Unknown command. Type \"/help\" for help.").queue();
        }
    }
    public static String checkPlayerName(String playerName) {
        String userName = System.getProperty("user.name");
        String filePath = "C:\\Users\\" + userName + "\\Desktop\\Minigame\\plugins\\PlayerData\\allPlayers.yml";
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(filePath);
            properties.load(inputStream);
            if (properties.getProperty(playerName) != null) {
                return properties.getProperty(playerName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static double getPlayerData(String UUID, String data) {
        String userName = System.getProperty("user.name");
        String filePath = "C:\\Users\\" + userName + "\\Desktop\\Minigame\\plugins\\PlayerData\\" + UUID + ".yml";
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(filePath);
            properties.load(inputStream);
            if (properties.getProperty(data) != null && !properties.getProperty(data).contains("-")) {
                return Double.parseDouble(properties.getProperty(data));
            } else {
                return Double.NaN;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.NaN;
    }

    public static String getPlayerData2(String UUID, String data) {
        String userName = System.getProperty("user.name");
        String filePath = "C:\\Users\\" + userName + "\\Desktop\\Minigame\\plugins\\PlayerData\\" + UUID + ".yml";
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(filePath);
            properties.load(inputStream);
            if (properties.getProperty(data) != null && !properties.getProperty(data).contains("-")) {
                return properties.getProperty(data);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
