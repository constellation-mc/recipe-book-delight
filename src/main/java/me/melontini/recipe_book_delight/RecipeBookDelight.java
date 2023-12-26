package me.melontini.recipe_book_delight;

import me.melontini.dark_matter.api.recipe_book.RecipeBookHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.Identifier;

public class RecipeBookDelight implements ModInitializer {

    public static RecipeBookCategory COOKING_POT;
    public static final Identifier MOD_CHECK = new Identifier("recipe_book_delight", "mod_check");

    @Override
    public void onInitialize() {
        COOKING_POT = RecipeBookHelper.createCategory(new Identifier("recipe_book_delight", "cooking_pot"));
    }
}
