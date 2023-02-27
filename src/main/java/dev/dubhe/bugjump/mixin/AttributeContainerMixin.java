package dev.dubhe.bugjump.mixin;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(AttributeContainer.class)
public abstract class AttributeContainerMixin<E> {
    @Shadow
    @Final
    private Set<EntityAttributeInstance> tracked;

    @Redirect(method = "updateTrackedStatus", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    private synchronized boolean updateTrackedStatus(Set<EntityAttributeInstance> instance, E e) {
        return this.tracked.add((EntityAttributeInstance) e);
    }
}
