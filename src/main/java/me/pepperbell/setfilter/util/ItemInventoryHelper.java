package me.pepperbell.setfilter.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class ItemInventoryHelper {
	public static void create(ItemStack itemStack, int size) {
		ItemStackHandler itemHandler = new ItemStackHandler(size);
		save(itemStack, itemHandler);
	}
	
	public static ItemStackHandler load(ItemStack itemStack) {
		ItemStackHandler itemHandler;
		CompoundNBT nbt = itemStack.getTag();
		if (nbt != null) {
			itemHandler = new ItemStackHandler();
			itemHandler.deserializeNBT(nbt);
			return itemHandler;
		} else {
			return null;
		}
	}
	
	public static void save(ItemStack itemStack, ItemStackHandler itemHandler) {
		CompoundNBT nbt = itemHandler.serializeNBT();
		itemStack.setTag(nbt);
	}
}