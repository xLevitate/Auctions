package me.levitate.auctions.auction;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

@Getter
public class AuctionItem {
    private final ItemStack item;
    private final OfflinePlayer seller;
    private final int price;

    public AuctionItem(ItemStack item, OfflinePlayer seller, int price) {
        this.item = item;
        this.seller = seller;
        this.price = price;
    }
}
