package me.smourad.cmfk.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import me.smourad.cmfk.event.StartGameEvent;
import me.smourad.cmfk.game.team.GameState;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

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

    public boolean isNotStarted() {
        return Set.of(GameState.WAITING, GameState.STARTING).contains(state);
    }

    public void start() {
        if (Objects.equals(state, GameState.WAITING)) {
            state = GameState.STARTING;

            new BukkitRunnable() {

                private int i = 3;

                @Override
                public void run() {
                    if (i > 0) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            TextComponent main = Component.text("La partie commence", NamedTextColor.YELLOW);
                            TextComponent sub = Component.text(String.format("dans %d secondes", i--), NamedTextColor.GOLD);
                            player.showTitle(Title.title(main, sub, Title.Times.times(
                                    Duration.ZERO, Duration.ofMillis(1_100), Duration.ZERO
                            )));
                            player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP.key(), Sound.Source.AMBIENT, 1, 1),
                                    Sound.Emitter.self()
                            );
                        }
                    } else {
                        plugin.getServer().getPluginManager().callEvent(new StartGameEvent());
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
        }
    }

}
