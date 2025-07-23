package eu.mikart.panoptic.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import eu.mikart.panoptic.PanopticPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.uniform.BaseCommand;
import net.william278.uniform.Permission;

public class PanopticCommand extends PluginCommand {

    private final static AboutMenu aboutMenu;

    static {
        aboutMenu = AboutMenu.builder()
                .title(Component.text("Panoptic"))
                .version(PanopticPlugin.getInstance().getVersion())
                .description(Component.text("An extensive event handler."))
                .credits("Author", AboutMenu.Credit.of("ArikSquad").url("https://github.com/ariksquad"))
                .credits("Developer", AboutMenu.Credit.of("ArikSquad").url("https://github.com/ariksquad"))
                .buttons(AboutMenu.Link.of("https://discord.gg/m7E9qjc69J").text("Issues").icon("❌")
                        .color(TextColor.color(0xff9f0f)))
                .build();
    }

    protected PanopticCommand(@NotNull PanopticPlugin plugin) {
        super("panoptic", List.of(), Permission.Default.TRUE, ExecutionScope.ALL, plugin);
    }

    @Override
    public void provide(@NotNull BaseCommand<?> command) {
        command.setDefaultExecutor((context) -> {
            final CommandSender sender = adapt(command.getUser(context.getSource()));
            sender.sendMessage(aboutMenu.toComponent());
        });

        command.addSubCommand("reload", (sub) -> {
            sub.setDefaultExecutor((context) -> {
                final CommandSender sender = adapt(command.getUser(context.getSource()));
                plugin.reload();
                sender.sendMessage(Component.text("Panoptic has been reloaded!").color(TextColor.color(0x00ff00)));
            });

            sub.setPermission(new Permission(getPermission("reload"), Permission.Default.IF_OP));
        });
    }
}
