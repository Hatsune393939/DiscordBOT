package org.example;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Random;

public class ButtonInteractionListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String Mention = event.getUser().getAsMention();
        if (event.getComponentId().equals("don't click me")) {
            event.reply(Mention + "YOU ARE AN \uD83D\uDCA9 AND \\:poop:").queue();
        } else {
            String you = event.getComponentId();
            int randomInt = new Random().nextInt(3);
            /*
            0=グー
            1=チョキ
            2=パー
            */
            String userName = System.getProperty("user.name");
            String filePath = "C:\\Users\\" + userName + "\\Desktop\\intellij\\YOULOSE.mp3";
            if (you.equals("rock") && randomInt == 0) {
                event.reply(Mention + you + "あいこ!").queue();
            } else if (you.equals("rock") && randomInt == 1) {
                event.reply(Mention + you + "YOU WIN! あなたは人生勝ち組だ").queue();
            } else if (you.equals("rock")) {
                event.reply(Mention + you + "**Y O U L O S E** (※音量注意※ [youtube.com/watch?v=rYA4m5ZfhYA])").addFiles(FileUpload.fromData(new File(filePath))).queue();
            } else if (you.equals("scissors") && randomInt == 1) {
                event.reply(Mention + you + "あいこ!").queue();
            } else if (you.equals("scissors") && randomInt == 2) {
                event.reply(Mention + you + "YOU WIN! あなたは人生勝ち組だ").queue();
            } else if (you.equals("scissors")) {
                event.reply(Mention + you + "**Y O U L O S E** (※音量注意※ [youtube.com/watch?v=rYA4m5ZfhYA])").addFiles(FileUpload.fromData(new File(filePath))).queue();
            } else if (you.equals("paper") && randomInt == 2) {
                event.reply(Mention + you + "あいこ!").queue();
            } else if (you.equals("paper") && randomInt == 0) {
                event.reply(Mention + you + "YOU WIN! あなたは人生勝ち組だ").queue();
            } else if (you.equals("paper")) {
                event.reply(Mention + you + "**Y O U L O S E** (※音量注意※ [youtube.com/watch?v=rYA4m5ZfhYA])").addFiles(FileUpload.fromData(new File(filePath))).queue();
            }
        }
    }
}
