package com.alostmagic.switchz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(
    modid = SwitchZ.MODID,
    value = Dist.CLIENT
)
public class SwitchZScreenButtons {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();

        // Only add to title & multiplayer screens
        if (!(screen instanceof TitleScreen || screen instanceof JoinMultiplayerScreen)) {
            return;
        }

        int margin = 10;
        int width = 90;
        int height = 20;

        int x = screen.width - width - margin;
        int y = margin;

        Button switchButton = Button.builder(
            Component.literal("SwitchZ"),
            btn -> Minecraft.getInstance().setScreen(new SwitchZScreen())
        ).bounds(x, y, width, height).build();

        event.addListener(switchButton);
    }
}
