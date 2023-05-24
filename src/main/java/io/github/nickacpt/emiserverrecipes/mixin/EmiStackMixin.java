package io.github.nickacpt.emiserverrecipes.mixin;

import dev.emi.emi.api.stack.EmiStack;
import io.github.nickacpt.emiserverrecipes.emi.ModelDataAwareItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EmiStack.class)
public class EmiStackMixin {
    @Inject(at = @At("HEAD"), method = {"isEqual(Ldev/emi/emi/api/stack/EmiStack;)Z", "isEqual(Ldev/emi/emi/api/stack/EmiStack;Ldev/emi/emi/api/stack/Comparison;)Z"}, cancellable = true, remap = false)
    public void onIsEqual(EmiStack other, CallbackInfoReturnable<Boolean> info) {
        var thisStack = (EmiStack) (Object) this;
        if (thisStack instanceof ModelDataAwareItem && other instanceof ModelDataAwareItem) {
            var thisModelData = ((ModelDataAwareItem) thisStack).getCustomModelDataValue();
            var otherModelData = ((ModelDataAwareItem) other).getCustomModelDataValue();
            if (thisModelData != null && otherModelData != null) {
                if (!thisModelData.equals(otherModelData)) {
                    info.setReturnValue(false);
                }
            }
        }
    }
}
