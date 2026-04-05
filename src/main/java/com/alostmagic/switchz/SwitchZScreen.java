package com.alostmagic.switchz;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SwitchZScreen extends Screen {
    private static final int USERNAME_MAX_LENGTH = 16;

    private EditBox usernameBox;

    public SwitchZScreen() {
        super(Component.literal("SwitchZ - Change Username"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        usernameBox = new EditBox(
            this.font,
            centerX - 100,
            centerY - 20,
            200,
            20,
            Component.literal("Username")
        );
        usernameBox.setMaxLength(USERNAME_MAX_LENGTH);
        this.addRenderableWidget(usernameBox);

        this.addRenderableWidget(
            Button.builder(
                Component.literal("Switch & Disconnect"),
                btn -> {
                    String name = usernameBox.getValue().trim();
                    if (!name.isEmpty()) {
                        SwitchZClientActions.switchOfflineAccount(name);
                    }
                }
            ).bounds(
                centerX - 100,
                centerY + 10,
                200,
                20
            ).build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("Cancel"),
                btn -> this.onClose()
            ).bounds(
                centerX - 100,
                centerY + 35,
                200,
                20
            ).build()
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
