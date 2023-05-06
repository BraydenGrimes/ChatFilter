package com.github.braydengrimes.listener;

import com.github.braydengrimes.ChatFilter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

public class ChatListeners implements Listener {

    private final Map<UUID, Long> lastMessageTimestamps = new HashMap<>();

    private boolean hasCooldown(UUID playerId) {
        final long CHAT_COOLDOWN_TIME = 3000; // Adjust the Cooldown time as needed (in milliseconds)

        Long lastTimestamp = lastMessageTimestamps.get(playerId);
        if (lastTimestamp == null) {
            lastMessageTimestamps.put(playerId, System.currentTimeMillis());
            return false;
        }

        long timeSinceLastMessage = System.currentTimeMillis() - lastTimestamp;

        if (timeSinceLastMessage < CHAT_COOLDOWN_TIME) {
            return true;
        } else {
            lastMessageTimestamps.put(playerId, System.currentTimeMillis());
            return false;
        }
    }


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
        UUID playerId = event.getPlayer().getUniqueId();

        if (hasCooldown(playerId)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cPlease wait before sending another message.");
            return;
        }

            if (containsBannedWord(message)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cYour message contains a banned word. Please refrain from using profanity.");
        }

            if (isCapsLock(message)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cPlease do not use excessive capital letters");
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
