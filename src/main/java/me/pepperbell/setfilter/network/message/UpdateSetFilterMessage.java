package me.pepperbell.setfilter.network.message;

import java.util.function.Supplier;

import me.pepperbell.setfilter.container.SetFilterContainer;
import me.pepperbell.setfilter.tileentity.SetFilterTileEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class UpdateSetFilterMessage {
	private int mode;
	
	public UpdateSetFilterMessage(int mode) {
		this.mode = mode;
	}
	
	public static void encode(UpdateSetFilterMessage message, PacketBuffer buffer) {
		buffer.writeInt(message.mode);
	}
	
	public static UpdateSetFilterMessage decode(PacketBuffer buffer) {
		return new UpdateSetFilterMessage(buffer.readInt());
	}
	
	public static void handle(final UpdateSetFilterMessage message, Supplier<Context> supplier) {
		Context ctx = supplier.get();
		
		ctx.enqueueWork(() -> {
			Container c = ctx.getSender().openContainer;
			if (c instanceof SetFilterContainer) {
				SetFilterContainer container = (SetFilterContainer) c;
				SetFilterTileEntity tileEntity = container.getTileEntity();
				tileEntity.setMode(message.mode);
			}
		});
		
		ctx.setPacketHandled(true);
	}
}