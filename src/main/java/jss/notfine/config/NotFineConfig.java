package jss.notfine.config;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;
import jss.notfine.NotFine;

import java.io.File;

public class NotFineConfig {

    private static final String CATEGORY_TOGGLE = "toggle";

    public static boolean betterBlockFaceCulling;

    public static void loadSettings() {
        File configFile = new File(Launch.minecraftHome + File.separator + "config" + File.separator + NotFine.MODID + File.separator + "notfine.cfg");
        Configuration config = new Configuration(configFile);

        config.setCategoryComment(CATEGORY_TOGGLE, "Toggle mod features.");
        betterBlockFaceCulling = config.getBoolean("betterBlockFaceCulling", CATEGORY_TOGGLE, true,
            "Use more accurate block face culling when building chunk meshes.");

        if(config.hasChanged()) {
            config.save();
        }
    }

}
