package net.lightglow.racialfeats.component;

import net.lightglow.racialfeats.component.entity.PlayerAppearanceComponent;
import net.lightglow.racialfeats.component.entity.PlayerImpactfulComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class EntityRacialFeatsComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.beginRegistration(PlayerEntity.class, PlayerAppearanceComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PlayerAppearanceComponent::new);
        entityComponentFactoryRegistry.beginRegistration(PlayerEntity.class, PlayerImpactfulComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PlayerImpactfulComponent::new);
    }
}
