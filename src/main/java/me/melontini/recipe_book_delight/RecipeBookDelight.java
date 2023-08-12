package me.melontini.recipe_book_delight;

import me.melontini.dark_matter.api.recipe_book.RecipeBookHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.Identifier;

public class RecipeBookDelight implements ModInitializer {
    public static final RecipeBookCategory COOKING_POT = RecipeBookHelper.createCategory(new Identifier("recipe_book_delight", "cooking_pot"));
    @Override
    public void onInitialize() {
    }
}
