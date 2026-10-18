package net.artyrian.insufferable;

import net.artyrian.insufferable.definition.data.DeathMsgManager;
import net.artyrian.insufferable.definition.data.DeathMsgScenario;
import net.minecraft.world.entity.player.Player;
import net.vertisoft.vectorlib.VectorLib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class InsRemarks
{
    public static final String MOD_ID = "insufferable_remarks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static InsRemarksConfig CONFIG = new InsRemarksConfig();

    public static final String ENHANCED_ID = "enhancedvisuals";

    public static final boolean DOING_DATAGEN = VectorLib.PLATFORM.isDatagen();

    public static DeathMsgManager msgManager = new DeathMsgManager();

    // For the record I don't actually hate any of the mods here, this is just a joke. I just want the UI to look clean LOL
    public static boolean evilModsEnabled()
    {
        return VectorLib.PLATFORM.isModLoaded(ENHANCED_ID);
    }

    public static void init()
    {
        CONFIG = InsRemarksConfig.load_config();
    }

    public static void customDeathScreen(DeathMsgScenario scenario) {
        InsRemarks.msgManager.addCustom(scenario);
    }
}