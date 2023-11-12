package me.levitate.auctions.auction;

import me.levitate.auctions.config.Configuration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public class AuctionManager {
    public final Set<AuctionItem> auctionItems = new LinkedHashSet<>();

    private final Configuration config;
    private final Economy economy;

    public AuctionManager(Configuration config, Economy economy) {
        this.config = config;
        this.economy = economy;
    }

    public void addItem(AuctionItem item) {
        auctionItems.add(item);
    }

    public void removeItem(AuctionItem item) {
        auctionItems.remove(item);
    }

    public void purchaseItem(Player player, AuctionItem item) {
        if (!auctionItems.contains(item)) {
            config.sendMessage(player, "already-purchased");
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            config.sendMessage(player, "inventory-full");
            return;
        }

        if (economy.getBalance(player) <= item.getPrice()) {
            config.sendMessage(player, "no-balance");
            return;
        }

        if (economy.withdrawPlayer(player, item.getPrice()).transactionSuccess()) {
            player.getInventory().addItem(item.getItem());
            auctionItems.remove(item);
        }
    }
}
