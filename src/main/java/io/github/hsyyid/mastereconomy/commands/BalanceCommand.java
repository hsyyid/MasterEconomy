package io.github.hsyyid.mastereconomy.commands;

import java.util.Optional;

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

public class BalanceCommand implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> target = ctx.<Player> getOne("player");

		if (target.isPresent())
		{
			Player player = target.get();
			src.sendMessage(Text.builder()
				.append(Text.of(TextColors.GOLD, "Balance: ", TextColors.GRAY))
				.append(ConfigManager.getBalanceText(ConfigManager.getUserAccount(player.getUniqueId()).get(), MasterEconomy.getMasterEconomy().getCurrency()))
				.build());
		}
		else
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				src.sendMessage(Text.builder()
					.append(Text.of(TextColors.GOLD, "Balance: ", TextColors.GRAY))
					.append(ConfigManager.getBalanceText(ConfigManager.getUserAccount(player.getUniqueId()).get(), MasterEconomy.getMasterEconomy().getCurrency()))
					.build());
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You musaia"));
			}
		}

		return CommandResult.success();
	}
}
