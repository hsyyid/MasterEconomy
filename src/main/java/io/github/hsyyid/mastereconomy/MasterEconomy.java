package io.github.hsyyid.mastereconomy;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import io.github.hsyyid.mastereconomy.commands.AdminPayCommand;
import io.github.hsyyid.mastereconomy.commands.BalanceCommand;
import io.github.hsyyid.mastereconomy.commands.MasterEconomyCommand;
import io.github.hsyyid.mastereconomy.commands.PayCommand;
import io.github.hsyyid.mastereconomy.commands.SetCurrencyCommand;
import io.github.hsyyid.mastereconomy.config.ConfigManager;
import io.github.hsyyid.mastereconomy.listeners.PlayerJoinListener;
import io.github.hsyyid.mastereconomy.service.MasterEconomyCurrency;
import io.github.hsyyid.mastereconomy.service.MasterEconomyService;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Plugin(id = "MasterEconomy", name = "MasterEconomy", version = "0.1")
public class MasterEconomy
{
	protected MasterEconomy() {}
	private static MasterEconomy masterEconomy;
	
	public static ConfigurationNode config;
	public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
	public static Set<Account> accounts = Sets.newHashSet();
	
	public static MasterEconomy getMasterEconomy()
	{
		return masterEconomy;
	}
	
	private Currency currency;
	
	public Currency getCurrency()
	{
		return currency;
	}
	
	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}
	
	@Inject
	private Logger logger;

	public Logger getLogger()
	{
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot = true)
	private File dConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> confManager;

	@Listener
	public void onGamePreInit(GamePreInitializationEvent event)
	{
		Sponge.getGame().getServiceManager().setProvider(Sponge.getPluginManager().getPlugin("MasterEconomy").get().getInstance().get(), EconomyService.class, new MasterEconomyService());
	}
	
	@Listener
	public void onGameInit(GameInitializationEvent event)
	{
		getLogger().info("MasterEconomy loading...");
		masterEconomy = this;
		
		try
		{
			if (!dConfig.exists())
			{
				dConfig.createNewFile();
				config = confManager.load();
				confManager.save(config);
			}
			configurationManager = confManager;
			config = confManager.load();

		}
		catch (IOException exception)
		{
			getLogger().error("The default configuration could not be loaded or created!");
		}
		
		if(this.currency == null)
		{
			this.currency = new MasterEconomyCurrency();
		}
		
		HashMap<List<String>, CommandSpec> subcommands = new HashMap<List<String>, CommandSpec>();

		subcommands.put(Arrays.asList("setcurrency"), CommandSpec.builder()
			.description(Text.of("Sets Currency of Economy"))
			.permission("mastereconomy.command.setcurrency")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.string(Text.of("currency name"))),
				GenericArguments.onlyOne(GenericArguments.string(Text.of("currency plural name"))),
				GenericArguments.onlyOne(GenericArguments.string(Text.of("currency symbol")))))
			.executor(new SetCurrencyCommand())
			.build());
		
		CommandSpec masterEconomyCommandSpec = CommandSpec.builder()
			.description(Text.of("Master Economy Command"))
			.permission("mastereconomy.command")
			.children(subcommands)
			.executor(new MasterEconomyCommand())
			.build();
		
		Sponge.getCommandManager().register(this, masterEconomyCommandSpec, "mastereconomy", "economy");
		
		CommandSpec balanceCommandSpec = CommandSpec.builder()
			.description(Text.of("Master Economy Balance Command"))
			.permission("mastereconomy.command.balance")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
			.executor(new BalanceCommand())
			.build();
		
		Sponge.getCommandManager().register(this, balanceCommandSpec, "balance", "bal");
		
		CommandSpec payCommandSpec = CommandSpec.builder()
			.description(Text.of("Master Economy Pay Command"))
			.permission("mastereconomy.command.pay")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
					GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount")))))
			.executor(new PayCommand())
			.build();
		
		Sponge.getCommandManager().register(this, payCommandSpec, "pay");
		
		CommandSpec adminPayCommandSpec = CommandSpec.builder()
			.description(Text.of("Master Economy Admin Pay Command"))
			.permission("mastereconomy.command.admin.pay")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
					GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount")))))
			.executor(new AdminPayCommand())
			.build();
		
		Sponge.getCommandManager().register(this, adminPayCommandSpec, "adminpay");
		
		Sponge.getEventManager().registerListeners(this, new PlayerJoinListener());
		
		if(accounts.size() == 0)
			ConfigManager.readAccounts();
		
		getLogger().info("-----------------------------");
		getLogger().info("MasterEconomy was made by HassanS6000!");
		getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
		getLogger().info("Have fun, and enjoy! :D");
		getLogger().info("-----------------------------");
		getLogger().info("MasterEconomy loaded!");
	}
	
	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
