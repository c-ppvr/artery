package net.ppvr.artery.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MobWandItem extends Item {
    private final EntityType<?> type;
    private final int cost;

    public MobWandItem(EntityType<? extends MobEntity> type, int cost, Settings settings) {
        super(settings);
        this.type = type;
        this.cost = cost;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        HitResult hitResult = user.raycast(4, 1, false);
        Vec3d vec = hitResult.getPos();
        if (stack.artery$getPressure() < cost && !user.isCreative()) {
            if (world.isClient) {
                Random random = world.getRandom();
                for (int i = 0; i < 3; ++i) {
                    world.addParticleClient(
                            ParticleTypes.WHITE_SMOKE,
                            vec.getX(),
                            vec.getY(),
                            vec.getZ(),
                            random.nextDouble() * 0.005,
                            random.nextDouble() * 0.005 + 0.005,
                            random.nextDouble() * 0.005
                    );
                }
            }
        } else if (world instanceof ServerWorld serverWorld) {
            if (!user.isCreative()) {
                stack.artery$addPressure(-cost);
                stack.damage(1, user, LivingEntity.getSlotForHand(hand));
            }
            type.spawnFromItemStack(serverWorld, stack, user, BlockPos.ofFloored(vec), SpawnReason.SPAWN_ITEM_USE, false, false);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResult.PASS;
        }
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();
        Direction side = context.getSide();
        if (stack.artery$getPressure() < cost && !context.getPlayer().isCreative()) {
            if (world.isClient) {
                Random random = world.getRandom();
                for (int i = 0; i < 3; ++i){
                    world.addParticleClient(
                            ParticleTypes.WHITE_SMOKE,
                            pos.getX() + (double) side.getOffsetX() / 2 + (side.getAxis() == Direction.Axis.X ? 0.52 : random.nextDouble() * 0.4 + 0.3),
                            pos.getY() + (double) side.getOffsetY() / 2 + (side.getAxis() == Direction.Axis.Y ? 0.52 : random.nextDouble() * 0.4 + 0.3),
                            pos.getZ() + (double) side.getOffsetZ() / 2 + (side.getAxis() == Direction.Axis.Z ? 0.52 : random.nextDouble() * 0.4 + 0.3),
                            side.getOffsetX() * 0.01 + random.nextDouble() * 0.005,
                            side.getOffsetY() * 0.01 + random.nextDouble() * 0.005 + 0.005,
                            side.getOffsetZ() * 0.01 + random.nextDouble() * 0.005
                    );
                }
            }
        } else if (world instanceof ServerWorld serverWorld) {
            if (!player.isCreative()) {
                stack.artery$addPressure(-cost);
                stack.damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
            type.spawnFromItemStack(serverWorld, stack, player, pos.offset(side), SpawnReason.SPAWN_ITEM_USE, false, false);
        }
        return ActionResult.SUCCESS;
    }
}
