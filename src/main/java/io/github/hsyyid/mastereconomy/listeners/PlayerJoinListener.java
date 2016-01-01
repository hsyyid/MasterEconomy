package io.github.hsyyid.mastereconomy.listeners;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;

import io.github.hsyyid.mastereconomy.MasterEconomy;
import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class PlayerJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event)
	{	
		if (!ConfigManager.doesPlayerHaveAccount(event.getTargetEntity().getUniqueId(), MasterEconomy.getMasterEconomy().getCurrency()))
		{
			ConfigManager.addUserAccount(event.getTargetEntity().getUniqueId(), Text.of(event.getTargetEntity().getName()));
		}
	}
}
