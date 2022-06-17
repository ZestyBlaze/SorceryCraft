package net.zestyblaze.sorcerycraft.block;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.zestyblaze.sorcerycraft.SorceryCraft;
import net.zestyblaze.sorcerycraft.client.gui.CastingTableScreenHandler;
import net.zestyblaze.sorcerycraft.registry.StatInit;
import net.zestyblaze.sorcerycraft.util.spell.SpellServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CastingTableBlock extends Block {
    public CastingTableBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(2.0f, 6.0f));
    }

    @Override
    public ActionResult onUse(@NotNull BlockState state, World world, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand hand, @NotNull BlockHitResult hit) {
        if (!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(new Identifier(SorceryCraft.MODID, "casting_table"), player, (buf) -> buf.writeBlockPos(pos));
            player.incrementStat(StatInit.INTERACT_WITH_CASTING_TABLE_STAT);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(@NotNull BlockState state, @NotNull World level, @NotNull BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, inventory, player) -> {
            if(!player.getWorld().isClient) {
                ((SpellServerPlayerEntity)player).sync();
            }
            return new CastingTableScreenHandler(i, inventory, ScreenHandlerContext.create(level, pos));
        }, new TranslatableText("container." + SorceryCraft.MODID + ".casting_table"));
    }
}
