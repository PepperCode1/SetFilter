package me.pepperbell.setfilter.registration;

import java.util.ArrayList;
import java.util.List;

import me.pepperbell.setfilter.item.ItemGroups;
import me.pepperbell.setfilter.item.SetFilterModuleItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Items {
	private static final List<Item> items = new ArrayList<>();
	
	public static final Item SET_FILTER = register(Blocks.SET_FILTER, ItemGroups.SET_FILTER);
	
	public static final Item SET_FILTER_MODULE = register(new SetFilterModuleItem());
	
	// register methods
	// Item from Block
	private static Item register(Block block) {
		return register(block, new Item.Properties());
	}
	private static Item register(Block block, ItemGroup itemGroup) {
		return register(block, new Item.Properties().group(itemGroup));
	}
	private static Item register(Block block, Item.Properties properties) {
		return register(new BlockItem(block, properties).setRegistryName(block.getRegistryName()));
	}
	// custom Item/ItemBlock class
	private static Item register(String registryName, Item item) {
		return register(item.setRegistryName(registryName));
	}
	private static Item register(Item item) {
		items.add(item);
		return item;
	}
	// new Item
	private static Item register(String registryName) {
		return register(registryName, new Item.Properties());
	}
	private static Item register(String registryName, ItemGroup itemGroup) {
		return register(registryName, new Item.Properties().group(itemGroup));
	}
	private static Item register(String registryName, Item.Properties properties) {
		return register(registryName, new Item(properties));
	}
	
	// register everything in items
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(items.toArray(new Item[0]));
	}
}