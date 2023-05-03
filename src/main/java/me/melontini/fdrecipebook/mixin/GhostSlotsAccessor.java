package me.melontini.fdrecipebook.mixin;

import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeBookGhostSlots.class)
public interface GhostSlotsAccessor {
    @Accessor("time")
    float time();

    @Accessor("time")
    void time(float time);

    @Accessor("slots")
    List<RecipeBookGhostSlots.GhostInputSlot> slots();
}
