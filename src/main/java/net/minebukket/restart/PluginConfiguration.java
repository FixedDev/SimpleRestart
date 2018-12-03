package net.minebukket.restart;

import com.google.common.collect.ImmutableList;
import net.minebukket.restart.util.Config;
import net.minebukket.restart.util.DateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PluginConfiguration {

    public static List<String> LOBBIES = ImmutableList.of("lobby");

    /**
     * This time is in seconds
     */
    public static long RESTART_TIME = TimeUnit.HOURS.toSeconds(6);

    public static List<Long> BROADCAST_TIME = ImmutableList.of(TimeUnit.HOURS.toSeconds(1), TimeUnit.MINUTES.toSeconds(30), TimeUnit.MINUTES.toSeconds(15), TimeUnit.MINUTES.toSeconds(10), TimeUnit.MINUTES.toSeconds(1), 30L);

    private final RestartPlugin plugin;
    private final Config configuration;

    public PluginConfiguration(RestartPlugin plugin) {
        this.plugin = plugin;
        this.configuration = new Config(plugin, "config");

        this.loadConfiguration();
    }

    public void loadConfiguration() {
        LOBBIES = configuration.getStringList("lobbies");
        RESTART_TIME = DateUtil.parseRelativeHumanTime(configuration.getString("restart-time")) / 1000;
        BROADCAST_TIME = configuration.getStringList("broadcast-time").stream().map(DateUtil::parseRelativeHumanTime).map(relativeTime -> relativeTime / 1000).collect(Collectors.toList());
    }

    public void saveConfiguration() {
        configuration.set("lobbies", LOBBIES);
        configuration.set("restart-time", DateUtil.getRelativeHumanTime(RESTART_TIME * 1000));
        configuration.set("broadcast-time", BROADCAST_TIME.stream().map(relativeTime -> relativeTime / 1000).map(DateUtil::getRelativeHumanTime).collect(Collectors.toList()));
    }
}
