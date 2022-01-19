package com.gmail.anthony17j.playertabhider;

import com.gmail.anthony17j.playertabhider.config.PlayerTabHiderConfig;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerTabHider implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		PlayerTabHiderConfig.load();
	}
}
