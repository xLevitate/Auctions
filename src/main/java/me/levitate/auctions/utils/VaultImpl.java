package me.levitate.auctions.utils;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class VaultImpl {
    private final Plugin plugin;
    private Economy econ;

    public VaultImpl(Plugin plugin) {
        this.plugin = plugin;

        if (!setupEconomy()) {
            Bukkit.getLogger().severe("Failed to setup economy.");
        }
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return true;
    }
}