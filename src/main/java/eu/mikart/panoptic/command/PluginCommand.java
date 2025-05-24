package eu.mikart.panoptic.command;

import eu.mikart.panoptic.PanopticPlugin;
import net.william278.uniform.Command;
import net.william278.uniform.Permission;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class PluginCommand extends Command {

    protected final PanopticPlugin plugin;

    protected PluginCommand(@NotNull String name, @NotNull List<String> aliases, @NotNull Permission.Default defPerm,
                            @NotNull Command.ExecutionScope scope, @NotNull PanopticPlugin plugin) {
        super(name, aliases, "", new Permission(createPermission(name), defPerm), scope);
        this.plugin = plugin;
    }

    @NotNull
    private static String createPermission(@NotNull String name, @NotNull String... sub) {
        return "panoptic.command." + name + (sub.length > 0 ? "." + String.join(".", sub) : "");
    }

    @NotNull
    protected String getPermission(@NotNull String... sub) {
        return createPermission(this.getName(), sub);
    }

    @NotNull
    protected CommandSender adapt(net.william278.uniform.CommandUser user) {
        return user.getUuid() == null ? plugin.getServer().getConsoleSender() : Objects.requireNonNull(plugin.getServer().getPlayer(user.getUuid()));
    }

    public enum Type {

        PANOPTIC_COMMAND(PanopticCommand::new);

        public final Function<PanopticPlugin, PluginCommand> commandSupplier;

        Type(@NotNull Function<PanopticPlugin, PluginCommand> supplier) {
            this.commandSupplier = supplier;
        }

        @NotNull
        public static PluginCommand[] create(@NotNull PanopticPlugin plugin) {
            return Arrays.stream(values()).map(type -> type.supply(plugin))
                    .filter(command -> !plugin.getSettings().isCommandDisabled(command))
                    .toArray(PluginCommand[]::new);
        }

        @NotNull
        public PluginCommand supply(@NotNull PanopticPlugin plugin) {
            return commandSupplier.apply(plugin);
        }

    }

}