package me.melontini.recipe_book_delight.util;

import me.melontini.recipe_book_delight.RecipeBookDelight;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.InputSlotFiller;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractRecipeScreenHandlerMapper extends AbstractRecipeScreenHandler<Inventory> {
    protected AbstractRecipeScreenHandlerMapper(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public void fillInputSlots(boolean craftAll, Recipe<?> recipe, ServerPlayerEntity player) {
        new InputSlotFiller<>(this).fillInputSlots(player, (Recipe<Inventory>) recipe, craftAll);
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        for (int i = 0; i < RBUtils.trickGetTE(this).getInventory().size(); i++) {
            ItemStack itemStack = RBUtils.trickGetTE(this).getInventory().getStack(i);
            if (!itemStack.isEmpty()) {
                finder.addInput(itemStack);
            }
        }
    }

    @Override
    public void clearCraftingSlots() {
        for (int i = 0; i < 6; i++) {
            RBUtils.trickGetSlot(this, i).setStack(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(RBUtils.trickGetTE(this).getInventory(), RBUtils.trickGetTE(this).getWorld());
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 8;
    }

    @Override
    public int getCraftingWidth() {
        return 3;
    }

    @Override
    public int getCraftingHeight() {
        return 2;
    }

    @Override
    public int getCraftingSlotCount() {
        return 6;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookDelight.COOKING_POT;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 6;
    }
}
