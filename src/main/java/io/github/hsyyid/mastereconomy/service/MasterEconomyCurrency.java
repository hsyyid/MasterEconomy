package io.github.hsyyid.mastereconomy.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class MasterEconomyCurrency implements Currency
{
	@Override
	public Text getDisplayName()
	{
		return ConfigManager.getCurrencyDisplayName();
	}

	@Override
	public Text getPluralDisplayName()
	{
		return ConfigManager.getCurrencyPluralDisplayName();
	}

	@Override
	public Text getSymbol()
	{
		return ConfigManager.getCurrencySymbol();
	}

	@Override
	public Text format(BigDecimal amount)
	{
		return Text.of(amount.toString());
	}

	@Override
	public Text format(BigDecimal amount, int numFractionDigits)
	{
		amount = amount.setScale(numFractionDigits, BigDecimal.ROUND_DOWN);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(numFractionDigits);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);

		return Text.of(df.format(amount));
	}

	@Override
	public int getDefaultFractionDigits()
	{
		return 2;
	}

	@Override
	public boolean isDefault()
	{
		return true;
	}
}
