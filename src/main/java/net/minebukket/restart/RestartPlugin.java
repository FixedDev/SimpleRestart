package net.minebukket.restart;

import com.google.common.base.Strings;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.minebukket.restart.command.RestartCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.sparknetwork.cm.CommandHandler;
import us.sparknetwork.cm.handlers.CommandMapHandler;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RestartPlugin extends JavaPlugin {

    @Getter
    private static RestartPlugin plugin;
    @Getter
    private RestartHandler restartHandler;

    @Override
    public void onEnable() {
        plugin = this;

        new PluginConfiguration(this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        restartHandler = new RestartHandler(this, Date.from(Instant.now().plus(PluginConfiguration.RESTART_TIME, ChronoUnit.SECONDS)));

        CommandHandler handler = new CommandMapHandler(this);

        handler.registerCommandClass(RestartCommand.class);
    }

    public void sendPlayerToServer(Player player, String serverName) {
        if (player == null) {
            throw new IllegalArgumentException("The player is null!");
        }

        if (Strings.nullToEmpty(serverName).trim().isEmpty()) {
            throw new IllegalArgumentException("The server is empty!");
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(serverName);

        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public String getRandomLobby() {
        List<String> lobbies = new ArrayList<>(PluginConfiguration.LOBBIES);

        Collections.shuffle(lobbies);

        return lobbies.get(0);
    }
}
