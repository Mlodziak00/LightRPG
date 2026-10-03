package net.lightglow.racialfeats;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lightglow.racialfeats.component.entity.PlayerImpactfulComponent;
import net.lightglow.racialfeats.helper.RaceHelper;
import net.lightglow.racialfeats.race.Race;
import net.lightglow.racialfeats.network.ModNetworking;
import net.lightglow.racialfeats.network.OpenCharCustomScreenPayload;
import net.lightglow.racialfeats.reg.*;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RacialFeats implements ModInitializer {
	public static final String MOD_ID = "racialfeats";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		//ArmorRegistry.register();
		//WeaponRegistry.register();
		SoundRegistry.register();
		TraitRegistry.register();

		ModNetworking.registerC2SNetworking();
		ModNetworking.registerS2CNetworking();

		CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) ->
				commandDispatcher.register(CommandManager.literal("charcustom").requires(source -> source.hasPermissionLevel(1))
						.then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> openScreen(context.getSource(), EntityArgumentType.getPlayer(context, "player"))))));

		ServerPlayerEvents.COPY_FROM.register((oldServerPlayerEntity, serverPlayerEntity, alive) -> {
			if (alive) return;
			Race race = RaceRegistry.get(PlayerImpactfulComponent.KEY.get(serverPlayerEntity).getRace());
			RaceHelper.applyRace(serverPlayerEntity, race);
		});

	}

	public static int openScreen(ServerCommandSource source, ServerPlayerEntity target){
		OpenCharCustomScreenPayload payload = new OpenCharCustomScreenPayload(false);
		ServerPlayNetworking.send(target, payload);
		return 1;
	}
	public static Identifier id(String id){
		return Identifier.of(MOD_ID, id);
	}

}