package me.levitate.auctions.auction;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

@Getter
public class AuctionItem {
    private final ItemStack item;
    private final OfflinePlayer player;
    private final int price;

    public AuctionItem(ItemStack item, OfflinePlayer player, int price) {
        this.item = item;
        this.player = player;
        this.price = price;
    }
}
