package com.github.braydengrimes.listener;

import com.github.braydengrimes.ChatFilter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.List;

public class ChatListeners implements Listener {
    private boolean isCapsLock(String message) {
        int capitalLetters = 0;
        int totalLetters = 0;

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                totalLetters++;
                if (Character.isUpperCase(c)) {
                    capitalLetters++;
                }
            }
        }

        if (totalLetters == 0) {
            return false;
        }

        double capitalLetterPercentage = ((double) capitalLetters) / totalLetters;
        return capitalLetterPercentage > 0.8; // Adjust the threshold as needed
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();


            if (containsBannedWord(message)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Your message contains a banned word. Please refrain from using profanity.");
        }

            if (isCapsLock(message)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Please do not use excessive capital letters");
            }
    }

        private final ChatFilter plugin;

    public ChatListeners(ChatFilter plugin) {
        this.plugin = plugin;
    }

    private List<String> getBannedWords() {
        return plugin.getSettings().getBannedWords();
    }

    private boolean containsBannedWord(String message) {
        List<String> bannedWords = getBannedWords();
        String[] words = message.split("//s+");
        for (String word : words) {
            if (bannedWords.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
