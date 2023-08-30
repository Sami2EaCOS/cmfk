package me.smourad.cmfk;

import com.google.inject.Injector;
import me.smourad.cmfk.config.PluginModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class CMFK extends JavaPlugin {

    @Override
    public void onEnable() {
        Injector injector = new PluginModule(this).createInjector();
        injector.injectMembers(this);
    }

}
