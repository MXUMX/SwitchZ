package com.alostmagic.switchz;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT
)
public class SwitchZClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (SwitchZKeybinds.OPEN_UI.consumeClick() && mc.screen == null) {
            mc.setScreen(new SwitchZScreen());
        }
    }
}
