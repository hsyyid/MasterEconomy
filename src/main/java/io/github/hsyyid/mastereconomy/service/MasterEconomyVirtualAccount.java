package io.github.hsyyid.mastereconomy.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.VirtualAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.economy.transaction.TransactionType;
import org.spongepowered.api.service.economy.transaction.TransactionTypes;
import org.spongepowered.api.service.economy.transaction.TransferResult;
import org.spongepowered.api.text.Text;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class MasterEconomyVirtualAccount implements VirtualAccount
{
	private Text displayName;
	private String identifier;
	
	public MasterEconomyVirtualAccount(String identifier)
	{
		this.identifier = identifier;
		this.displayName = Text.of(identifier);
	}
	
	public MasterEconomyVirtualAccount(String identifier, Text displayName)
	{
		this.identifier = identifier;
		this.displayName = displayName;
	}

	@Override
	public Text getDisplayName()
	{
		return this.displayName;
	}

	@Override
	public BigDecimal getDefaultBalance(Currency currency)
	{
		return BigDecimal.valueOf(0);
	}

	@Override
	public boolean hasBalance(Currency currency, Set<Context> contexts)
	{
		return ConfigManager.getBalance(this, currency).doubleValue() != 0;
	}

	@Override
	public BigDecimal getBalance(Currency currency, Set<Context> contexts)
	{
		return ConfigManager.getBalance(this, currency);
	}

	@Override
	public Map<Currency, BigDecimal> getBalances(Set<Context> contexts)
	{
		return ConfigManager.getBalances(this);
	}

	@Override
	public TransactionResult setBalance(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		TransactionType transactionType = ConfigManager.getBalance(this, currency).compareTo(amount) >= 0 ? TransactionTypes.DEPOSIT : TransactionTypes.WITHDRAW;
		ConfigManager.setBalance(this, currency, amount);
		TransactionResult transactionResult = new MasterEconomyTransactionResult(this, MasterEconomy.getMasterEconomy().getCurrency(), amount, ResultType.SUCCESS, transactionType);
		SpongeEventFactory.createEconomyTransactionEvent(Cause.of(Sponge.getPluginManager().getPlugin("MasterEconomy")), transactionResult);
		return transactionResult;
	}

	@Override
	public TransactionResult resetBalances(Cause cause, Set<Context> contexts)
	{
		ConfigManager.resetBalances(this, BigDecimal.valueOf(0));
		TransactionResult transactionResult = new MasterEconomyTransactionResult(this, MasterEconomy.getMasterEconomy().getCurrency(), BigDecimal.valueOf(0), ResultType.SUCCESS, TransactionTypes.WITHDRAW);
		SpongeEventFactory.createEconomyTransactionEvent(Cause.of(Sponge.getPluginManager().getPlugin("MasterEconomy")), transactionResult);
		return transactionResult;
	}

	@Override
	public TransactionResult resetBalance(Currency currency, Cause cause, Set<Context> contexts)
	{
		ConfigManager.setBalance(this, currency, BigDecimal.valueOf(0));
		return new MasterEconomyTransactionResult(this, currency, BigDecimal.valueOf(0), ResultType.SUCCESS, TransactionTypes.WITHDRAW);
	}

	@Override
	public TransactionResult deposit(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		ConfigManager.addToBalance(this, currency, amount);
		return new MasterEconomyTransactionResult(this, currency, amount, ResultType.SUCCESS, TransactionTypes.DEPOSIT);
	}

	@Override
	public TransactionResult withdraw(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		if (ConfigManager.getBalance(this, currency).compareTo(amount) >= 0)
		{
			ConfigManager.subtractFromBalance(this, currency, amount);
			TransactionResult transactionResult = new MasterEconomyTransactionResult(this, currency, amount, ResultType.SUCCESS, TransactionTypes.WITHDRAW);
			SpongeEventFactory.createEconomyTransactionEvent(Cause.of(Sponge.getPluginManager().getPlugin("MasterEconomy")), transactionResult);
			return transactionResult;
		}
		else
		{
			TransactionResult transactionResult = new MasterEconomyTransactionResult(this, currency, amount, ResultType.ACCOUNT_NO_FUNDS, TransactionTypes.WITHDRAW);
			SpongeEventFactory.createEconomyTransactionEvent(Cause.of(Sponge.getPluginManager().getPlugin("MasterEconomy")), transactionResult);
			return transactionResult;
		}
	}

	@Override
	public TransferResult transfer(Account to, Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		if (ConfigManager.getBalance(this, currency).compareTo(amount) >= 0)
		{
			to.deposit(currency, amount, cause);
			ConfigManager.subtractFromBalance(this, currency, amount);
			TransferResult transactionResult = new MasterEconomyTransferResult(this, currency, amount, ResultType.SUCCESS, TransactionTypes.TRANSFER, to);
			SpongeEventFactory.createEconomyTransactionEvent(Cause.of(Sponge.getPluginManager().getPlugin("MasterEconomy")), transactionResult);
			return transactionResult;
		}
		else
		{
			TransferResult transactionResult = new MasterEconomyTransferResult(this, currency, amount, ResultType.ACCOUNT_NO_FUNDS, TransactionTypes.TRANSFER, to);
			SpongeEventFactory.createEconomyTransactionEvent(Cause.of(Sponge.getPluginManager().getPlugin("MasterEconomy")), transactionResult);
			return transactionResult;
		}

	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public Set<Context> getActiveContexts()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
