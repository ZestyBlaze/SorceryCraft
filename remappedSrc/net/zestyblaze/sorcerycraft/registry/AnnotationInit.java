package net.zestyblaze.sorcerycraft.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import net.zestyblaze.sorcerycraft.config.ModConfig;

import java.util.Collections;

public class AnnotationInit {
    public static void registerAnnotations() {
        GuiRegistry guiRegistry = AutoConfig.getGuiRegistry(ModConfig.class);
        guiRegistry.registerAnnotationProvider((s, field, config, defaults, guiRegistryAccess) -> {
            ModConfig.UsePercentage bounds = field.getAnnotation(ModConfig.UsePercentage.class);
            return Collections.singletonList(ConfigEntryBuilder.create().startIntSlider(new TranslatableText(s), MathHelper.ceil(Utils.getUnsafely(field, config, 0.0) * 100), MathHelper.ceil(bounds.min() * 100), MathHelper.ceil(bounds.max() * 100)).setDefaultValue(() -> MathHelper.ceil((double) Utils.getUnsafely(field, defaults) * 100)).setSaveConsumer((newValue) -> Utils.setUnsafely(field, config, newValue / 100d)).setTextGetter(integer -> new LiteralText(String.format("%d%%", integer))).build());
        }, field -> field.getType() == Double.TYPE || field.getType() == Double.class, ModConfig.UsePercentage.class);

    }
}
