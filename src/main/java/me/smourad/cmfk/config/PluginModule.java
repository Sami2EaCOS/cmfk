package me.smourad.cmfk.config;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import me.smourad.cmfk.factory.InventoryFactory;
import me.smourad.cmfk.factory.InventorySlotFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public final class PluginModule extends AbstractModule {

    private static final String MAIN_PACKAGE = "me.smourad.cmfk";

    private final JavaPlugin plugin;

    public PluginModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(plugin);

        Reflections reflections = new Reflections(MAIN_PACKAGE);

        configureUtils(reflections);
        configureFactories();
        configureEagerListeners(reflections);
    }

    private void configureUtils(Reflections reflections) {
        bind(Reflections.class).toInstance(reflections);
    }

    private void configureEagerListeners(Reflections reflections) {
        Set<Class<?>> singletons = reflections.getTypesAnnotatedWith(Singleton.class);

        try {
            for (Class<?> singleton : singletons) {
                bind(singleton).asEagerSingleton();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void configureFactories() {
        install(new FactoryModuleBuilder().build(InventoryFactory.class));
        install(new FactoryModuleBuilder().build(InventorySlotFactory.class));
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

}
