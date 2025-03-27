package net.ppvr.artery.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.*;
import java.util.stream.Collectors;

import static net.ppvr.artery.Artery.MOD_ID;

public class OrganGroupState extends PersistentState {
    private final Set<OrganGroup> groups;

    public static PersistentStateType<OrganGroupState> getPersistentStateType(ServerWorld world) {
        return new PersistentStateType<>(MOD_ID + "_organ_group", OrganGroupState::new, (nbt, registries) -> OrganGroupState.fromNbt(world, nbt), null);
    }

    public static OrganGroupState get(ServerWorld world) {
        return world.getPersistentStateManager().get(OrganGroupState.getPersistentStateType(world), MOD_ID + "_organ_group");
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
            Optional<NbtCompound> compound = nbt.getCompound(uuid);
            if (compound.isEmpty()) {
                continue;
            }
            Optional<long[]> blocks = compound.get().getLongArray("blocks");
            if (blocks.isEmpty()) {
                continue;
            }
            OrganGroup group = new OrganGroup(world, UUID.fromString(uuid));
            group.addAll(Arrays.stream(blocks.get()).mapToObj(BlockPos::fromLong).collect(Collectors.toSet()));
            group.initializeSanguinity(compound.get().getInt("sanguinity", 0));
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