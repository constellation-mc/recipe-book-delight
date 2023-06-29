package me.melontini.recipe_book_delight.client;

import com.google.common.collect.Lists;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry;
import me.melontini.dark_matter.recipe_book.RecipeBookHelper;
import me.melontini.dark_matter.enums.util.EnumWrapper;
import me.melontini.recipe_book_delight.RecipeBookDelight;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.Items;

public class RecipeBookDelightClient implements ClientModInitializer {
    public static final RecipeBookGroup COOKING_POT_SEARCH = EnumWrapper.RecipeBookGroup.extend("FDRB_COOKING_POT_SEARCH", Items.COMPASS.getDefaultStack());
    public static final RecipeBookGroup COOKING_POT_MAIN = EnumWrapper.RecipeBookGroup.extend("FDRB_COOKING_POT_MAIN", ItemsRegistry.FRIED_RICE.get().getDefaultStack());

    @Override
    public void onInitializeClient() {
        RecipeBookHelper.addRecipePredicate(RecipeTypesRegistry.COOKING_RECIPE_SERIALIZER.type(), recipe -> COOKING_POT_MAIN);
        RecipeBookHelper.addToGetGroups(RecipeBookDelight.COOKING_POT, Lists.newArrayList(COOKING_POT_SEARCH, COOKING_POT_MAIN));
        RecipeBookHelper.addToSearchMap(COOKING_POT_SEARCH, Lists.newArrayList(COOKING_POT_MAIN));
    }
}
