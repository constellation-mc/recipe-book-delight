package me.melontini.recipe_book_delight.client;

import com.google.common.collect.Lists;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry;
import me.melontini.dark_matter.api.recipe_book.RecipeBookHelper;
import me.melontini.recipe_book_delight.RecipeBookDelight;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class RecipeBookDelightClient implements ClientModInitializer {

    public static final RecipeBookGroup COOKING_POT_SEARCH = RecipeBookHelper.createGroup(new Identifier("recipe_book_delight", "search"), Items.COMPASS.getDefaultStack());
    public static final RecipeBookGroup COOKING_POT_MAIN = RecipeBookHelper.createGroup(new Identifier("recipe_book_delight", "main"), ItemsRegistry.FRIED_RICE.get().getDefaultStack());

    @Override
    public void onInitializeClient() {
        RecipeBookHelper.registerGroupLookup(RecipeTypesRegistry.COOKING_RECIPE_SERIALIZER.type(), recipe -> COOKING_POT_MAIN);
        RecipeBookHelper.registerAndAddToSearch(RecipeBookDelight.COOKING_POT, COOKING_POT_SEARCH, Lists.newArrayList(COOKING_POT_MAIN));

        FabricLoader.getInstance().getObjectShare().put("recipe-book-delight:registered_groups", RecipeBookDelight.COOKING_POT);
    }
}
