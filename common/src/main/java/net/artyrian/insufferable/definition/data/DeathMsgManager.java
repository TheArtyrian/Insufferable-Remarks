package net.artyrian.insufferable.definition.data;

import com.mojang.datafixers.util.Pair;
import net.artyrian.insufferable.InsRemarks;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.vertisoft.vectorlib.VectorLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class DeathMsgManager extends SimplePreparableReloadListener<Pair<List<String>, List<String>>>
{
    private static final String LOCATION = "texts/" + InsRemarks.MOD_ID + "/death_messages.txt";
    private static final String LOCATION_HARDCORE = "texts/" + InsRemarks.MOD_ID + "/death_messages_hardcore.txt";
    private static final RandomSource RANDOM = RandomSource.create();

    private static final List<String> ENHANCED_VIS_MSGS = List.of(
            "...are we serious.",
            "Traitor.",
            "But I thought what we had was special!",
            "Was I not funny enough for you?",
            "How could this happen to me?! I'm funny, I swear!",
            "Jerk.",
            "You didn't NEED two death message mods, dude.",
            "It's either ME, or the other guy. Pick one.",
            "That's it. I'm filing for divorce.",
            "Was I not good enough?!",
            "I'm all for sharing screens, but this is ridiculous!",
            "Look at these mods, stealing my job!",
            "Oh god, it's another death message mod.",
            "Welp, I know when I'm not needed!",
            "Nope. Don't feel like it today.",
            "Why would you do this to me, PLAYERNAME...",
            "Yo! My eyes are up HERE!",
            "How am I supposed to react to this",
            "Live Insufferable Remarks Reaction:"
    );

    private final List<String> messages = new ArrayList<>();
    private final List<String> hardcoreMessages = new ArrayList<>();
    private final List<DeathMsgScenario> scenarios = new ArrayList<>();

    public DeathMsgManager()
    {
        /**
         * This is an example of how you would add custom scenarios for your own death msg rendering.
         */
        this.addCustom((player, isHardcore, w, h) -> {
            if (InsRemarks.evilModsEnabled()) {
                return new DeathMsgRenderer(ENHANCED_VIS_MSGS.get(RANDOM.nextInt(ENHANCED_VIS_MSGS.size())), w / 2, 32);
            }
            return null;
        });
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    protected Pair<List<String>, List<String>> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller)
    {
        Set<String> sets = resourceManager.getNamespaces();

        List<String> normal = new ArrayList<>();
        List<String> hardcore = new ArrayList<>();

        for (String namespace : sets)
        {
            applyList(normal, namespace, LOCATION);
            applyList(hardcore, namespace, LOCATION_HARDCORE);
        }

        if (normal.isEmpty()) normal.add("missingno");
        if (hardcore.isEmpty()) normal.add("missingno but hardcore");

        return Pair.of(normal, hardcore);
    }

    @Override
    protected void apply(Pair<List<String>, List<String>> strings, ResourceManager resourceManager, ProfilerFiller profilerFiller)
    {
        this.messages.clear();
        this.hardcoreMessages.clear();

        this.messages.addAll(strings.getFirst());
        this.hardcoreMessages.addAll(strings.getSecond());
    }

    /////////////////////////////////////////////////////////////////////////

    public void addCustom(DeathMsgScenario scenario) { this.scenarios.add(scenario); }

    private void applyList(List<String> target, String namespace, String path)
    {
        try
        {
            ResourceLocation loc = VectorLib.id(namespace, path);
            BufferedReader bufferedReader = Minecraft.getInstance().getResourceManager().openAsReader(loc);
            List<String> base_up;

            try {
                base_up = bufferedReader.lines().map(String::trim).filter(hsh -> hsh.hashCode() != 125780783).toList();
            }
            catch (Throwable var7) {
                if (bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    }
                    catch (Throwable var6){
                        var7.addSuppressed(var6);
                    }
                }
                throw var7;
            }
            if (bufferedReader != null) bufferedReader.close();

            if (!base_up.isEmpty()) {
                InsRemarks.LOGGER.info("Added death messages from {}", loc);
                target.addAll(base_up);
            }
            else InsRemarks.LOGGER.error("Could not read death messages from {} - the list was empty", loc);
        }
        catch (IOException ignored) {

        }
    }

    public DeathMsgRenderer getMessage(Player player, boolean isHardcore, int width, int height)
    {
        if (!InsRemarks.CONFIG.isEnabled()) {
            DeathMsgRenderer ren = new DeathMsgRenderer("", width / 2, 115);
            ren.disable();
            return ren;
        }

        DeathMsgRenderer preten;
        for (DeathMsgScenario scen : this.scenarios)
        {
            preten = scen.getRenderer(player, isHardcore, width, height);
            if (preten != null) return preten;
        }

        if (isHardcore) return new DeathMsgRenderer(this.hardcoreMessages.get(RANDOM.nextInt(this.hardcoreMessages.size())), width / 2, 115);
        return new DeathMsgRenderer(this.messages.get(RANDOM.nextInt(this.messages.size())), width / 2, 115);
    }
}
