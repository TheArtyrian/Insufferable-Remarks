package net.artyrian.insufferable;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

import java.util.List;

@EventBusSubscriber(modid = InsRemarks.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class InsRemarksNFClient
{
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event)
    {

    }

    @SubscribeEvent
    public static void registerMenus(RegisterMenuScreensEvent event)
    {

    }

    @SubscribeEvent
    public static void registerRenderLayerDef(EntityRenderersEvent.RegisterLayerDefinitions event)
    {

    }

    @SubscribeEvent
    public static void registerEntityRender(EntityRenderersEvent.RegisterRenderers event)
    {

    }

    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event)
    {
        InsRemarksClient.doTintsBlock();

        for (Pair<BlockColor, List<Block>> pair : InsRemarksClient.blockColors)
        {
            for (Block block : pair.getSecond())
            {
                event.register(pair.getFirst(), block);
            }
        }
    }

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event)
    {
        InsRemarksClient.doTintsItem();

        for (Pair<ItemColor, List<ItemLike>> pair : InsRemarksClient.itemColors)
        {
            for (ItemLike item : pair.getSecond())
            {
                event.register(pair.getFirst(), item);
            }
        }
    }

    @SubscribeEvent
    public static void registerBlockEntityRender(EntityRenderersEvent.RegisterRenderers event)
    {

    }
}
