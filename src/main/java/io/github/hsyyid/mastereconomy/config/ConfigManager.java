package io.github.hsyyid.mastereconomy.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.TextMessageException;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.service.MasterEconomyUniqueAccount;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager
{
	@SuppressWarnings("deprecation")
	public static Text getCurrencyPluralDisplayName()
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.currency.pluralname").split("\\."));

		if (valueNode.getValue() == null)
		{
			setCurrencyPluralDisplayName("Dollars");
			return Texts.of("Dollars");
		}
		else
		{
			try
			{
				return Texts.legacy('&').from(valueNode.getString());
			}
			catch (TextMessageException e)
			{
				return Texts.of(valueNode.getString());
			}
		}
	}

	public static void setCurrencyPluralDisplayName(String displayPluralName)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "currency", "pluralname").setValue(displayPluralName);

		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
	}

	@SuppressWarnings("deprecation")
	public static Text getCurrencyDisplayName()
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.currency.name").split("\\."));

		if (valueNode.getValue() == null)
		{
			setCurrencyDisplayName("Dollar");
			return Texts.of("Dollar");
		}
		else
		{
			try
			{
				return Texts.legacy('&').from(valueNode.getString());
			}
			catch (TextMessageException e)
			{
				return Texts.of(valueNode.getString());
			}
		}
	}

	public static void setCurrencyDisplayName(String displayName)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "currency", "name").setValue(displayName);

		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
	}

	@SuppressWarnings("deprecation")
	public static Text getCurrencySymbol()
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.currency.symbol").split("\\."));

		if (valueNode.getValue() == null)
		{
			setCurrencySymbol("$");
			return Texts.of("$");
		}
		else
		{
			try
			{
				return Texts.legacy('&').from(valueNode.getString());
			}
			catch (TextMessageException e)
			{
				return Texts.of(valueNode.getString());
			}
		}
	}

	public static void setCurrencySymbol(String currencySymbol)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "currency", "symbol").setValue(currencySymbol);

		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
	}

	public static UniqueAccount addUserAccount(UUID playerUuid)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "users", playerUuid.toString(), "balance").setValue(0);
		UniqueAccount uniqueAccount = new MasterEconomyUniqueAccount(playerUuid);
			
		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
		
		return uniqueAccount;
	}
	
	public static void setBalance(UUID playerUuid, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "users", playerUuid.toString(), "balance").setValue(amount.doubleValue());
			
		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
	}

	public static Text getBalanceText(UUID playerUuid)
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.users." + playerUuid.toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() == null)
		{
			addUserAccount(playerUuid);
		}
		else
		{
			balance = BigDecimal.valueOf(valueNode.getDouble());
		}

		return Texts.builder()
			.append(getCurrencySymbol())
			.append(Texts.of(" ", balance.toString()))
			.build();
	}
	
	public static boolean doesPlayerHaveAccount(UUID playerUuid)
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.users." + playerUuid.toString() + ".balance").split("\\."));
		return (valueNode.getValue() != null);
	}
	
	public static BigDecimal getBalance(UUID playerUuid)
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.users." + playerUuid.toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() == null)
		{
			addUserAccount(playerUuid);
		}
		else
		{
			balance = BigDecimal.valueOf(valueNode.getDouble());
		}

		return balance;
	}

	public static void addToBalance(UUID playerUuid, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.users." + playerUuid.toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() == null)
		{
			addUserAccount(playerUuid);
		}
		else
		{
			balance = BigDecimal.valueOf(valueNode.getDouble());
		}

		balance = balance.add(amount);
		valueNode.setValue(balance.doubleValue());

		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
	}
	
	public static void subtractFromBalance(UUID playerUuid, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.users." + playerUuid.toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() == null)
		{
			addUserAccount(playerUuid);
		}
		else
		{
			balance = BigDecimal.valueOf(valueNode.getDouble());
		}

		balance = balance.subtract(amount);
		valueNode.setValue(balance.doubleValue());

		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}
	}
}
