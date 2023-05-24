package io.github.nickacpt.emiserverrecipes.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;

public class EmiServerRecipesPlugin implements EmiPlugin {

    private static ItemStack getOutput(Recipe<?> recipe) {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        return recipe.getOutput(client.player.world.getRegistryManager());
    }

    @Override
    public void register(EmiRegistry registry) {
        for (CraftingRecipe craftingRecipe : registry.getRecipeManager().listAllOfType(RecipeType.CRAFTING)) {
            if (craftingRecipe instanceof ShapedRecipe || craftingRecipe instanceof ShapelessRecipe) {
                var output = getOutput(craftingRecipe);

                if (output.hasNbt()) {
                    var emiStack = EmiStack.of(output, 1);
                    if (emiStack instanceof ModelDataAwareItem && ((ModelDataAwareItem) emiStack).getCustomModelDataValue() != null) {
                        registry.addEmiStack(emiStack);
                    }
                }
            }
        }
    }
}
