package me.melontini.fdrecipebook;

import me.melontini.crackerutil.content.RecipeBookHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.book.RecipeBookCategory;

public class RecipeBookDelight implements ModInitializer {
    public static final RecipeBookCategory COOKING_POT = RecipeBookHelper.createCategory("FDRB_COOKING_POT");
    @Override
    public void onInitialize() {
    }
}
