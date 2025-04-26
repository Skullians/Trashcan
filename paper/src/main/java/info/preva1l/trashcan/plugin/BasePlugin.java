package info.preva1l.trashcan.plugin;

import info.preva1l.trashcan.Version;
import info.preva1l.trashcan.flavor.Flavor;
import info.preva1l.trashcan.flavor.FlavorOptions;
import info.preva1l.trashcan.flavor.PackageIndexer;
import info.preva1l.trashcan.flavor.binder.defaults.DefaultManagersBinder;
import info.preva1l.trashcan.flavor.binder.defaults.DefaultPluginBinder;
import info.preva1l.trashcan.plugin.annotations.PluginDisable;
import info.preva1l.trashcan.plugin.annotations.PluginEnable;
import info.preva1l.trashcan.plugin.annotations.PluginLoad;
import info.preva1l.trashcan.plugin.annotations.PluginReload;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The base plugin class to extend if you want the full function of Trashcan.
 * Obviously you don't need to use this and can cherry-pick its contents if need be.
 * <p>
 * Created on 11/04/2025
 *
 * @author Preva1l
 */
@SuppressWarnings("UnstableApiUsage")
public abstract class BasePlugin extends JavaPlugin {
    protected final Version currentVersion =
            Version.fromString(getPluginMeta() == null ? "1.0.0" : getPluginMeta().getVersion());

    protected Flavor flavor;
    protected PackageIndexer packageIndexer;

    @Override
    public final void onLoad() {
        // Resolve all plugin annotations here, preventing class not found exceptions on shutdown
        try {
            Class.forName("info.preva1l.trashcan.plugin.annotations.PluginLoad");
            Class.forName("info.preva1l.trashcan.plugin.annotations.PluginEnable");
            Class.forName("info.preva1l.trashcan.plugin.annotations.PluginDisable");
            Class.forName("info.preva1l.trashcan.plugin.annotations.PluginReload");
        } catch (ClassNotFoundException ignored) {}

        this.flavor = Flavor.create(
                this.getClass(),
                new FlavorOptions(
                        this.getLogger(),
                        this.getClass().getPackageName()
                )
        );

        this.packageIndexer = flavor.reflections;

        this.packageIndexer.invokeMethodsAnnotatedWith(PluginLoad.class);

        this.flavor.inherit(new DefaultPluginBinder(this))
                .inherit(new DefaultManagersBinder(this));
    }

    @Override
    public final void onEnable() {
        this.packageIndexer.invokeMethodsAnnotatedWith(PluginEnable.class);

        flavor.startup();
    }

    @Override
    public final void onDisable() {
        this.packageIndexer.invokeMethodsAnnotatedWith(PluginDisable.class);

        flavor.close();
    }

    /**
     * Reloads your plugin, runs any methods annotated with @PluginReload
     */
    public final void reload() {
        this.packageIndexer.invokeMethodsAnnotatedWith(PluginReload.class);
    }

    public final Version getCurrentVersion() {
        return currentVersion;
    }
}
