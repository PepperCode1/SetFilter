package me.pepperbell.setfilter.registration;

import java.util.ArrayList;
import java.util.List;

import me.pepperbell.setfilter.container.SetFilterContainer;
import me.pepperbell.setfilter.container.SetFilterModuleContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypes {
	private static final List<ContainerType<?>> containerTypes = new ArrayList<>();
	
	public static final ContainerType<SetFilterContainer> SET_FILTER = register(SetFilterContainer.createType());
	public static final ContainerType<SetFilterModuleContainer> SET_FILTER_MODULE = register(SetFilterModuleContainer.createType());
	
	private static <T extends Container> ContainerType<T> register(ContainerType<T> containerType) {
		containerTypes.add(containerType);
		return containerType;
	}
	
	// register everything in containerTypes
	@SubscribeEvent
	public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().registerAll(containerTypes.toArray(new ContainerType<?>[0]));
	}
}