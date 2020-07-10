package me.pepperbell.setfilter.item;

import me.pepperbell.setfilter.registration.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroups {
	public static final ItemGroup SET_FILTER = new ItemGroup("setFilter") {
		@OnlyIn(Dist.CLIENT)
      	public ItemStack createIcon() {
    	  	return new ItemStack(Blocks.SET_FILTER);
      	}
    };
}