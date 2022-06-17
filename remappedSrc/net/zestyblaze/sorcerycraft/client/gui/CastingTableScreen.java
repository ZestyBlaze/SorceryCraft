package net.zestyblaze.sorcerycraft.client.gui;

import Identifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.api.Spell;
import net.zestyblaze.sorcerycraft.network.SelectSpellC2SPacket;
import net.zestyblaze.sorcerycraft.util.spell.SpellHelper;
import org.jetbrains.annotations.NotNull;

public class CastingTableScreen extends HandledScreen<CastingTableScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/villager2.png");
    private int selectedIndex = 0;
    private int indexStartOffset = 0;
    private boolean scrolling = false;

    public CastingTableScreen(CastingTableScreenHandler container, PlayerInventory inventory, Text title) {
        super(container, inventory, title);
        backgroundWidth = 276;
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int i, int j) {
        int titleY = backgroundHeight - 94;
        textRenderer.draw(matrixStack, title, (float) (49 + this.backgroundWidth / 2 - textRenderer.getWidth(title) / 2), 6.0F, 4210752);
        textRenderer.draw(matrixStack, playerInventory.getDisplayName(), 107.0F, (float) titleY, 4210752);
        Text spells = new TranslatableText("container." + SorceryCraft.NAMESPACE + ".spells");
        textRenderer.draw(matrixStack, spells, (float) (5 - textRenderer.getWidth(spells) / 2 + 48), 6.0F, 4210752);
        renderXPCost(matrixStack);
    }

    public void resetIndex() {
        selectedIndex = 0;
        indexStartOffset = 0;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (width - backgroundWidth) / 2;
        int j = (height - backgroundHeight) / 2;
        drawTexture(matrixStack, i, j, getZOffset(), 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 512);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrixStack, mouseX, mouseY);

        renderScrollbar(matrixStack);
        renderItems();

        for (WidgetButtonPage button : buttons) {
            if (button.isHovered()) {
                button.renderToolTip(matrixStack, mouseX, mouseY);
            }
            button.visible = button.index < handler.getRecipes().length;
            if (button.visible) {
                Spell spell = handler.getRecipes()[button.getIndex() + indexStartOffset];
                button.setMessage(SpellHelper.getTranslatedSpell(spell.getID(), spell.getLevel()));
            }
        }
    }

    private void renderScrollbar(MatrixStack matrixStack) {
        Spell[] spells = handler.getRecipes();
        assert client != null;
        client.getTextureManager().bindTexture(TEXTURE);
        int i = (width - backgroundWidth) / 2;
        int j = (height - backgroundHeight) / 2;
        int k = spells.length - 7;

        if (k > 0) {
            int modifier = (int) (((float) indexStartOffset / k) * (1 + 139 - 27));
            drawTexture(matrixStack, i + 94, j + 18 + modifier, getZOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
        } else {
            drawTexture(matrixStack, i + 94, j + 18, getZOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
        }
    }

    private void renderItems() {
        int i = (width - backgroundWidth) / 2;
        int j = (height - backgroundHeight) / 2;
        int k = j + 16 + 1;

        Spell[] spells = handler.getRecipes();
        for (int x = 0; x < spells.length; x++) {
            if (!canScroll(spells.length) || (x >= indexStartOffset && x < 7 + indexStartOffset)) {
                ItemStack itemStack = spells[x].getItemCost();
                itemRenderer.zOffset = 100.0F;
                int y = k + 2;

                itemRenderer.renderInGuiWithOverrides(itemStack, i + 5 + 68, y);
                itemRenderer.renderGuiItemOverlay(textRenderer, itemStack, i + 5 + 68, y);
                itemRenderer.zOffset = 0.0F;
                k += 20;
            }
        }
    }

    private void renderXPCost(MatrixStack matrixStack) {
        if (handler.getRecipes().length > 0) {
            int cost = handler.getRecipes()[selectedIndex].getXPCost();
            int color = 8453920;
            assert client != null;
            assert client.player != null;
            Text string = new TranslatableText("container.repair.cost", cost);
            if (!handler.canTakeResult(playerInventory.player)) {
                color = 16736352;
            }

            int x2 = backgroundWidth - 8;
            int x1 = x2 - textRenderer.getWidth(string);
            fill(matrixStack, x1, 65, x2, 77, 1325400064);
            textRenderer.drawWithShadow(matrixStack, string, (float) x1, 67.0F, color);
        }
    }

    private boolean canScroll(int listSize) {
        return listSize > 7;
    }

    @Override
    public boolean mouseScrolled(double d, double e, double amount) {
        int i = handler.getRecipes().length;
        if (canScroll(i)) {
            int j = i - 7;
            indexStartOffset = (int) ((double) indexStartOffset - amount);
            indexStartOffset = MathHelper.clamp(indexStartOffset, 0, j);
        }

        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int i = handler.getRecipes().length;
        if (scrolling) {
            int j = y + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float) mouseY - (float) j - 13.5F) / ((float) (k - j) - 27.0F);
            f = f * (float) l + 0.5F;
            indexStartOffset = MathHelper.clamp((int) f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        scrolling = false;
        int i = (width - backgroundWidth) / 2;
        int j = (height - backgroundHeight) / 2;
        if (canScroll(handler.getRecipes().length) && mouseX > (double) (i + 94) && mouseX < (double) (i + 94 + 6) && mouseY > (double) (j + 18) && mouseY <= (double) (j + 18 + 139 + 1)) {
            scrolling = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void syncRecipeIndex() {
        handler.setIndex(selectedIndex);
        assert client != null;
        SelectSpellC2SPacket.send(client, selectedIndex);
    }

    private final WidgetButtonPage[] buttons = new WidgetButtonPage[7];

    @Override
    protected void init() {
        super.init();
        int i = (width - backgroundWidth) / 2;
        int j = (height - backgroundHeight) / 2;
        int k = j + 16 + 2;

        for (int l = 0; l < 7; ++l) {
            buttons[l] = addButton(new WidgetButtonPage(i + 5, k, l, (buttonWidget) -> {
                selectedIndex = ((WidgetButtonPage) buttonWidget).getIndex() + indexStartOffset;
                syncRecipeIndex();
            }));
            k += 20;
        }
    }

    @Environment(EnvType.CLIENT)
    public class WidgetButtonPage extends ButtonWidget {
        final int index;

        public WidgetButtonPage(int i, int j, int k, PressAction pressAction) {
            super(i, j, 89, 20, new LiteralText(""), pressAction);
            index = k;
            visible = false;
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
            active = (index + CastingTableScreen.this.indexStartOffset) != CastingTableScreen.this.selectedIndex;
            super.render(matrixStack, mouseX, mouseY, delta);
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (hovered && handler.getRecipes().length > index + indexStartOffset && mouseX > this.x + 65) {
                ItemStack itemStack = handler.getRecipes()[index + indexStartOffset].getItemCost();
                if (!itemStack.isEmpty()) {
                    renderTooltip(matrixStack, itemStack, mouseX, mouseY);
                }
            }
        }
    }
}
