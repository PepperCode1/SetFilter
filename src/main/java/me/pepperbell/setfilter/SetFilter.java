package me.pepperbell.setfilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.pepperbell.setfilter.network.NetworkManager;
import net.minecraftforge.fml.common.Mod;

@Mod(SetFilter.MODID)
public class SetFilter {
	public static final String MODID = "setfilter";
	public static SetFilter instance;
	public static final NetworkManager NETWORK = new NetworkManager();
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public SetFilter() {
		instance = this;
	}
	
	public SetFilter getInstance() {
		return instance;
	}
}