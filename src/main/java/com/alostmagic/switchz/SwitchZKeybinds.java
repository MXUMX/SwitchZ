package com.alostmagic.switchz;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT
)
public class SwitchZKeybinds {

    public static final KeyMapping OPEN_UI = new KeyMapping(
        "key.switchz.open_ui",
        GLFW.GLFW_KEY_F9,
        KeyMapping.Category.GAMEPLAY
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_UI);
    }
}
