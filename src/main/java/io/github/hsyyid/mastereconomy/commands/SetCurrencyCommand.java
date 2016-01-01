package io.github.hsyyid.mastereconomy.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.config.ConfigManager;
import io.github.hsyyid.mastereconomy.service.MasterEconomyCurrency;

public class SetCurrencyCommand implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String currencyDisplayName = ctx.<String> getOne("currency name").get();
		String currencyPluralDisplayName = ctx.<String> getOne("currency plural name").get();
		String currencySymbol = ctx.<String> getOne("currency symbol").get();
		
		ConfigManager.setCurrencyDisplayName(currencyDisplayName);
		ConfigManager.setCurrencyPluralDisplayName(currencyPluralDisplayName);
		ConfigManager.setCurrencySymbol(currencySymbol);
		MasterEconomy.getMasterEconomy().setCurrency(new MasterEconomyCurrency());
		
		src.sendMessage(Text.of(TextColors.AQUA, "[MasterEconomy]: ", TextColors.GREEN, "Set currency name and symbol."));
		return CommandResult.success();
	}
}