package me.levitate.auctions.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.levitate.auctions.auction.AuctionItem;
import me.levitate.auctions.auction.AuctionManager;
import me.levitate.auctions.config.Configuration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuctionMenu extends PaginatedGui {
    private static final GuiItem filler = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem();

    private final Configuration config;
    private final AuctionManager auctionManager;

    public AuctionMenu(Configuration config, AuctionManager auctionManager) {
        super(6, 28, "Auction Menu", InteractionModifier.VALUES);

        this.config = config;
        this.auctionManager = auctionManager;

        drawMenu();
    }

    private void drawMenu() {
        getFiller().fillBorder(filler);

        // Loops through all the items in the auction manager and adds them to the menu.
        auctionManager.auctionItems.forEach((item) -> {
            GuiItem guiItem = ItemBuilder.from(config.modifyItem(item)).asGuiItem(e -> {
                Player target = (Player) e.getWhoClicked();

                auctionManager.purchaseItem(target, item);
                target.closeInventory();
            });

            addItem(guiItem);
        });

        addButtons();
    }

    private void addButtons() {
        // Previous page button
        if (getPrevPageNum() != getCurrentPageNum())
            setItem(6, 4, ItemBuilder.from(config.previousItemStack).asGuiItem(e -> {
                previous();
                addButtons();
                update();
            }));
        else setItem(6, 4, filler);

        // Next page button
        if (getNextPageNum() != getCurrentPageNum())
            setItem(6, 6, ItemBuilder.from(config.nextItemStack).asGuiItem(e -> {
                next();
                addButtons();
                update();
            }));
        else setItem(6, 6, filler);
    }
}
