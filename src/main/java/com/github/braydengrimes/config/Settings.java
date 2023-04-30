package com.github.braydengrimes.config;

import com.github.braydengrimes.ChatFilter;

import java.util.List;

public class Settings {
    private final ChatFilter plugin;

    public Settings(ChatFilter plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
    }

    public List<String> getBannedWords() {
        return plugin.getConfig().getStringList("swears");
    }
}
