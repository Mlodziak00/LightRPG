package net.lightglow.racialfeats.trait.imp;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public abstract class RaceTrait {

    public void onDamaged(ServerPlayerEntity player, DamageSource source, float amount) {}

    public void onDamage(ServerPlayerEntity player, Entity target, float amount) {}

    public void onTick(ServerPlayerEntity player) {}

    public void onClientTick(ClientPlayerEntity player) {}

    public void onMine(ServerPlayerEntity player, BlockPos pos, BlockState state) {}

    public void onItemuse(ServerPlayerEntity player, ItemStack stack) {}

    public void onBlockInteract(ServerPlayerEntity player, BlockPos pos, BlockState state) {}


    public void writeToNbt(NbtCompound tag) {}
    public void readFromNbt(NbtCompound tag) {}

    public abstract Identifier getId();
    public abstract TraitType getType();

    public enum TraitType {
        RACIAL,
        PASSIVE
    }
}
