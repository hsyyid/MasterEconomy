package io.github.hsyyid.mastereconomy.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.service.context.ContextCalculator;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.account.VirtualAccount;

import com.google.common.collect.Sets;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class MasterEconomyService implements EconomyService
{
	@Override
	public void registerContextCalculator(ContextCalculator<Account> calculator)
	{
		;
	}

	@Override
	public Currency getDefaultCurrency()
	{
		return MasterEconomy.getMasterEconomy().getCurrency();
	}

	@Override
	public Set<Currency> getCurrencies()
	{
		Set<Currency> currencies = Sets.newHashSet();
		currencies.add(MasterEconomy.getMasterEconomy().getCurrency());
		return currencies;
	}

	@Override
	public Optional<UniqueAccount> getAccount(UUID uuid)
	{
		
	}

	@Override
	public Optional<UniqueAccount> createAccount(UUID uuid)
	{
		return Optional.of(ConfigManager.addUserAccount(uuid));
	}

	@Override
	public Optional<Account> getAccount(String identifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<VirtualAccount> createVirtualAccount(String identifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
