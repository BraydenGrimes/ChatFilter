package com.github.braydengrimes;

import com.github.braydengrimes.config.Settings;
import com.github.braydengrimes.listener.ChatListeners;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatFilter extends JavaPlugin {

    private Settings settings;
    @Override
    public void onEnable() {
        settings = new Settings(this);
        getServer().getPluginManager().registerEvents(new ChatListeners(this), this);
    }

    public Settings getSettings() {
        return settings;
    }
}
