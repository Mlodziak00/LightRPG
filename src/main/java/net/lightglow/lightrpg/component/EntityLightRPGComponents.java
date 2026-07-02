package net.lightglow.lightrpg.component;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.component.entity.PlayerAppearanceComponent;
import net.lightglow.lightrpg.component.entity.PlayerImpactfulComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.internal.base.ComponentRegistrationInitializer;

public class EntityLightRPGComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.beginRegistration(PlayerEntity.class, PlayerAppearanceComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PlayerAppearanceComponent::new);
        entityComponentFactoryRegistry.beginRegistration(PlayerEntity.class, PlayerImpactfulComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PlayerImpactfulComponent::new);
    }
}
