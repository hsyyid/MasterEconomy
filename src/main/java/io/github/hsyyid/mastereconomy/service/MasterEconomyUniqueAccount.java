package io.github.hsyyid.mastereconomy.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.economy.transaction.TransferResult;
import org.spongepowered.api.text.Text;

import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class MasterEconomyUniqueAccount implements UniqueAccount
{
	private Text displayName;
	private UUID uuid;
	
	public MasterEconomyUniqueAccount(UUID uuid)
	{
		this.uuid = uuid;
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
		return ConfigManager.doesPlayerHaveAccount(this.getUUID());
	}

	@Override
	public BigDecimal getBalance(Currency currency, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Currency, BigDecimal> getBalances(Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult setBalance(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult resetBalances(Cause cause, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult resetBalance(Currency currency, Cause cause, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		ConfigManager.setBalance(this.getUUID(), 0);
		return null;
	}

	@Override
	public TransactionResult deposit(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		ConfigManager.addToBalance(this.getUUID(), amount);
		return null;
	}

	@Override
	public TransactionResult withdraw(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferResult transfer(Account to, Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdentifier()
	{
		return this.getUUID().toString();
	}

	@Override
	public Set<Context> getActiveContexts()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getUUID()
	{
		return this.uuid;
	}

}
