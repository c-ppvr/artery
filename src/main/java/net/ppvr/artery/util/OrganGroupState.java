package net.ppvr.artery.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.ppvr.artery.Artery.MOD_ID;

public class OrganGroupState extends PersistentState {
    private final Set<OrganGroup> groups;

    public static Type<OrganGroupState> getPersistentStateType(ServerWorld world) {
        return new Type<>(OrganGroupState::new, (nbt, registries) -> OrganGroupState.fromNbt(world, nbt), null);
    }

    public static OrganGroupState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(OrganGroupState.getPersistentStateType(world), MOD_ID + "_organ_group");
    }

    public OrganGroupState() {
        this.groups = new HashSet<>();
    }

    public void add(OrganGroup group) {
        groups.add(group);
        markDirty();
    }

    public void remove(OrganGroup group) {
        groups.remove(group);
        markDirty();
    }

    public OrganGroup get(BlockPos pos) {
        for (OrganGroup group : groups) {
            if (group.contains(pos)) {
                return group;
            }
        }
        return null;
    }

    public static OrganGroupState fromNbt(ServerWorld world, NbtCompound nbt) {
        OrganGroupState groupState = new OrganGroupState();
        for (String uuid : nbt.getKeys()) {
            NbtCompound compound = nbt.getCompound(uuid);
            if (compound.getLongArray("blocks").length == 0) {
                continue;
            }
            OrganGroup group = new OrganGroup(world, UUID.fromString(uuid));
            group.addAll(Arrays.stream(compound.getLongArray("blocks")).mapToObj(BlockPos::fromLong).collect(Collectors.toSet()));
            group.initializeSanguinity(compound.getInt("sanguinity"));
            groupState.add(group);
        }
        return groupState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        groups.forEach(group -> group.writeNbt(nbt));
        return nbt;
    }
}