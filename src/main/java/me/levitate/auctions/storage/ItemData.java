package me.levitate.auctions.storage;

import me.levitate.auctions.auction.AuctionItem;
import me.levitate.auctions.auction.AuctionManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ItemData {
    private final AuctionManager auctionManager;

    private final File dataFile;
    private FileConfiguration dataConfig;

    public ItemData(JavaPlugin plugin, AuctionManager auctionManager) {
        this.auctionManager = auctionManager;
        this.dataFile = new File(plugin.getDataFolder(), "items.yml");

        // Create the data file if it doesn't exist
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create items.yml file.");
                e.printStackTrace();
            }
        }

        // Load the items on startup
        loadItems();

        // Save and then load the data every 2 minutes.
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::reloadData, 2400L, 2400L);
    }

    public void loadItems() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        auctionManager.auctionItems.clear();

        ConfigurationSection itemsSection = dataConfig.getConfigurationSection("items");
        if (itemsSection != null) {
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                if (itemSection != null) {
                    ItemStack item = itemSection.getItemStack("item");
                    String sellerUUID = itemSection.getString("seller");
                    OfflinePlayer seller = Bukkit.getOfflinePlayer(UUID.fromString(sellerUUID));
                    int price = itemSection.getInt("price");

                    AuctionItem auctionItem = new AuctionItem(item, seller, price);
                    auctionManager.addItem(auctionItem);
                }
            }
        }
    }

    public void saveItems() {
        ConfigurationSection itemsSection = dataConfig.createSection("items");

        // Get the items from the manager and save them.
        int index = 1;
        for (AuctionItem auctionItem : auctionManager.auctionItems) {
            String key = "item" + index;
            ConfigurationSection itemSection = itemsSection.createSection(key);

            itemSection.set("item", auctionItem.getItem());
            itemSection.set("seller", auctionItem.getSeller().getUniqueId().toString());
            itemSection.set("price", auctionItem.getPrice());

            index++;
        }

        // Save the data file
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save items.yml file.");
            e.printStackTrace();
        }
    }

    public void reloadData() {
        saveItems();
        loadItems();
    }
}
