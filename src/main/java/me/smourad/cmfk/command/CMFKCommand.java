package me.smourad.cmfk.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class CMFKCommand extends Command {

    protected CMFKCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            execute(player, args);
        } else {
            sender.sendMessage(Component.text("Cette commande ne peut être exécutée que par un joueur !"));
        }

        return false;
    }

    public abstract void execute(Player player, String[] args);

}
