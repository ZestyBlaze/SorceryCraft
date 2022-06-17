package net.zestyblaze.sorcerycraft.registry;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.SorceryCraft;

import java.util.Objects;

public class ContainerInit {
    public static void registerContainers() {
        ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(SorceryCraft.MODID, "casting_table"), ((syncId, identifier, player, buf) -> {
            final World level = player.world;
            final BlockPos pos = buf.readBlockPos();
            return Objects.requireNonNull(level.getBlockState(pos).createScreenHandlerFactory(player.world, pos)).createMenu(syncId, player.getInventory(), player);
        }));
    }
}
