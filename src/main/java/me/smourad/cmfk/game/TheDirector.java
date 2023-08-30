package me.smourad.cmfk.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import me.smourad.cmfk.team.GameState;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Singleton
public class TheDirector implements Listener {

    private final JavaPlugin plugin;

    @Getter
    @Setter
    private GameState state = GameState.WAITING;

    @Inject
    public TheDirector(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void launch() {
        if (Objects.equals(GameState.WAITING, state)) {
            state = GameState.IN_PROGRESS;
        }
    }

}
