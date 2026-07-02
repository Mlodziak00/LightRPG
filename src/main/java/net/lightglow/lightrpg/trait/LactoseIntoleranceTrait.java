package net.lightglow.lightrpg.trait;

import net.lightglow.lightrpg.LightRPG;
import net.lightglow.lightrpg.client.sound.VampireSizzleSound;
import net.lightglow.lightrpg.trait.imp.RaceTrait;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class LactoseIntoleranceTrait extends RaceTrait {

    @Override
    public Identifier getId() {
        return LightRPG.id("lactose_intolerance");
    }

    @Override
    public TraitType getType() {
        return TraitType.PASSIVE;
    }

}
