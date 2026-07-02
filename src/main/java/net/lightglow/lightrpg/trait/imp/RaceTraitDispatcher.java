package net.lightglow.lightrpg.trait.imp;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
import net.lightglow.lightrpg.race.Race;
import net.lightglow.lightrpg.reg.RaceRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceTraitDispatcher {
    public static void tick(ServerPlayerEntity player){
        Race race = RaceRegistry.get(PlayerImpactfulComponent.KEY.get(player).getRace());
        if (race == null) return;
        for (RaceTrait trait : PlayerImpactfulComponent.KEY.get(player).getTraits().values()){
            trait.onTick(player);
        }
    }
    public static void tickClient(ClientPlayerEntity player){
        Race race = RaceRegistry.get(PlayerImpactfulComponent.KEY.get(player).getRace());
        if (race == null) return;
        for (RaceTrait trait : PlayerImpactfulComponent.KEY.get(player).getTraits().values()){
            trait.onClientTick(player);
        }
    }
}
