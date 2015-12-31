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

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class PayCommand implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = ctx.<Player> getOne("player").get();
		Double amount = ctx.<Double> getOne("amount").get();

		if (src instanceof Player)
		{
			Player source = (Player) src;
			BigDecimal balance = ConfigManager.getBalance(ConfigManager.getUserAccount(source.getUniqueId()).get(), MasterEconomy.getMasterEconomy().getCurrency());

			if (balance.compareTo(BigDecimal.valueOf(amount)) >= 0)
			{
				source.sendMessage(Texts.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Sent ", BigDecimal.valueOf(amount).toString(), " to player ", TextColors.YELLOW, player.getName()));
				player.sendMessage(Texts.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Received ", BigDecimal.valueOf(amount).toString(), " from player ", TextColors.YELLOW, source.getName()));
				ConfigManager.addToBalance(ConfigManager.getUserAccount(player.getUniqueId()).get(), MasterEconomy.getMasterEconomy().getCurrency(), BigDecimal.valueOf(amount));
				ConfigManager.subtractFromBalance(ConfigManager.getUserAccount(source.getUniqueId()).get(), MasterEconomy.getMasterEconomy().getCurrency(), BigDecimal.valueOf(amount));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have enough money to complete this transaction."));
			}
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a player to send money."));
		}

		return CommandResult.success();
	}
}
