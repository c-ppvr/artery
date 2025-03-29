package net.ppvr.artery.util;

import com.mojang.serialization.Codec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.*;

import static net.ppvr.artery.Artery.MOD_ID;

public class OrganGroupState extends PersistentState {
    private final Set<OrganGroup> groups;
    public static PersistentStateType<OrganGroupState> getPersistentStateType() {
        return new PersistentStateType<>(MOD_ID + "_organ_group", OrganGroupState::new, OrganGroupState::createCodec, null);
    }

    private static Codec<OrganGroupState> createCodec(Context context) {
        Codec<OrganGroup> groupCodec = OrganGroup.createCodec(context);
        return groupCodec.listOf().xmap(OrganGroupState::new, state -> state.groups.stream().toList());
    }

    public static OrganGroupState get(ServerWorld world) {
        return world.getPersistentStateManager().get(OrganGroupState.getPersistentStateType());
    }

    public OrganGroupState() {
        this.groups = new HashSet<>();
    }

    public OrganGroupState(Context context) {
        this.groups = new HashSet<>();
    }

    public OrganGroupState(Collection<OrganGroup> groups) {
        this();
        this.groups.addAll(groups);
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
}