package net.lightglow.racialfeats.trait.imp;

import net.lightglow.racialfeats.component.entity.PlayerImpactfulComponent;
import net.lightglow.racialfeats.race.Race;
import net.lightglow.racialfeats.reg.RaceRegistry;
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
