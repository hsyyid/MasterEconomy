package io.github.hsyyid.mastereconomy.listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import io.github.hsyyid.mastereconomy.config.ConfigManager;

public class PlayerJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Login event)
	{
		if (event.getTargetUser().getPlayer().isPresent())
		{
			Player player = event.getTargetUser().getPlayer().get();
			
			if(!ConfigManager.doesPlayerHaveAccount(player.getUniqueId()))
			{
				ConfigManager.addUserAccount(player.getUniqueId());
			}
		}
	}
}
