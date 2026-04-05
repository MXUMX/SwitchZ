package com.alostmagic.switchz;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.MOD
)
public class SwitchZKeybinds {

    public static KeyMapping OPEN_UI;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        OPEN_UI = new KeyMapping(
            "key.switchz.open_ui",
            GLFW.GLFW_KEY_F9,
            "key.categories.misc"
        );
    }
}
