package xyz.crafttogether.craftcore.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.crafttogether.craftcore.connector.AccountConnector;
import xyz.crafttogether.craftcore.discord.DiscordCommand;

/**
 * Discord command which allows a discord user to unlink their discord account from their minecraft account
 */
public class UnlinkCommand implements DiscordCommand {
    /**
     * The name of the command
     */
    private static final String COMMAND_NAME = "unlink";
    /**
     * The description of the command
     */
    private static final String COMMAND_DESCRIPTION = "Unlink your discord account to your minecraft account";

    /**
     * Method invoked when the unlink command is executed
     *
     * @param event The SlashCommandInteractionEvent object
     */
    @Override
    public void invoke(SlashCommandInteractionEvent event) {
        boolean isLinked = AccountConnector.containsAccount(event.getUser().getIdLong());
        if (isLinked) {
            AccountConnector.removeAccount(event.getUser().getIdLong());
            event.reply("Your minecraft account has now been unliked")
                    .setEphemeral(true)
                    .queue();
        } else {
            event.reply("You have not yet linked your minecraft account, to link your minecraft account use /link")
                    .setEphemeral(true)
                    .queue();
        }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }
}
