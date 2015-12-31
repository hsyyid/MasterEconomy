package io.github.hsyyid.mastereconomy.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.account.VirtualAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.TextMessageException;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.service.MasterEconomyUniqueAccount;
import io.github.hsyyid.mastereconomy.service.MasterEconomyVirtualAccount;
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
		MasterEconomy.config.getNode("mastereconomy", "accounts", "users", playerUuid.toString(), MasterEconomy.getMasterEconomy().getCurrency().getDisplayName().toText().toString(), "balance").setValue(0);
		UniqueAccount uniqueAccount = new MasterEconomyUniqueAccount(playerUuid);
		MasterEconomy.accounts.add(uniqueAccount);

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

	public static Optional<UniqueAccount> getUserAccount(UUID playerUuid)
	{
		for (Account account : MasterEconomy.accounts)
		{
			if (account instanceof UniqueAccount)
			{
				UniqueAccount uniqueAccount = (UniqueAccount) account;

				if (uniqueAccount.getUUID().equals(playerUuid))
				{
					return Optional.of(uniqueAccount);
				}
			}
		}

		return Optional.empty();
	}

	public static VirtualAccount addAccount(String identifier)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "accounts", "virtual", identifier, MasterEconomy.getMasterEconomy().getCurrency().getDisplayName().toText().toString(), "balance").setValue(0);
		VirtualAccount virtualAccount = new MasterEconomyVirtualAccount(identifier);
		MasterEconomy.accounts.add(virtualAccount);

		try
		{
			configManager.save(MasterEconomy.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while saving the config.");
		}

		return virtualAccount;
	}

	public static Optional<Account> getAccount(String identifier)
	{
		for (Account account : MasterEconomy.accounts)
		{
			if (account.getIdentifier().equals(identifier))
			{
				return Optional.of(account);
			}
		}

		return Optional.empty();
	}

	public static void setBalance(Account account, Currency currency, BigDecimal amount)
	{
		if (account instanceof UniqueAccount)
		{
			UniqueAccount uniqueAccount = (UniqueAccount) account;
			ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
			MasterEconomy.config.getNode("mastereconomy", "accounts", "users", uniqueAccount.getUUID().toString(), currency.getDisplayName().toText().toString(), "balance").setValue(amount.doubleValue());

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
		else if (account instanceof VirtualAccount)
		{
			VirtualAccount virtualAccount = (VirtualAccount) account;
			ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
			MasterEconomy.config.getNode("mastereconomy", "accounts", "virtual", virtualAccount.getIdentifier(), currency.getDisplayName().toText().toString(), "balance").setValue(amount.doubleValue());

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

	public static Text getBalanceText(Account account, Currency currency)
	{
		if (account instanceof UniqueAccount)
		{
			UniqueAccount uniqueAccount = (UniqueAccount) account;
			ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users." + uniqueAccount.getUUID().toString() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

			BigDecimal balance = BigDecimal.valueOf(0);

			if (valueNode.getValue() != null)
			{
				balance = BigDecimal.valueOf(valueNode.getDouble());
			}

			return Texts.builder().append(currency.getSymbol()).append(Texts.of(" ", balance.toString())).build();
		}
		else if (account instanceof VirtualAccount)
		{
			VirtualAccount virtualAccount = (VirtualAccount) account;
			ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.virtual." + virtualAccount.getIdentifier() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

			BigDecimal balance = BigDecimal.valueOf(0);

			if (valueNode.getValue() != null)
			{
				balance = BigDecimal.valueOf(valueNode.getDouble());
			}

			return Texts.builder().append(currency.getSymbol()).append(Texts.of(" ", balance.toString())).build();
		}
		else
		{
			return Texts.builder().append(currency.getSymbol()).append(Texts.of(" ", 0)).build();
		}
	}

	public static boolean doesPlayerHaveAccount(UUID uuid, Currency currency)
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users." + uuid.toString() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));
		return (valueNode.getValue() != null);
	}

	public static BigDecimal getBalance(Account account, Currency currency)
	{
		if (account instanceof UniqueAccount)
		{
			UniqueAccount uniqueAccount = (UniqueAccount) account;
			ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users." + uniqueAccount.getUUID().toString() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

			BigDecimal balance = BigDecimal.valueOf(0);

			if (valueNode.getValue() != null)
			{
				balance = BigDecimal.valueOf(valueNode.getDouble());
			}

			return balance;
		}
		else if (account instanceof VirtualAccount)
		{
			VirtualAccount virtualAccount = (VirtualAccount) account;
			ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.virtual." + virtualAccount.getIdentifier() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

			BigDecimal balance = BigDecimal.valueOf(0);

			if (valueNode.getValue() != null)
			{
				balance = BigDecimal.valueOf(valueNode.getDouble());
			}

			return balance;
		}
		else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public static void addToBalance(UniqueAccount account, Currency currency, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users." + account.getUUID().toString() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() != null)
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

	public static void addToBalance(VirtualAccount account, Currency currency, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.virtual." + account.getIdentifier() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() != null)
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

	public static void subtractFromBalance(UniqueAccount account, Currency currency, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users." + account.getUUID().toString() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() != null)
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

	public static void subtractFromBalance(VirtualAccount account, Currency currency, BigDecimal amount)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.virtual." + account.getIdentifier() + "." + currency.getDisplayName().toText().toString() + ".balance").split("\\."));

		BigDecimal balance = BigDecimal.valueOf(0);

		if (valueNode.getValue() != null)
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

	public static void resetBalances(Account account, BigDecimal value)
	{
		if (account instanceof UniqueAccount)
		{
			UniqueAccount uniqueAccount = (UniqueAccount) account;
			ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
			ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users." + uniqueAccount.getUUID().toString()).split("\\."));

			for(ConfigurationNode childNode : valueNode.getChildrenList())
			{
				childNode.getChildrenList().get(0).setValue(value.doubleValue());
			}
			
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
		else if (account instanceof VirtualAccount)
		{
			VirtualAccount virtualAccount = (VirtualAccount) account;
			ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
			ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.virtual." + virtualAccount.getIdentifier()).split("\\."));

			for(ConfigurationNode childNode : valueNode.getChildrenList())
			{
				childNode.getChildrenList().get(0).setValue(value.doubleValue());
			}
			
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
}
