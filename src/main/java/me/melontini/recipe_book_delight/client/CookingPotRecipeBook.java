package me.melontini.recipe_book_delight.client;

import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class CookingPotRecipeBook extends RecipeBookWidget {
    @Override
    public void showGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        if (recipe instanceof CookingPotRecipe cookingPotRecipe) {
            ItemStack container = cookingPotRecipe.getContainer();
            ItemStack itemStack = cookingPotRecipe.getOutput(this.client.world.getRegistryManager());

            this.ghostSlots.setRecipe(cookingPotRecipe);
            this.ghostSlots.addSlot(Ingredient.ofStacks(itemStack), slots.get(8).x, slots.get(8).y);
            if (!container.isEmpty()) {
                this.ghostSlots.addSlot(Ingredient.ofStacks(container), slots.get(7).x, slots.get(7).y);
            }
            DefaultedList<Ingredient> defaultedList = cookingPotRecipe.getIngredients();
            for (int i = 0; i < defaultedList.size(); i++) {
                this.ghostSlots.addSlot(defaultedList.get(i), slots.get(i).x, slots.get(i).y);
            }
        }
    }

    public void drawGhostSlots(DrawContext ctx, int i, int j, boolean bl, float f) {
        if (!Screen.hasControlDown()) {
            this.ghostSlots.time += f;
        }

        for (int k = 0; k < this.ghostSlots.slots.size(); ++k) {
            RecipeBookGhostSlots.GhostInputSlot ghostInputSlot = this.ghostSlots.slots.get(k);
            int l = ghostInputSlot.getX() + i;
            int m = ghostInputSlot.getY() + j;
            ctx.fill(l, m, l + 16, m + 16, 822018048);

            ItemStack itemStack = ghostInputSlot.getCurrentItemStack();
            ctx.drawItemWithoutEntity(itemStack, l, m);
            ctx.fill(l, m, l + 16, m + 16, 822083583);
            if (k == 0) {
                ctx.drawItemInSlot(client.textRenderer, itemStack, l, m);
            }
        }
    }
}
