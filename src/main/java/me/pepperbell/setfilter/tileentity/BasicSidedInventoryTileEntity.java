package me.pepperbell.setfilter.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class BasicSidedInventoryTileEntity extends BasicInventoryTileEntity implements ISidedInventory {
	private int[] allSlots;
	
	public BasicSidedInventoryTileEntity(TileEntityType<?> tileEntityTypeIn, int inventorySize) {
		super(tileEntityTypeIn, inventorySize);
		this.allSlots = new int[inventorySize];
		for (int i=0; i<inventorySize; i++) {
			this.allSlots[i] = i;
		}
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return this.allSlots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return true;
	}
}