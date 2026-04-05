package com.alostmagic.switchz;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(SwitchZ.MODID)
public class SwitchZ {

    public static final String MODID = "switchz";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SwitchZ() {
        LOGGER.info("SwitchZ loaded");
    }
}
