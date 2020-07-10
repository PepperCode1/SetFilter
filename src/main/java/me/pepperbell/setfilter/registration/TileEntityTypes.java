package me.pepperbell.setfilter.registration;

import java.util.ArrayList;
import java.util.List;

import me.pepperbell.setfilter.tileentity.SetFilterTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityTypes {
	private static final List<TileEntityType<?>> tileEntityTypes = new ArrayList<>();
	
	public static final TileEntityType<SetFilterTileEntity> SET_FILTER = register(SetFilterTileEntity.createType());
	
	private static <T extends TileEntity> TileEntityType<T> register(TileEntityType<T> tileEntityType) {
		tileEntityTypes.add(tileEntityType);
		return tileEntityType;
	}
	
	// register everything in tileEntityTypes
	@SubscribeEvent
	public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().registerAll(tileEntityTypes.toArray(new TileEntityType<?>[0]));
	}
}