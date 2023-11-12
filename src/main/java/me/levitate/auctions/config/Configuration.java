package me.levitate.auctions.config;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private final Plugin plugin;
    private FileConfiguration fileConfiguration;

    @Getter
    private final Map<String, String> messages = new LinkedHashMap<>();

    // Main Menu
    public String menuTitle;
    public int menuRows;

    // Previous Page
    public ItemStack previousItemStack;
    public Material previousItem;
    public String previousName = "";
    public List<String> previousLore;

    // Next Page
    public ItemStack nextItemStack;
    public Material nextItem;
    public String nextName = "";
    public List<String> nextLore;

    // Back
    public ItemStack backItemStack;
    public Material backItem;
    public String backName = "";
    public List<String> backLore;

    public Configuration(Plugin plugin, FileConfiguration fileConfiguration) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;

        reloadConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.saveDefaultConfig();

        this.fileConfiguration = plugin.getConfig();
        fileConfiguration.options().copyDefaults(true);
        plugin.saveConfig();

        loadMessages();
        //loadSettings();
    }

    public void loadMessages() {
        messages.clear();

        for (String key : fileConfiguration.getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, fileConfiguration.getString("messages." + key));
        }
    }

    public void loadSettings() {
        ConfigurationSection mainMenu = fileConfiguration.getConfigurationSection("main-menu");
        if (mainMenu == null) {
            Bukkit.getLogger().severe("Couldn't find main-menu section in the config.");
            return;
        }

        previousItem = Material.matchMaterial(mainMenu.getString("previous-material"));
        previousName = mainMenu.getString("previous-name");
        previousLore = mainMenu.getStringList("previous-lore");
        previousItemStack = createMenuItem(previousItem, previousName, previousLore);

        nextItem = Material.matchMaterial(mainMenu.getString("next-material"));
        nextName = mainMenu.getString("next-name");
        nextLore = mainMenu.getStringList("next-lore");
        nextItemStack = createMenuItem(nextItem, nextName, nextLore);

        backItem = Material.matchMaterial(mainMenu.getString("back-material"));
        backName = mainMenu.getString("back-name");
        backLore = mainMenu.getStringList("back-lore");
        backItemStack = createMenuItem(backItem, backName, backLore);
    }

    /**
     * Sends a formatted message to the player using MiniMessage.
     * @param player The player to send the message to
     * @param message The configuration message to send
     */
    public void sendMessage(Player player, String message) {
        player.sendMessage(MiniMessage.miniMessage().deserialize(messages.get(message)));
    }

    public ItemStack createMenuItem(Material material, String nameKey, List<String> loreKey) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        // Set the name of the item
        if (nameKey != null) {
            Component name = MiniMessage.miniMessage().deserialize(nameKey)
                    .decoration(TextDecoration.ITALIC, false);
            itemMeta.displayName(name);
        }

        // Set the lore of the item
        if (loreKey != null) {
            List<Component> translatedLore = loreKey.stream()
                    .map(line -> MiniMessage.miniMessage().deserialize(line).decoration(TextDecoration.ITALIC, false))
                    .toList();
            itemMeta.lore(translatedLore);
        }

        item.setItemMeta(itemMeta);

        return item;
    }
}