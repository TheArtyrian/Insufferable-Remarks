package net.artyrian.insufferable;

import net.artyrian.insufferable.exclusive.data.FabricDeathMsgManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class InsRemarksFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        InsRemarks.init();

        FabricDeathMsgManager msgMan = new FabricDeathMsgManager();
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(msgMan);
        InsRemarks.msgManager = msgMan;
    }
}
