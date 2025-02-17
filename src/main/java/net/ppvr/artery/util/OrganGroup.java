package net.ppvr.artery.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtLongArray;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.ppvr.artery.Artery;
import net.ppvr.artery.blocks.OrganBlock;
import net.ppvr.artery.blocks.entity.OrganBlockEntity;

import java.util.*;

public class OrganGroup {

    public final UUID uuid;
    private int sanguinity;
    private int capacity;
    private boolean modified;
    private final ServerWorld world;
    private final Set<BlockPos> posSet;

    public OrganGroup(ServerWorld world, UUID uuid) {
        this.world = world;
        this.posSet = new HashSet<>();
        this.uuid = uuid;
    }

    private OrganGroup(ServerWorld world) {
        this(world, UUID.randomUUID());
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
            modified = true;
        }
    }

    public void addAll(Collection<BlockPos> posCollection) {
        for (BlockPos pos : posCollection) {
            if (world.getBlockEntity(pos) instanceof OrganBlockEntity blockEntity) {
                blockEntity.setGroup(this);
                posSet.add(pos);
                modified = true;
            }
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
                group.setSanguinity(sanguinity*group.getCapacity()/capacity);
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

    public void writeNbt(NbtCompound nbt) {
        NbtCompound compound = new NbtCompound();
        NbtLongArray blocksNbt = new NbtLongArray(this.posSet.stream().map(BlockPos::asLong).toList());
        compound.put("blocks", blocksNbt);
        compound.putInt("sanguinity", sanguinity);
        nbt.put(uuid.toString(), compound);
    }

    public boolean contains(BlockPos pos) {
        return posSet.contains(pos);
    }

    public int getSanguinity() {
        return sanguinity;
    }

    public void initializeSanguinity(int sanguinity) {
        this.sanguinity = sanguinity;
    }

    public void setSanguinity(int sanguinity) {
        this.sanguinity = Math.min(sanguinity, getCapacity());
        for (BlockPos pos : new HashSet<>(posSet)) {
            world.setBlockState(pos, world.getBlockState(pos).with(OrganBlock.ACTIVE, this.sanguinity != 0));
        }
        OrganGroupState.get(world).markDirty();
    }

    public void addSanguinity(int amount) {
        setSanguinity(sanguinity + amount);
    }

    public int getCapacity() {
        if (modified) {
            calculateCapacity();
        }
        return capacity;
    }

    private void calculateCapacity() {
        this.capacity = this.posSet.stream()
                .map(pos -> world.getBlockEntity(pos) instanceof OrganBlockEntity blockEntity ? blockEntity.getCapacity() : 0)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
