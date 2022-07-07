package net.zestyblaze.sorcerycraft.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.zestyblaze.sorcerycraft.config.SCModConfig;

import java.util.Collections;

public class SCAnnotationInit {
    public static void registerAnnotations() {
        GuiRegistry guiRegistry = AutoConfig.getGuiRegistry(SCModConfig.class);
        guiRegistry.registerAnnotationProvider((s, field, config, defaults, guiRegistryAccess) -> {
            SCModConfig.UsePercentage bounds = field.getAnnotation(SCModConfig.UsePercentage.class);
            return Collections.singletonList(ConfigEntryBuilder.create().startIntSlider(Component.literal(s), Mth.ceil(Utils.getUnsafely(field, config, 0.0) * 100), Mth.ceil(bounds.min() * 100), Mth.ceil(bounds.max() * 100)).setDefaultValue(() -> Mth.ceil((double) Utils.getUnsafely(field, defaults) * 100)).setSaveConsumer((newValue) -> Utils.setUnsafely(field, config, newValue / 100d)).setTextGetter(integer -> Component.literal(String.format("%d%%", integer))).build());
        }, field -> field.getType() == Double.TYPE || field.getType() == Double.class, SCModConfig.UsePercentage.class);

    }
}
