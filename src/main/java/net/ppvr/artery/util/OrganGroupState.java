package net.ppvr.artery.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.ppvr.artery.Artery.MOD_ID;

public class OrganGroupState extends PersistentState {
    private final Set<OrganGroup> groups;

    public static Type<OrganGroupState> getPersistentStateType(ServerWorld world) {
        return new Type<>(OrganGroupState::new, (nbt, registries) -> OrganGroupState.fromNbt(world, nbt), null);
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
        NbtList list = nbt.getList("groups", NbtElement.COMPOUND_TYPE);
        int end = list.size();
        for (int i = 0; i < end; ++i) {
            NbtCompound compound = list.getCompound(i);
            if (compound.getLongArray("blocks").length == 0) {
                continue;
            }
            OrganGroup group = new OrganGroup(world);
            group.addAll(Arrays.stream(compound.getLongArray("blocks")).mapToObj(BlockPos::fromLong).collect(Collectors.toSet()));
            group.initializeSanguinity(compound.getInt("sanguinity"));
            groupState.add(group);
        }
        return groupState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        NbtList list = new NbtList();
        groups.forEach(group -> group.writeNbtList(list));
        nbt.put("groups", list);
        return nbt;
    }
}