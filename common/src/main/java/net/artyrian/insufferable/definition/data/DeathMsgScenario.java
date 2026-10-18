package net.artyrian.insufferable.definition.data;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface DeathMsgScenario
{
    DeathMsgRenderer getRenderer(Player player, boolean isHardcore, int width, int height);
}
