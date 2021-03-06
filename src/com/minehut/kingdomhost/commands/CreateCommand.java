package com.minehut.kingdomhost.commands;

import com.minehut.commons.common.chat.C;
import com.minehut.core.Core;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.kingdomhost.KingdomHost;
import com.minehut.kingdomhost.offline.OfflineServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 4/11/15.
 */
public class CreateCommand extends Command {

    public CreateCommand(JavaPlugin plugin) {
        super(plugin, "create", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        //Todo: event servers (allow multiple server ownership)

        if (args.size() == 1) {

            ArrayList<OfflineServer> ownedServers = KingdomHost.getPlugin().getServerManager().getServer(player);
            if (ownedServers.isEmpty()) {
                KingdomHost.getPlugin().getServerManager().createServer(player, args.get(0));
            } else {
                Rank rank = Core.getInstance().getPlayerInfo(player).getRank();
                if (rank.has(null, Rank.Ref, false)) {
                    if(ownedServers.size() > 3) {
                        player.sendMessage("");
                        player.sendMessage("You have reached your maximum of " + C.aqua + "three servers");
                        player.sendMessage("");
                    } else {
                        KingdomHost.getPlugin().getServerManager().createServer(player, args.get(0));
                    }
                } else if (rank.has(null, Rank.Champ, false)) {
                    if(ownedServers.size() > 2) {
                        player.sendMessage("");
                        player.sendMessage("You have reached your maximum of " + C.aqua + "two servers");
                        player.sendMessage("");
                    } else {
                        KingdomHost.getPlugin().getServerManager().createServer(player, args.get(0));
                    }
                } else {
                    player.sendMessage("");
                    player.sendMessage("You already have a server called " + C.aqua + ownedServers.get(0).getKingdomName());
                    player.sendMessage("");
                }
            }
        } else {
            player.sendMessage("Please use the format " + C.aqua + "/create (name)");
        }

        return false;
    }
}
