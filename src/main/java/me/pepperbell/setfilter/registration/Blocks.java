package me.pepperbell.setfilter.registration;

import java.util.ArrayList;
import java.util.List;

import me.pepperbell.setfilter.block.SetFilterBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Blocks {
	private static final List<Block> blocks = new ArrayList<>();
	
	public static final Block SET_FILTER = register(new SetFilterBlock());
	
	// register methods
	// custom Block class
	private static Block register(String registryName, Block block) {
		return register(block.setRegistryName(registryName));
	}
	private static Block register(Block block) {
		blocks.add(block);
		return block;
	}
	// new Block
	private static Block register(String registryName, Block.Properties properties) {
		return register(new Block(properties).setRegistryName(registryName));
	}
	
	// register everything in blocks
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    	event.getRegistry().registerAll(blocks.toArray(new Block[0]));
	}
}