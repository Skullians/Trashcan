package info.preva1l.trashcan.flavor.binder.defaults;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.CommandManager;
import info.preva1l.trashcan.flavor.binder.FlavorBinderContainer;
import info.preva1l.trashcan.plugin.BasePlugin;

public class DefaultManagersBinder extends FlavorBinderContainer {
    private final BasePlugin plugin;

    public DefaultManagersBinder(BasePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void populate() {
        bind(plugin.commandManager())
                .to(BukkitCommandManager.class)
                .to(CommandManager.class)
                .bind();
    }
}