package me.levitate.auctions.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.levitate.auctions.auction.AuctionManager;
import me.levitate.auctions.config.Configuration;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;

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

        // TODO: Make a foreach that loops through all the auction items and adds them here.
        auctionManager.auctionItems.forEach((item) -> {
            GuiItem guiItem = ItemBuilder.from(item.getModifiedItem()).asGuiItem(e -> {
                Player target = (Player) e.getWhoClicked();

                auctionManager.purchaseItem(target, item);
                target.closeInventory();
            });

            addItem(guiItem);
        });

        addButtons();
    }

    private void addButtons() {
        // TODO: Add the buttons
    }
}
