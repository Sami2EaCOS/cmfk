package me.smourad.cmfk.config;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.smourad.cmfk.generator.OrePopulator;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginModule extends AbstractModule {

    private final JavaPlugin plugin;

    public PluginModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(plugin);

        configureEagerListeners();
    }

    private void configureEagerListeners() {
        bind(OrePopulator.class).asEagerSingleton();
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

}
