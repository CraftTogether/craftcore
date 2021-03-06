package xyz.crafttogether.craftcore.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.crafttogether.craftcore.CraftCore;
import xyz.crafttogether.craftcore.connector.AccountConnector;
import xyz.crafttogether.craftcore.discord.DiscordCommand;
import xyz.crafttogether.craftcore.discord.VerifyCode;

import java.time.Instant;
import java.util.Random;

/**
 * Discord command which allows the user to link their discord account to their minecraft account
 */
public class LinkCommand implements DiscordCommand {
    /**
     * The name of the command
     */
    private static final String COMMAND_NAME = "link";
    /**
     * The description of the command
     */
    private static final String COMMAND_DESCRIPTION = "link your discord account to your minecraft account";

    /**
     * Method invoked when the link slash command is executed
     *
     * @param event The SlashCommandInteractionEvent object
     */
    @Override
    public void invoke(SlashCommandInteractionEvent event) {
        if (CraftCore.doesCodeAlreadyExists(event.getUser().getIdLong())) {
            event.reply("You have already been given a verification code which is still valid, check your dms")
                    .setEphemeral(true)
                    .queue();
            return;
        }
        if (AccountConnector.containsAccount(event.getUser().getIdLong())) {
            event.reply("You have already linked your discord account to your minecraft account, use ```/unlink```")
                    .setEphemeral(true)
                    .queue();
            return;
        }
        event.getUser().openPrivateChannel().queue(privateChannel -> {
            VerifyCode code = new VerifyCode(Long.toHexString(new Random().nextInt(1000, 9999)), System.currentTimeMillis());
            CraftCore.addVerifyCode(event.getUser().getIdLong(), code);
            final MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Verification")
                    .setDescription(String.format("You can verify your minecraft account by using ```/verify %s``` on the craft together server", code.getCode()))
                    .setTimestamp(Instant.now())
                    .build();
            privateChannel.sendMessageEmbeds(embed).queue();
        });
        event.reply(":thumbsup: Check your dms")
                .setEphemeral(true)
                .queue();
    }

    /**
     * Gets the command name
     *
     * @return The command name
     */
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    /**
     * Gets the command description
     *
     * @return The command description
     */
    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }
}
