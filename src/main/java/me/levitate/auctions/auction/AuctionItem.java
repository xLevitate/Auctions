package me.levitate.auctions.auction;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

@Getter
public class AuctionItem {
    private final ItemStack item;

    // This item contains the custom lore with the price, etc
    private final ItemStack modifiedItem;

    private final OfflinePlayer seller;
    private final int price;

    public AuctionItem(ItemStack item, ItemStack modifiedItem, OfflinePlayer seller, int price) {
        this.item = item;
        this.modifiedItem = modifiedItem;
        this.seller = seller;
        this.price = price;
    }
}
