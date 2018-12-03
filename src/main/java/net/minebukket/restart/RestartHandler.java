package net.minebukket.restart;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import net.minebukket.restart.event.ServerRestartEvent;
import net.minebukket.restart.util.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class RestartHandler {

    private RestartPlugin plugin;

    private BukkitTask scheduledRestartTask;

    private Date nextRestartDate;

    public RestartHandler(RestartPlugin plugin, Date nextRestartDate) {
        this.plugin = plugin;

        this.nextRestartDate = nextRestartDate;

        this.initializeScheduler();
    }

    private void initializeScheduler() {
        this.scheduledRestartTask = new BukkitRunnable() {

            @Override
            public void run() {
                if (Date.from(Instant.now()).after(nextRestartDate)) {

                    String[] messages = {
                            ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH.toString() + Strings.repeat("-", 24),
                            ChatColor.YELLOW + "El server se reiniciara ahora.",
                            ChatColor.YELLOW + "Todos los jugadores seran movidos al lobby.",
                            ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH.toString() + Strings.repeat("-", 24)
                    };

                    Bukkit.broadcastMessage(Joiner.on("\n").join(messages));

                    // Move all players to a random lobby
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        plugin.sendPlayerToServer(player, plugin.getRandomLobby());
                    });

                    // Send server restart listener and wait 1 second
                    Bukkit.getPluginManager().callEvent(new ServerRestartEvent(plugin.getServer()));

                    // Stop the server synchronaly
                    Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.getServer().shutdown(), 20L);
                    return;
                }

                long secondsBeforeRestart = Instant.now().until(nextRestartDate.toInstant(), ChronoUnit.SECONDS);

                if (secondsBeforeRestart <= 10) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + String.format("El servidor se reiniciara en %1$s.", DateUtil.getHumanReadableDate(secondsBeforeRestart * 1000)));
                }

                if (PluginConfiguration.BROADCAST_TIME.contains(secondsBeforeRestart)) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + String.format("El servidor se reiniciara en %1$s.", DateUtil.getHumanReadableDate(secondsBeforeRestart * 1000)));
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public Date getNextRestart() {
        return nextRestartDate;
    }

    public void restartNow(boolean broadcastForceRestart) {
        if (broadcastForceRestart) {
            Bukkit.broadcastMessage(ChatColor.RED + "Reinicio forzoso activado!");
        }

        this.restartIn(11, ChronoUnit.SECONDS);
    }

    public void restartIn(long time, TemporalUnit timeUnit) {
        nextRestartDate = Date.from(Instant.now().plus(time, timeUnit));
    }

    public void addTimeToNextRestart(long time, TemporalUnit timeUnit) {
        Instant nextRestartDate = this.nextRestartDate.toInstant();

        nextRestartDate.plus(time, timeUnit);

        this.nextRestartDate = Date.from(nextRestartDate);
    }
}
