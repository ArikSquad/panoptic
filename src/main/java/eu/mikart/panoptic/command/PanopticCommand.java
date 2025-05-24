package eu.mikart.panoptic.command;

import eu.mikart.panoptic.PanopticPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.uniform.CommandUser;
import net.william278.uniform.Permission;
import net.william278.uniform.annotations.CommandNode;
import net.william278.uniform.annotations.PermissionNode;
import net.william278.uniform.annotations.Syntax;

@CommandNode(value = "panoptic", permission = @PermissionNode(value = "panoptic.command.panoptic", defaultValue = Permission.Default.TRUE))
public class PanopticCommand {

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

    @Syntax
    public void execute(CommandUser user) {
        user.getAudience().sendMessage(aboutMenu.toComponent());
    }

}
