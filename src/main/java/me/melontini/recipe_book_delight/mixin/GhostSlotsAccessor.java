package me.melontini.recipe_book_delight.mixin;

import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeBookGhostSlots.class)
public interface GhostSlotsAccessor {
    @Accessor("time")
    float fdrb$time();

    @Accessor("time")
    void fdrb$time(float time);

    @Accessor("slots")
    List<RecipeBookGhostSlots.GhostInputSlot> fdrb$slots();
}
