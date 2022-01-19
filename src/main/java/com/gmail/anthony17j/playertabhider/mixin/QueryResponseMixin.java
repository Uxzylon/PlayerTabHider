package com.gmail.anthony17j.playertabhider.mixin;

import com.gmail.anthony17j.playertabhider.PlayerTabHider;
import com.gmail.anthony17j.playertabhider.config.PlayerTabHiderConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(QueryResponseS2CPacket.class)
public class QueryResponseMixin {

	@Inject(method = "<init>(Lnet/minecraft/server/ServerMetadata;)V", at = @At("TAIL"))
	private void onInit(ServerMetadata metadata, CallbackInfo ci) {

		if (PlayerTabHiderConfig.hideEnabled.getValue()) {

			ServerMetadata.Players players;

			String[] nameList = PlayerTabHiderConfig.playerList.getValue();
			GameProfile[] onlinePlayers = metadata.getPlayers().getSample();
			Integer nbrHidden = 0;

			for(GameProfile profile : onlinePlayers) {
				if (Arrays.asList(nameList).contains(profile.getName())) {
					nbrHidden++;
					onlinePlayers = ArrayUtils.removeElement(onlinePlayers, profile);
				}
			}
			players = new ServerMetadata.Players(
					metadata.getPlayers().getPlayerLimit() - nbrHidden,metadata.getPlayers().getOnlinePlayerCount() - nbrHidden
			);
			players.setSample(metadata.getPlayers().getSample());
			players.setSample(onlinePlayers);

			metadata.setPlayers(players);
		}

	}
}
