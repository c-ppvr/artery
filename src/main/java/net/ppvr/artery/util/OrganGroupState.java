package net.ppvr.artery.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.*;
import java.util.stream.Collectors;

import static net.ppvr.artery.Artery.MOD_ID;

public class OrganGroupState extends PersistentState {
    private final Set<OrganGroup> groups;
    public static PersistentStateType<OrganGroupState> getPersistentStateType() {
        return new PersistentStateType<>(MOD_ID + "_organ_group", OrganGroupState::new, OrganGroupState::createCodec, null);
    }

    private static Codec<OrganGroupState> createCodec(Context context) {
        Codec<OrganGroup> groupCodec = OrganGroup.createCodec(context);
        return RecordCodecBuilder.create(
                instance -> instance.group(
                        groupCodec.listOf().fieldOf("groups").forGetter(state -> state.groups.stream().toList())
                ).apply(instance, groups -> new OrganGroupState(context, groups))
        );
    }

    public static OrganGroupState get(ServerWorld world) {
        return world.getPersistentStateManager().get(OrganGroupState.getPersistentStateType());
    }

    @Deprecated
    public OrganGroupState() {
        this.groups = new HashSet<>();
    }

    public OrganGroupState(Context context) {
        this.groups = new HashSet<>();
    }

    public OrganGroupState(Context context, Collection<OrganGroup> groups) {
        this(context);
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

    @Deprecated
    public static OrganGroupState fromNbt(Context context, NbtCompound nbt) {
        ServerWorld world = context.world();
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

    @Deprecated
    public NbtCompound writeNbt(NbtCompound nbt) {
        groups.forEach(group -> group.writeNbt(nbt));
        return nbt;
    }
}