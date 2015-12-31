package io.github.hsyyid.mastereconomy.commands;

import java.math.BigDecimal;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class AdminPayCommand implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = ctx.<Player> getOne("player").get();
		Double amount = ctx.<Double> getOne("amount").get();
		src.sendMessage(Texts.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Sent ", BigDecimal.valueOf(amount).toString(), " to player ", TextColors.YELLOW, player.getName()));
		player.sendMessage(Texts.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Received ", BigDecimal.valueOf(amount).toString(), " from ", TextColors.YELLOW, src.getName()));
		ConfigManager.addToBalance(player.getUniqueId(), BigDecimal.valueOf(amount));
		return CommandResult.success();
	}
}
