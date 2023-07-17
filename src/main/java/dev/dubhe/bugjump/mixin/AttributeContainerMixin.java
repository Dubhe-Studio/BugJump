package dev.dubhe.bugjump.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(AttributeMap.class)
public abstract class AttributeContainerMixin<E> {
    @Shadow @Final private Set<AttributeInstance> dirtyAttributes;

    @Redirect(method = "onAttributeModified", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    private synchronized boolean updateTrackedStatus(Set<AttributeInstance> instance, E e) {
        return this.dirtyAttributes.add((AttributeInstance) e);
    }
}
