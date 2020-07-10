package me.pepperbell.setfilter.tileentity;

import me.pepperbell.setfilter.container.SetFilterContainer;
import me.pepperbell.setfilter.registration.Blocks;
import me.pepperbell.setfilter.registration.Items;
import me.pepperbell.setfilter.registration.TileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SetFilterTileEntity extends BasicSidedInventoryTileEntity implements ITickableTileEntity, INamedContainerProvider {
	private int mode = 0;
	private int tickCounter = 0;
	
	public SetFilterTileEntity() {
		super(TileEntityTypes.SET_FILTER, 39);
	}
	
	public static TileEntityType<SetFilterTileEntity> createType() {
		TileEntityType<SetFilterTileEntity> tileEntityType = Builder.create(SetFilterTileEntity::new, Blocks.SET_FILTER).build(null);
		tileEntityType.setRegistryName("set_filter");
		return tileEntityType;
	}

	@Override
	public void tick() {
		if (world.isRemote) {
			return;
		}
		if (tickCounter <= 0) {
			//filter();
			tickCounter = 19;
            markDirty();
		} else {
			tickCounter--;
			markDirty();
		}
	}
	
	// TODO
	public void filter() {
		/*
		itemHandler.ifPresent(handler -> {
            ItemStack stack = handler.getStackInSlot(0);
            if (stack.getItem() == Items.DIAMOND) {
                handler.extractItem(0, 1, false);
                tickCounter = 19;
                markDirty();
            }
        });
        */
		
		//int[] itemSlots = new int[5];
		ItemStack itemStack;
		//this.count(itemIn);
		for (int i=0; i<this.getSizeInventory(); i++) {
			itemStack = this.getStackInSlot(i);
			if (itemStack.isEmpty()) {
				continue;
			}
			
			/*
			for (int j=0; j<5; j++) {
				if (itemStack.getItem() == is1.getItem() && itemStack.getCount() >= is1.getCount()) {
					itemSlots[j] = i;
					break
				}
			}
			*/
		}
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int value) {
		mode = value;
		this.markDirty();
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index >= 27 && index < 36) {
    		return false;
    	}
    	if (index >= 36 && index < 39) {
    		return stack.getItem() == Items.SET_FILTER_MODULE;
    	}
        return true;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (index >= 27 && index < 36) {
			return true;
		}
		return false;
	}
	
	@Override
    public void read(CompoundNBT compound) {
		super.read(compound);
        mode = compound.getInt("Mode");
        tickCounter = compound.getInt("Counter");
    }
	
	@Override
    public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("Mode", mode);
        compound.putInt("Counter", tickCounter);
        return compound;
    }
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.set_filter");
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new SetFilterContainer(id, this, playerInventory, playerEntity);
	}
}