package io.github.hsyyid.mastereconomy.config;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.service.MasterEconomyUniqueAccount;
import io.github.hsyyid.mastereconomy.service.MasterEconomyVirtualAccount;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.account.VirtualAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ConfigManager
{
	public static Text getCurrencyPluralDisplayName()
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.currency.pluralname").split("\\."));

		if (valueNode.getValue() == null)
		{
			setCurrencyPluralDisplayName("Dollars");
			return Text.of("Dollars");
		}
		else
		{
			return TextSerializers.formattingCode('&').deserialize(valueNode.getString());
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

	public static Text getCurrencyDisplayName()
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.currency.name").split("\\."));

		if (valueNode.getValue() == null)
		{
			setCurrencyDisplayName("Dollar");
			return Text.of("Dollar");
		}
		else
		{
			return TextSerializers.formattingCode('&').deserialize(valueNode.getString());
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

	public static Text getCurrencySymbol()
	{
		ConfigurationNode valueNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.currency.symbol").split("\\."));

		if (valueNode.getValue() == null)
		{
			setCurrencySymbol("$");
			return Text.of("$");
		}
		else
		{
			return TextSerializers.formattingCode('&').deserialize(valueNode.getString());
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

	public static UniqueAccount addUserAccount(UUID playerUuid, Text displayName)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = MasterEconomy.getConfigManager();
		MasterEconomy.config.getNode("mastereconomy", "accounts", "users", playerUuid.toString(), MasterEconomy.getMasterEconomy().getCurrency().getDisplayName().toText().toString(), "balance").setValue(0);
		UniqueAccount uniqueAccount = new MasterEconomyUniqueAccount(playerUuid, displayName);
		MasterEconomy.accounts.add(uniqueAccount);
		MasterEconomy.getMasterEconomy().getLogger().info("User added");
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

			return Text.builder().append(currency.getSymbol()).append(Text.of(" ", balance.toString())).build();
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

			return Text.builder().append(currency.getSymbol()).append(Text.of(" ", balance.toString())).build();
		}
		else
		{
			return Text.builder().append(currency.getSymbol()).append(Text.of(" ", 0)).build();
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

			for (ConfigurationNode childNode : valueNode.getChildrenMap().values())
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

			for (ConfigurationNode childNode : valueNode.getChildrenMap().values())
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
	
	public static Map<Currency, BigDecimal> getBalances(Account account)
	{
		HashMap<Currency, BigDecimal> balances = new HashMap<>();
		balances.put(MasterEconomy.getMasterEconomy().getCurrency(), ConfigManager.getBalance(account, MasterEconomy.getMasterEconomy().getCurrency()));	
		return balances;
	}

	public static void readAccounts()
	{
		// Read VirtualAccounts
		ConfigurationNode virtualAccountsNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.virtual").split("\\."));

		for (Object key : virtualAccountsNode.getChildrenMap().keySet())
		{
			String identifier = key.toString();
			VirtualAccount virtualAccount = new MasterEconomyVirtualAccount(identifier);
			MasterEconomy.accounts.add(virtualAccount);
		}

		// Read UniqueAccounts
		ConfigurationNode uniqueAccountsNode = MasterEconomy.config.getNode((Object[]) ("mastereconomy.accounts.users").split("\\."));

		for (Object key : uniqueAccountsNode.getChildrenMap().keySet())
		{
			UUID uuid = UUID.fromString(key.toString());
			UniqueAccount uniqueAccount = new MasterEconomyUniqueAccount(uuid);
			MasterEconomy.accounts.add(uniqueAccount);
		}
	}
}
