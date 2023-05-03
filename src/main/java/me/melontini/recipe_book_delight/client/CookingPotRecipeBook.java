package me.melontini.recipe_book_delight.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import me.melontini.recipe_book_delight.mixin.GhostSlotsAccessor;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
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
            ItemStack itemStack = cookingPotRecipe.getOutput();

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

    public void drawGhostSlots(MatrixStack matrices, int i, int j, boolean bl, float f) {
        if (!Screen.hasControlDown()) {
            ((GhostSlotsAccessor) this.ghostSlots).fdrb$time(((GhostSlotsAccessor) this.ghostSlots).fdrb$time() + f);
        }

        for (int k = 0; k < ((GhostSlotsAccessor) this.ghostSlots).fdrb$slots().size(); ++k) {
            RecipeBookGhostSlots.GhostInputSlot ghostInputSlot = ((GhostSlotsAccessor) this.ghostSlots).fdrb$slots().get(k);
            int l = ghostInputSlot.getX() + i;
            int m = ghostInputSlot.getY() + j;
            DrawableHelper.fill(matrices, l, m, l + 16, m + 16, 822018048);

            ItemStack itemStack = ghostInputSlot.getCurrentItemStack();
            ItemRenderer itemRenderer = client.getItemRenderer();
            itemRenderer.renderInGui(itemStack, l, m);
            RenderSystem.depthFunc(516);
            DrawableHelper.fill(matrices, l, m, l + 16, m + 16, 822083583);
            RenderSystem.depthFunc(515);
            if (k == 0) {
                itemRenderer.renderGuiItemOverlay(client.textRenderer, itemStack, l, m);
            }
        }
    }
}
