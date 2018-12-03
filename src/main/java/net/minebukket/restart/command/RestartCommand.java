package net.minebukket.restart.command;

import net.minebukket.restart.RestartPlugin;
import net.minebukket.restart.util.DateUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import us.sparknetwork.cm.annotation.Command;
import us.sparknetwork.cm.command.arguments.CommandContext;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class RestartCommand {

    @Command(names = {"restartnow", "reiniciarahora"}, permission = "restart.command.restartnow", max = 0, usage = "Usage: /<command>")
    public static boolean restartNow(CommandSender sender, CommandContext args) {
        RestartPlugin.getPlugin().getRestartHandler().restartNow(true);
        return true;
    }

    @Command(names = {"addrestarttime", "anadirtiempoalreinicio"}, permission = "restart.command.addrestarttime", max = 1, min = 1, usage = "Usage: /<command>")
    public static boolean addRestartTime(CommandSender sender, CommandContext args) {

        long time = DateUtil.parseRelativeHumanTime(args.getArgument(0));

        if (time < 1000) {
            sender.sendMessage(ChatColor.RED + "Especifica una cantidad de tiempo mayor a 1 segundo.");
            return true;
        }

        RestartPlugin.getPlugin().getRestartHandler().addTimeToNextRestart(time, ChronoUnit.MILLIS);

        long millisBeforeRestart = Instant.now().until(RestartPlugin.getPlugin().getRestartHandler().getNextRestart().toInstant(), ChronoUnit.MILLIS);

        sender.sendMessage(ChatColor.YELLOW + String.format("El servidor se reiniciara ahora en %1$s.", DateUtil.getHumanReadableDate(millisBeforeRestart)));

        return true;
    }

    @Command(names = {"nextrestart", "siguientereinicio"}, permission = "restart.command.nextrestart", max = 0, usage = "Usage: /<command>")
    public static boolean nextRestart(CommandSender sender, CommandContext args) {
        long millisBeforeRestart = Instant.now().until(RestartPlugin.getPlugin().getRestartHandler().getNextRestart().toInstant(), ChronoUnit.MILLIS);

        sender.sendMessage(ChatColor.YELLOW + String.format("El servidor se reiniciara en %1$s.", DateUtil.getHumanReadableDate(millisBeforeRestart)));
        return true;
    }
}
