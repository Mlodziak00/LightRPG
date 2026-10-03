package net.lightglow.racialfeats.component.entity;

import net.lightglow.racialfeats.RacialFeats;
import net.lightglow.racialfeats.race.Race;
import net.lightglow.racialfeats.reg.RaceRegistry;
import net.lightglow.racialfeats.trait.TraitRge;
import net.lightglow.racialfeats.trait.imp.RaceTrait;
import net.lightglow.racialfeats.trait.imp.RaceTraitDispatcher;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerImpactfulComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<PlayerImpactfulComponent> KEY =
            ComponentRegistry.getOrCreate(RacialFeats.id("impact"), PlayerImpactfulComponent.class);

    private final PlayerEntity player;

    private Identifier race = RacialFeats.id("human");
    private final Map<Identifier, RaceTrait> activeTraits = new LinkedHashMap<>();

    public PlayerImpactfulComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public Identifier getRace() {
        return race;
    }

    public void setRace(Identifier race) {
        this.race = race;
        activeTraits.values().removeIf(trait -> trait.getType() == RaceTrait.TraitType.RACIAL);

        // Add new racial traits
        Race raceTraits = RaceRegistry.get(race);
        for (RaceTrait trait : raceTraits.createTraits()) {
            activeTraits.put(trait.getId(), trait);
        }
        sync();
    }

    public void addPassiveTrait(Identifier traitId) {
        if (!TraitRge.isRegistered(traitId)) return;

        if (activeTraits.containsKey(traitId)) return;

        RaceTrait trait = TraitRge.create(traitId);

        if (trait.getType() != RaceTrait.TraitType.PASSIVE) return;

        activeTraits.put(traitId, trait);
        sync();
    }

    public Map<Identifier, RaceTrait> getTraits() {
        return activeTraits;
    }
    public boolean hasTrait(Identifier traitId) {
        return activeTraits.containsKey(traitId);
    }
    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        if (!tag.contains("Race")) return;

        race = Identifier.of(tag.getString("Race"));
        activeTraits.clear();

        Race raceObj = RaceRegistry.get(race);
        for (RaceTrait trait : raceObj.createTraits()) {
            activeTraits.put(trait.getId(), trait);
        }

        if (tag.contains("Traits")) {
            NbtCompound traitsTag = tag.getCompound("Traits");

            for (String key : traitsTag.getKeys()) {
                Identifier id = Identifier.of(key);
                NbtCompound traitTag = traitsTag.getCompound(key);

                RaceTrait.TraitType type = RaceTrait.TraitType.valueOf(traitTag.getString("Type"));

                if (type == RaceTrait.TraitType.PASSIVE) {
                    RaceTrait trait = TraitRge.create(id);
                    if (trait != null) {
                        trait.readFromNbt(traitTag);
                        activeTraits.put(id, trait);
                    }
                } else {
                    RaceTrait trait = activeTraits.get(id);
                    if (trait != null) {
                        trait.readFromNbt(traitTag);
                    }
                }
            }
        }

    }
    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        tag.putString("Race", race.toString());


        NbtCompound traitsTag = new NbtCompound();

        for (RaceTrait trait : activeTraits.values()) {
            NbtCompound traitTag = new NbtCompound();
            trait.writeToNbt(traitTag);

            traitTag.putString("Type", trait.getType().name());

            traitsTag.put(trait.getId().toString(), traitTag);
        }

        tag.put("Traits", traitsTag);

    }

    @Override
    public void tick() {
        if (this.player.getWorld().isClient){

            RaceTraitDispatcher.tickClient((ClientPlayerEntity) player);
        } else{

            RaceTraitDispatcher.tick((ServerPlayerEntity) player);
            sync();
        }
    }
}
