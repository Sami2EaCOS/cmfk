package me.smourad.cmfk.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

@Singleton
public final class TheCommandWatcher {

    @Inject
    public TheCommandWatcher(JavaPlugin plugin, Reflections reflections, Injector injector) {
        Set<Class<? extends CMFKCommand>> commands = reflections.getSubTypesOf(CMFKCommand.class);

        try {
            for (Class<? extends CMFKCommand> command : commands) {
                CMFKCommand cmd = injector.getInstance(command);
                Bukkit.getServer().getCommandMap().register(cmd.getName(), plugin.getName(), cmd);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

