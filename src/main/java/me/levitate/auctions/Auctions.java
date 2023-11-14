package me.levitate.auctions;

import co.aikar.commands.PaperCommandManager;
import me.levitate.auctions.auction.AuctionManager;
import me.levitate.auctions.command.MainCommand;
import me.levitate.auctions.config.Configuration;
import me.levitate.auctions.storage.ItemData;
import me.levitate.auctions.utils.VaultImpl;

import org.bukkit.plugin.java.JavaPlugin;

public final class Auctions extends JavaPlugin {
    private ItemData itemData;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        final PaperCommandManager commandManager = new PaperCommandManager(this);
        final VaultImpl vault = new VaultImpl(this);

        final Configuration config = new Configuration(this, getConfig());

        final AuctionManager auctionManager = new AuctionManager(config, vault.getEcon());
        itemData = new ItemData(this, auctionManager);

        commandManager.registerCommand(new MainCommand(config, auctionManager));
    }

    @Override
    public void onDisable() {
        itemData.saveItems();
    }
}
