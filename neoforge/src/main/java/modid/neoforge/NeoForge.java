package modid.neoforge;

import modid.Initializer;
import net.neoforged.fml.common.Mod;

@Mod("modid")
public class NeoForge {
    public NeoForge() {
        Initializer.INSTANCE.onInitializeClient();
    }
}
