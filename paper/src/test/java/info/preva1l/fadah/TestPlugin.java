package info.preva1l.fadah;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import info.preva1l.trashcan.plugin.BasePlugin;
import info.preva1l.trashcan.plugin.annotations.PluginDisable;
import info.preva1l.trashcan.plugin.annotations.PluginEnable;
import info.preva1l.trashcan.plugin.annotations.PluginLoad;
import org.bukkit.command.CommandSender;

/**
 * Created on 25/04/2025
 *
 * @author Preva1l
 */
public class TestPlugin extends BasePlugin {
    public static TestPlugin instance;

    private final BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(this);

    boolean loaded = false;
    boolean enabled = false;
    boolean disabled = false;

    public TestPlugin() {
        instance = this;
    }

    @PluginLoad
    public void load() {
        System.out.println("Plugin load");
        loaded = true;
    }

    @PluginEnable
    public void enable() {
        System.out.println("Plugin enable");
        enabled = true;
    }

    @PluginDisable
    public void disable() {
        System.out.println("Plugin disable");
        disabled = true;
    }

    @Override
    public BukkitCommandManager<?> getCommandManager() {
        return commandManager;
    }
}
