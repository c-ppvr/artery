package net.ppvr.artery.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.PersistentState;
import net.ppvr.artery.blocks.OrganBlock;
import net.ppvr.artery.blocks.entity.OrganBlockEntity;

import java.util.*;
import java.util.stream.LongStream;

public class OrganGroup {
    public static Codec<OrganGroup> createCodec(PersistentState.Context context) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.LONG_STREAM.fieldOf("blocks").forGetter(OrganGroup::getPosLongStream),
                        Codec.INT.fieldOf("sanguinity").forGetter(OrganGroup::getSanguinity)
                ).apply(instance,  (blocks, sanguinity) -> new OrganGroup(context.world(), blocks.mapToObj(BlockPos::fromLong).toList(), sanguinity)
                )
        );
    }

    private int sanguinity;
    private int capacity;
    private boolean modified;
    private final ServerWorld world;
    private final Set<BlockPos> posSet;

    public OrganGroup(ServerWorld world) {
        this.world = world;
        this.posSet = new HashSet<>();
    }

    public OrganGroup(ServerWorld world, Collection<BlockPos> posCollection, int sanguinity) {
        this(world);
        addAll(posCollection);
        this.sanguinity = sanguinity;
        refreshBlockStates();
    }

    public static OrganGroup create(ServerWorld world) {
        OrganGroup group = new OrganGroup(world);
        OrganGroupState.get(world).add(group);
        return group;
    }


    public void add(BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof OrganBlockEntity blockEntity) {
            blockEntity.setGroup(this);
            posSet.add(pos);
            world.setBlockState(pos, world.getBlockState(pos).with(OrganBlock.ACTIVE, sanguinity != 0));
            modified = true;
        }
    }

    public void addAll(Collection<BlockPos> posCollection) {
        for (BlockPos pos : posCollection) {
            add(pos);
        }
    }

    public static OrganGroup merge(ServerWorld world, Set<OrganGroup> groups) {
        if (groups.isEmpty()) {
            return OrganGroup.create(world);
        }
        if (groups.size() == 1) {
            return groups.stream().toList().getFirst();
        }
        OrganGroup newGroup = OrganGroup.create(world);
        groups.forEach(group -> group.moveTo(newGroup));
        return newGroup;
    }


    public void redistribute() {
        int capacity = getCapacity();
        while (!posSet.isEmpty()) {
            Stack<BlockPos> posStack = new Stack<>();
            OrganGroup group = OrganGroup.create(world);
            posStack.push(posSet.stream().findAny().orElse(null));
            while (!posStack.isEmpty()) {
                BlockPos pos = posStack.pop();
                moveTo(pos, group);
                for (Direction d : Direction.values()) {
                    if (posSet.contains(pos.offset(d))) {
                        posStack.push(pos.offset(d));
                    }
                }
            }
            if (capacity > 0) {
                group.setSanguinity(sanguinity * group.getCapacity() / capacity);
            }
        }
        OrganGroupState.get(world).remove(this);
    }

    public void remove(BlockPos pos) {
        posSet.remove(pos);
        if (posSet.isEmpty()) {
            OrganGroupState.get(world).remove(this);
        }
    }

    public void moveTo(BlockPos pos, OrganGroup group) {
        if (posSet.contains(pos)) {
            posSet.remove(pos);
            group.add(pos);
            if (posSet.isEmpty()) {
                OrganGroupState.get(world).remove(this);
            }
        }
    }

    public void moveTo(OrganGroup group) {
        for (BlockPos pos : new HashSet<>(posSet)) {
            group.add(pos);
        }
        group.addSanguinity(sanguinity);
        OrganGroupState.get(world).remove(this);
    }

    public LongStream getPosLongStream() {
        return posSet.stream().map(BlockPos::asLong).mapToLong(l -> l);
    }

    public boolean contains(BlockPos pos) {
        return posSet.contains(pos);
    }

    public int getSanguinity() {
        return sanguinity;
    }


    public void setSanguinity(int sanguinity) {
        this.sanguinity = Math.min(sanguinity, getCapacity());
        refreshBlockStates();
        OrganGroupState.get(world).markDirty();
    }

    public void addSanguinity(int amount) {
        setSanguinity(sanguinity + amount);
    }

    private void refreshBlockStates() {
        for (BlockPos pos : new HashSet<>(posSet)) {
            world.setBlockState(pos, world.getBlockState(pos).with(OrganBlock.ACTIVE, sanguinity != 0));
        }
    }

    public int getCapacity() {
        if (modified) {
            calculateCapacity();
        }
        return capacity;
    }

    private void calculateCapacity() {
        this.capacity = posSet.stream()
                .map(pos -> world.getBlockEntity(pos) instanceof OrganBlockEntity blockEntity ? blockEntity.getCapacity() : 0)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
