package com.alostmagic.switchz;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.MOD
)
public class SwitchZClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        SwitchZ.LOGGER.info("HELLO FROM CLIENT SETUP");
        SwitchZ.LOGGER.info(
            "MINECRAFT NAME >> {}",
            Minecraft.getInstance().getUser().getName()
        );
    }
}
