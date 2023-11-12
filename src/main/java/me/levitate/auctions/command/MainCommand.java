package me.levitate.auctions.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import lombok.AllArgsConstructor;
import me.levitate.auctions.auction.AuctionItem;
import me.levitate.auctions.auction.AuctionManager;
import me.levitate.auctions.config.Configuration;
import me.levitate.auctions.menu.AuctionMenu;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("auction|ah|auctions|auctionhouse")
@AllArgsConstructor
public class MainCommand extends BaseCommand {
    private final Configuration config;
    private final AuctionManager auctionManager;

    @Default
    @CommandPermission("auctions.use")
    public void onCommand(CommandSender sender) {
        final Player player = (Player) sender;

        new AuctionMenu(config, auctionManager).open(player);
    }

    @Subcommand("sell")
    @CommandPermission("auctions.sell")
    @Syntax("<price>")
    public void onSell(CommandSender sender, int price) {
        final Player player = (Player) sender;

        if (price <= 0) {
            config.sendMessage(player, "price-low");
            return;
        }

        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            config.sendMessage(player, "no-item");
            return;
        }

        final AuctionItem auctionItem = new AuctionItem(item, player, price);

        auctionManager.addItem(auctionItem);
        config.sendMessage(player, "item-listed");
    }
}
