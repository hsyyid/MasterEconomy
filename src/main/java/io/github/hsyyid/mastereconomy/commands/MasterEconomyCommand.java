package io.github.hsyyid.mastereconomy.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class MasterEconomyCommand implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		src.sendMessage(Text.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GRAY, "Version: ", TextColors.GOLD, Sponge.getPluginManager().getPlugin("MasterEconomy").get().getVersion()));
		return CommandResult.success();
	}
}
