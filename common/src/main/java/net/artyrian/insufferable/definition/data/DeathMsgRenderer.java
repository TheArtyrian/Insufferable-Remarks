package net.artyrian.insufferable.definition.data;

import com.mojang.math.Axis;
import net.artyrian.insufferable.InsRemarks;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

/**
 * The renderer for the death message. Supports custom formatting for quotations and the text itself.
 */
public class DeathMsgRenderer
{
    private int x;
    private int y;
    private String string;
    private int directRenderColor = 16777215;
    private boolean splashPop;

    private Style mainStyle;
    private Style quoteStyle;

    private boolean built = false;
    private Component comp;
    private boolean disabled = false;

    public DeathMsgRenderer(String string, Style mainStyle, Style quoteStyle, int x, int y)
    {
        this.string = string;
        this.mainStyle = mainStyle;
        this.quoteStyle = quoteStyle;
        this.x = x;
        this.y = y;

        this.splashPop = InsRemarks.CONFIG.shouldBob();
    }

    public DeathMsgRenderer(String string, int x, int y)
    {
        this(string, Style.EMPTY.withColor(ChatFormatting.YELLOW), Style.EMPTY.withColor(ChatFormatting.YELLOW), x, y);
    }

    /////////////////////////////////////////////////////////////////////////

    public boolean isDisabled() {return this.disabled;}

    public void setDirectColorForSomeReason(int color) { this.directRenderColor = color; }
    public void setBobbing(boolean bob) { this.splashPop = bob; }
    public void disable() {this.disabled = true;}

    public void concatPlayername(String text)
    {
        if (this.built) throw new IllegalArgumentException("The DeathMsgRenderer is already built");
        this.string = this.string.replaceAll("PLAYERNAME", text);
    }

    public void build()
    {
        if (this.built) throw new IllegalArgumentException("The DeathMsgRenderer is already built");
        this.built = true;

        Component pre = Component.literal(this.string).withStyle(this.mainStyle);
        Component quote = Component.literal("\"").withStyle(this.quoteStyle);
        this.comp = Component.empty().append(quote).append(pre).append(quote);
    }

    /////////////////////////////////////////////////////////////////////////

    public void render(GuiGraphics context, Font font, int mouseX, int mouseY, int screenWidth, float delta)
    {
        if (this.comp != null && !this.disabled) {
            context.pose().pushPose();
            context.pose().translate(this.x, this.y, 0.0F);

            if (this.splashPop)
            {
                float f = 1.5F - Mth.abs(Mth.sin((float)(Util.getMillis() % 2000L) / 2000.0F * 6.2831855F) * 0.1F);
                f = f * 0.75F;
                context.pose().scale(f, f, f);
            }

            context.drawCenteredString(font, this.comp, 0, 0, this.directRenderColor);
            context.pose().popPose();
        }
    }
}
