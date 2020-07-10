package me.pepperbell.setfilter.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

public class BasicInventoryTileEntity extends TileEntity implements IInventory {
	private NonNullList<ItemStack> inventory;
	
	public BasicInventoryTileEntity(TileEntityType<?> tileEntityTypeIn, int inventorySize) {
		super(tileEntityTypeIn);
		this.inventory = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
	}
	
	public BasicInventoryTileEntity(TileEntityType<?> tileEntityTypeIn, NonNullList<ItemStack> inventory) {
		super(tileEntityTypeIn);
		this.inventory = inventory;
	}
	
	public void read(CompoundNBT compound) {
		super.read(compound);
		ItemStackHelper.loadAllItems(compound, this.inventory);
	}
	
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.inventory);
		return compound;
	}
	
	@Override
	public void clear() {
		this.inventory.clear();
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
	    }
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= 0 && index < this.getSizeInventory() ? this.inventory.get(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index >= 0 && index < this.getSizeInventory()) {
			this.inventory.set(index, stack);
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	    }
	}
}