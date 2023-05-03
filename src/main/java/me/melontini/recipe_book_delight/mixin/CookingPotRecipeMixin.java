package me.melontini.recipe_book_delight.mixin;

import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CookingPotRecipe.class)
public abstract class CookingPotRecipeMixin implements Recipe<Inventory> {
    @Override
    public ItemStack createIcon() {
        return new ItemStack(BlocksRegistry.COOKING_POT.get());
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }
}
