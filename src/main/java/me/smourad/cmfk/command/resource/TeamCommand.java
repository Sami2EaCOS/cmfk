package me.smourad.cmfk.command.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.smourad.cmfk.command.CMFKCommand;
import me.smourad.cmfk.game.team.TheGatherer;
import org.bukkit.entity.Player;

@Singleton
public class TeamCommand extends CMFKCommand {

    private final TheGatherer gatherer;

    @Inject
    public TeamCommand(TheGatherer gatherer) {
        super("choose-team");
        this.gatherer = gatherer;
    }

    @Override
    public void execute(Player player, String[] args) {
        gatherer.openTeamSelector(player);
    }

}
