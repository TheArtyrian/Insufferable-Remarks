package net.artyrian.insufferable.mixin.ui.death;

import net.artyrian.insufferable.InsRemarks;
import net.artyrian.insufferable.definition.data.DeathMsgRenderer;
import net.artyrian.insufferable.mixin.ui.ScreenMixin;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends ScreenMixin
{
    @Shadow @Final private boolean hardcore;

    @Unique private DeathMsgRenderer insRem$deathMsgRen;

    @Inject(method = "init", at = @At("TAIL"))
    public void insRem$doChecks(CallbackInfo ci)
    {
        this.insRem$deathMsgRen = InsRemarks.msgManager.getMessage(this.minecraft.player, this.hardcore, this.width, this.height);
        if (!this.insRem$deathMsgRen.isDisabled()) {
            this.insRem$deathMsgRen.concatPlayername(this.minecraft.getUser().getName());
            this.insRem$deathMsgRen.build();
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void insRem$renderDeathRenderer(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        if (insRem$deathMsgRen != null) insRem$deathMsgRen.render(context, this.font, mouseX, mouseY, this.width, delta);
    }
}
