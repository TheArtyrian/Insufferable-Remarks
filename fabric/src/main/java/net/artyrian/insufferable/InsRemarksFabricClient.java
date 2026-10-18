package net.artyrian.insufferable;

import net.fabricmc.api.ClientModInitializer;

public class InsRemarksFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        InsRemarksClient.init();
    }
}
