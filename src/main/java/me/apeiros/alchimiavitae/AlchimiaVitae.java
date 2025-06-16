package me.apeiros.alchimiavitae;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.impl.PlatformScheduler;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.metrics.bukkit.Metrics;

import me.apeiros.alchimiavitae.setup.Setup;
import net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater;

import java.util.logging.Level;

/**
 * Main class
 */
public class AlchimiaVitae extends AbstractAddon {

    private static AlchimiaVitae instance;
    private static FoliaLib foliaLib;

    public AlchimiaVitae() {
        super("SlimefunGuguProject", "AlchimiaVitae", "master", "options.auto-update");
    }

    @Override
    public void enable() {
        // Set instance
        instance = this;
        foliaLib = new FoliaLib(this);

        if (!getServer().getPluginManager().isPluginEnabled("GuizhanLibPlugin")) {
            getLogger().log(Level.SEVERE, "本插件需要 鬼斩前置库插件(GuizhanLibPlugin) 才能运行!");
            getLogger().log(Level.SEVERE, "从此处下载: https://50L.cc/gzlib");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("Build")) {
            GuizhanUpdater.start(this, getFile(), "SlimefunGuguProject", "AlchimiaVitae", "master");
        }

        // Set up items and listeners
        Setup.setup(this);

        // bStats
        new Metrics(this, 15139);
    }

    @Override
    public void disable() {
        // Set instance to null
        instance = null;
    }

    public static AlchimiaVitae i() {
        return instance;
    }

    public static PlatformScheduler getScheduler() {
        return foliaLib.getScheduler();
    }

}
