package com.alostmagic.switchz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.TitleScreen;

import net.neoforged.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class SwitchZClientActions {

    private SwitchZClientActions() {
    }

    public static void switchOfflineAccount(String newName) {
        try {
            Minecraft minecraft = Minecraft.getInstance();
            User oldUser = minecraft.getUser();

            UUID uuid = UUID.nameUUIDFromBytes(
                ("OfflinePlayer:" + newName).getBytes(StandardCharsets.UTF_8)
            );

            User newUser = new User(
                newName,
                uuid,
                oldUser.getAccessToken(),
                oldUser.getXuid(),
                oldUser.getClientId()
            );

            Field userField = ObfuscationReflectionHelper.findField(
                Minecraft.class,
                "user"
            );
            userField.setAccessible(true);
            userField.set(minecraft, newUser);

            if (minecraft.level != null) {
                minecraft.disconnect(new TitleScreen(), false);
            }

            minecraft.setScreen(new TitleScreen());
            SwitchZ.LOGGER.info("Switched offline account to {}", newName);
        } catch (ReflectiveOperationException exception) {
            SwitchZ.LOGGER.error("Failed to switch account", exception);
        }
    }
}
