package io.github.hsyyid.mastereconomy.commands;

import java.math.BigDecimal;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class AdminPayCommand implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = ctx.<Player> getOne("player").get();
		Double amount = ctx.<Double> getOne("amount").get();
		src.sendMessage(Text.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Sent ", BigDecimal.valueOf(amount).toString(), " ", MasterEconomy.getMasterEconomy().getCurrency().getPluralDisplayName(), " to player ", TextColors.YELLOW, player.getName()));
		player.sendMessage(Text.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Received ", BigDecimal.valueOf(amount).toString(), " ", MasterEconomy.getMasterEconomy().getCurrency().getPluralDisplayName(), " from ", TextColors.YELLOW, src.getName()));
		ConfigManager.addToBalance(ConfigManager.getUserAccount(player.getUniqueId()).get(), MasterEconomy.getMasterEconomy().getCurrency(), BigDecimal.valueOf(amount));
		return CommandResult.success();
	}
}
