package me.pepperbell.setfilter.setup;

import me.pepperbell.setfilter.SetFilter;
import me.pepperbell.setfilter.container.SetFilterContainer;
import me.pepperbell.setfilter.container.SetFilterModuleContainer;
import me.pepperbell.setfilter.registration.ContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Setup {
	@SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        SetFilter.NETWORK.setup();
    }
	
	@SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        screenSetup();
    }
	
	private static void screenSetup() {
		ScreenManager.registerFactory(ContainerTypes.SET_FILTER, SetFilterContainer.SCREEN_FACTORY);
        ScreenManager.registerFactory(ContainerTypes.SET_FILTER_MODULE, SetFilterModuleContainer.SCREEN_FACTORY);
	}
}