package info.preva1l.trashcan.plugin;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import info.preva1l.trashcan.flavor.Flavor;
import info.preva1l.trashcan.flavor.FlavorOptions;
import info.preva1l.trashcan.flavor.PackageIndexer;
import info.preva1l.trashcan.flavor.annotations.ServicesPackage;
import info.preva1l.trashcan.flavor.binder.defaults.DefaultManagersBinder;
import info.preva1l.trashcan.flavor.binder.defaults.DefaultPluginBinder;
import info.preva1l.trashcan.plugin.annotations.PluginDisable;
import info.preva1l.trashcan.plugin.annotations.PluginEnable;
import info.preva1l.trashcan.plugin.annotations.PluginLoad;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * The base plugin class to extend if you want the full function of Trashcan.
 * Obviously you don't need to use this and can cherry-pick its contents if need be.
 * <p>
 * Created on 11/04/2025
 *
 * @author Preva1l
 */
public abstract class BasePlugin extends JavaPlugin {
    protected Flavor flavor;
    protected PackageIndexer packageIndexer;

    private BukkitCommandManager<?> commandManager;

    @Override
    public final void onLoad() {
        var servicesAnnotation = getClass().getAnnotation(ServicesPackage.class);
        this.flavor = Flavor.create(
                this.getClass(),
                new FlavorOptions(
                        this.getLogger(),
                        null,
                        Arrays.stream(servicesAnnotation != null ? servicesAnnotation.value() : new String[0]).toList()
                )
        );

        this.packageIndexer = flavor.reflections;

        this.packageIndexer
                .getMethodsAnnotatedWith(PluginLoad.class)
                .forEach(it -> {
                    try {
                        it.invoke(this);
                    } catch (Exception e) {
                        getLogger().log(
                                Level.WARNING,
                                "Failed to run container part {} on PluginLoad",
                                it.getClass().getSimpleName()
                        );
                    }
                });

        this.flavor.inherit(new DefaultPluginBinder(this))
                .inherit(new DefaultManagersBinder(this));
    }

    @Override
    public final void onEnable() {
        this.packageIndexer
                .getMethodsAnnotatedWith(PluginEnable.class)
                .forEach(it -> {
                    try {
                        it.invoke(this);
                    } catch (Exception e) {
                        getLogger().log(
                                Level.WARNING,
                                "Failed to run container part {} on PluginEnable",
                                it.getClass().getSimpleName()
                        );
                    }
                });

        flavor.startup();
    }

    @Override
    public final void onDisable() {
        this.packageIndexer
                .getMethodsAnnotatedWith(PluginDisable.class)
                .forEach(it -> {
                    try {
                        it.invoke(this);
                    } catch (Exception e) {
                        getLogger().log(
                                Level.WARNING,
                                "Failed to run container part {} on PluginDisable",
                                it.getClass().getSimpleName()
                        );
                    }
                });

        flavor.close();
    }

    public final BukkitCommandManager<?> getCommandManager() {
        return commandManager;
    }

    public final <T> BukkitCommandManager<T> commandManager() {
        return (BukkitCommandManager<T>) commandManager;
    }
}
