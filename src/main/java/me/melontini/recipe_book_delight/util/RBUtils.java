package me.melontini.recipe_book_delight.util;

import com.nhoryzon.mc.farmersdelight.entity.block.CookingPotBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.screen.CookingPotScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class RBUtils {
    public static CookingPotBlockEntity trickGetTE(ScreenHandler screenHandler) {
        return ((CookingPotScreenHandler) screenHandler).tileEntity;
    }
    public static Slot trickGetSlot(ScreenHandler screenHandler, int i) {
        return screenHandler.getSlot(i);
    }
}
