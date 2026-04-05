package com.alostmagic.switchz;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mod(SwitchZ.MODID)
public class SwitchZ {

    public static final String MODID = "switchz";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SwitchZ() {
        LOGGER.info("SwitchZ loaded");
    }

    public static void switchOfflineAccount(String newName) {
        try {
            Minecraft mc = Minecraft.getInstance();
            User oldUser = mc.getUser();

            UUID uuid = UUID.nameUUIDFromBytes(
                ("OfflinePlayer:" + newName).getBytes(StandardCharsets.UTF_8)
            );

            User newUser = new User(
                newName,
                uuid,
                oldUser.getAccessToken(),
                oldUser.getXuid(),
                oldUser.getClientId(),
                User.Type.LEGACY
            );

            Field userField = Minecraft.class.getDeclaredField("user");
            userField.setAccessible(true);
            userField.set(mc, newUser);

            if (mc.level != null) {
                mc.disconnect();
            }

            mc.setScreen(new TitleScreen());

            LOGGER.info("Switched offline account to {}", newName);

        } catch (Exception e) {
            LOGGER.error("Failed to switch account", e);
        }
    }
}
