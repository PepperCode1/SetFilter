package me.pepperbell.setfilter.network;

import me.pepperbell.setfilter.SetFilter;
import me.pepperbell.setfilter.network.message.UpdateSetFilterMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkManager {
	private final String protocolVersion = Integer.toString(1);
	private final SimpleChannel channel = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(SetFilter.MODID, "main"))
			.networkProtocolVersion(() -> protocolVersion)
			.clientAcceptedVersions(protocolVersion::equals)
			.serverAcceptedVersions(protocolVersion::equals)
			.simpleChannel();
	
	public void setup() {
		int id = 0;
		
		channel.registerMessage(id++, UpdateSetFilterMessage.class, UpdateSetFilterMessage::encode, UpdateSetFilterMessage::decode, UpdateSetFilterMessage::handle);
	}
	
	public <MSG> void sendToServer(MSG message) {
		channel.sendToServer(message);
	}
	
	public <MSG> void send(PacketTarget target, MSG message) {
		channel.send(target, message);
	}
}