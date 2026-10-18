package net.artyrian.insufferable.exclusive.data;

import net.artyrian.insufferable.InsRemarks;
import net.artyrian.insufferable.definition.data.DeathMsgManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.vertisoft.vectorlib.VectorLib;

public class FabricDeathMsgManager extends DeathMsgManager implements IdentifiableResourceReloadListener
{
    @Override
    public ResourceLocation getFabricId()
    {
        return VectorLib.id(InsRemarks.MOD_ID, "death_message_manager");
    }
}
