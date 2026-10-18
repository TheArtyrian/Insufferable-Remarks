package net.artyrian.insufferable;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(InsRemarks.MOD_ID)
public class InsRemarksNF
{
    public InsRemarksNF(IEventBus eventBus)
    {
        InsRemarks.init();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::payloadSetup);
    }

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event)
    {
        InsRemarksClient.init();
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public void payloadSetup(final RegisterPayloadHandlersEvent event)
    {
        //final PayloadRegistrar reg = event.registrar("V1").executesOn(HandlerThread.NETWORK);
    }
}
