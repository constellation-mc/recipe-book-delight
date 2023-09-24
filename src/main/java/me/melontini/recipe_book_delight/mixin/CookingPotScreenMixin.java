package me.melontini.recipe_book_delight.mixin;

import com.nhoryzon.mc.farmersdelight.client.screen.CookingPotScreen;
import com.nhoryzon.mc.farmersdelight.entity.block.screen.CookingPotScreenHandler;
import me.melontini.recipe_book_delight.client.CookingPotRecipeBook;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CookingPotScreen.class)
public abstract class CookingPotScreenMixin extends HandledScreen<CookingPotScreenHandler> implements RecipeBookProvider {
    @Shadow protected abstract void renderHeatIndicatorTooltip(DrawContext ctx, int mouseX, int mouseY);

    public CookingPotScreenMixin(CookingPotScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Unique
    public final CookingPotRecipeBook recipeBook = new CookingPotRecipeBook();
    @Unique
    private boolean narrow;

    @Override
    public void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, (AbstractRecipeScreenHandler<?>) (Object) this.handler);
        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
        this.addSelectableChild(this.recipeBook);
        this.setInitialFocus(this.recipeBook);
        this.addDrawableChild(new TexturedButtonWidget(this.x + 5, this.height / 2 - 49, 20, 18, RecipeBookWidget.BUTTON_TEXTURES, buttonWidget -> {
            this.recipeBook.reset();
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            buttonWidget.setPosition(this.x + 5, this.height / 2 - 49);
        }));
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.recipeBook.update();
    }

    /**
     * @author melontini
     * @reason recipe book rendering
     */
    @Overwrite
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx, mouseX, mouseY, delta);
        if (this.recipeBook.isOpen() && this.narrow) {
            this.drawBackground(ctx, delta, mouseX, mouseY);
            this.recipeBook.render(ctx, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(ctx, mouseX, mouseY, delta);
            super.render(ctx, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(ctx, this.x, this.y, true, delta);
        }

        this.renderHeatIndicatorTooltip(ctx, mouseX, mouseY);
        this.drawMouseoverTooltip(ctx, mouseX, mouseY);
        this.recipeBook.drawTooltip(ctx, this.x, this.y, mouseX, mouseY);
    }

    @Override
    protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(xPosition, yPosition, width, height, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        } else {
            return this.narrow && this.recipeBook.isOpen() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double) left
                || mouseY < (double) top
                || mouseX >= (double) (left + this.backgroundWidth)
                || mouseY >= (double) (top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
    }

    @Override
    protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
        super.onMouseClick(slot, invSlot, clickData, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    @Override
    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }
}
