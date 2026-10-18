package net.artyrian.insufferable;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = InsRemarks.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class InsRemarksNFEvent
{
    @SubscribeEvent
    public static void registerResourcesReloadClient(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(InsRemarks.msgManager);
    }
}