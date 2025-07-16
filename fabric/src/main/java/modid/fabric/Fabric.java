package modid.fabric;

import modid.Initializer;
import net.fabricmc.api.ClientModInitializer;

public class Fabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Initializer.INSTANCE.onInitializeClient();
    }
}
