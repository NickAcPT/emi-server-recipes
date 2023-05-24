package io.github.nickacpt.emiserverrecipes.mixin;

import dev.emi.emi.api.stack.ItemEmiStack;
import io.github.nickacpt.emiserverrecipes.emi.ModelDataAwareItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEmiStack.class)
public class ItemEmiStackMixin implements ModelDataAwareItem {
    private Integer emiServerRecipes$customModelDataValue = null;

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/item/ItemStack;J)V")
    private void onInit(ItemStack stack, long count, CallbackInfo info) {
        if (!stack.hasNbt()) return;
        var nbt = stack.getNbt();
        if (nbt == null || !nbt.contains("CustomModelData")) return;
        emiServerRecipes$customModelDataValue = nbt.getInt("CustomModelData");
    }

    @Inject(at = @At("HEAD"), method = "getKey()Ljava/lang/Object;", cancellable = true, remap = false)
    public void onGetKey(CallbackInfoReturnable<Object> info) {
        if (emiServerRecipes$customModelDataValue != null) {
            info.setReturnValue(emiServerRecipes$customModelDataValue);
        }
    }

    @Override
    public Integer getCustomModelDataValue() {
        return emiServerRecipes$customModelDataValue;
    }
}
