package net.ppvr.artery.items.components;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.ppvr.artery.items.components.consume.GainSanguinityConsumeEffect;

public class ArteryConsumableComponents extends ConsumableComponents {
    public static final ConsumableComponent FLESH = food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 0.2F))
            .consumeEffect(new GainSanguinityConsumeEffect(20))
            .build();
}
