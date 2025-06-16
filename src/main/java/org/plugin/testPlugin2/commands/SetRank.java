package org.plugin.testPlugin2.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.plugin.testPlugin2.events.PlayerSpawn;

public class SetRank implements CommandExecutor {

    private final PlayerSpawn playerSpawn;

    public SetRank(PlayerSpawn playerSpawn) {
        this.playerSpawn = playerSpawn;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }

        Player p = (Player) sender;

        String rank = PlayerSpawn.ranks.get(p.getName());
        if (!"Admin".equalsIgnoreCase(rank) && !"VIP".equalsIgnoreCase(rank)) {
            p.sendMessage("This command can only be used by Admins or VIPs.");
            return true;
        }

        if (args.length < 2) {
            p.sendMessage("Usage: /set_rank <player> <rank>");
            return false;
        }

        String targetName = args[0];
        String targetRank = args[1];
        Player target = Bukkit.getPlayerExact(targetName);

        if (target != null && target.isOnline()) {
            PlayerSpawn.ranks.put(targetName, targetRank);
            playerSpawn.saveRanks(); // ✅ Save after setting
            p.sendMessage("Set " + targetName + "'s rank to " + targetRank);
        } else {
            p.sendMessage("Player not found or not online.");
        }

        return true;
    }

}

