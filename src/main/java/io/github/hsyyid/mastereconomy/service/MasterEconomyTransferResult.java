package io.github.hsyyid.mastereconomy.service;

import java.math.BigDecimal;
import java.util.Set;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionType;
import org.spongepowered.api.service.economy.transaction.TransferResult;

public class MasterEconomyTransferResult implements TransferResult
{
	private Account account;
	private Currency currency;
	private BigDecimal amount;
	private ResultType result;
	private TransactionType type;
	private Account accountTo;
	
	public MasterEconomyTransferResult(Account account, Currency currency, BigDecimal amount, ResultType result, TransactionType type, Account accountTo)
	{
		this.account = account;
		this.currency = currency;
		this.amount = amount;
		this.result = result;
		this.type = type;
		this.accountTo = accountTo;
	}
	
	@Override
	public Account getAccount()
	{
		return this.account;
	}

	@Override
	public Currency getCurrency()
	{
		return this.currency;
	}

	@Override
	public BigDecimal getAmount()
	{
		return this.amount;
	}

	@Override
	public Set<Context> getContexts()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultType getResult()
	{
		return this.result;
	}

	@Override
	public TransactionType getType()
	{
		return this.type;
	}

	@Override
	public Account getAccountTo()
	{
		return this.accountTo;
	}
}
