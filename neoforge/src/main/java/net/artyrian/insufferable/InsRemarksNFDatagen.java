package net.artyrian.insufferable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = InsRemarks.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InsRemarksNFDatagen
{
    /** NOTE: Most of the generated asset files are managed inside of Fabric's datagen - this is for NF files ONLY! */
    @SubscribeEvent
    public static void packUpData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
    }
}
