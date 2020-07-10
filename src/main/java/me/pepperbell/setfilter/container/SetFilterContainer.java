package me.pepperbell.setfilter.container;

import me.pepperbell.setfilter.registration.ContainerTypes;
import me.pepperbell.setfilter.registration.Items;
import me.pepperbell.setfilter.screen.SetFilterScreen;
import me.pepperbell.setfilter.tileentity.SetFilterTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class SetFilterContainer extends BaseContainer {
	public static final IScreenFactory<SetFilterContainer, SetFilterScreen> SCREEN_FACTORY = SetFilterScreen::new;
	
	private SetFilterTileEntity tileEntity;
    private PlayerInventory playerInventory;
	private PlayerEntity playerEntity;
	
	public SetFilterContainer(int windowId, SetFilterTileEntity tileEntity, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		super(ContainerTypes.SET_FILTER, windowId);

		this.tileEntity = tileEntity;
		this.playerInventory = playerInventory;
		this.playerEntity = playerEntity;
		
        this.addSlotBox(this.tileEntity, 0, 9, 3, 10, 0, 18, 18); // input
        this.addSlotRow(this.tileEntity, 27, 9, 10, 58, 18, // output
        	(IInventory inventoryIn, int index, int xPosition, int yPosition) ->
        	new Slot(inventoryIn, index, xPosition, yPosition) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
        	});
        this.addSlotRow(this.tileEntity, 36, 3, 64, 80, 18, // filter modules
        	(IInventory inventoryIn, int index, int xPosition, int yPosition) ->
        	new Slot(inventoryIn, index, xPosition, yPosition) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem() == Items.SET_FILTER_MODULE;
				}
        	});
        
		this.addPlayerInventorySlots(this.playerInventory, 10, 100);
		
		this.trackInt(new IntReferenceHolder() {
	        @Override
	        public int get() { // check value on server using this method
	        	return SetFilterContainer.this.tileEntity.getMode();
	        }
	
	        @Override
	        public void set(int value) { // if server value changes, set value on client using this method
	        	SetFilterContainer.this.tileEntity.setMode(value);
	        }
	    });
	}
	
	public static ContainerType<SetFilterContainer> createType() {
		ContainerType<SetFilterContainer> containerType = IForgeContainerType.create((int windowId, PlayerInventory playerInventory, PacketBuffer data) -> {
			ClientWorld world = Minecraft.getInstance().world;
			BlockPos pos = data.readBlockPos();
			SetFilterTileEntity tileEntity = (SetFilterTileEntity) world.getTileEntity(pos);
			ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
			return new SetFilterContainer(windowId, tileEntity, playerInventory, playerEntity);
		});
		containerType.setRegistryName("set_filter");
		return containerType;
	}
	
	public SetFilterTileEntity getTileEntity() {
		return this.tileEntity;
	}
	
	@Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            
            if (index < 39) { // block inventory
                if (!this.mergeItemStack(stack, 39, 75, true)) { // merge with player inventory
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemStack);
            } else { // player inventory
                if (stack.getItem() == Items.SET_FILTER_MODULE) {
                    if (!this.mergeItemStack(stack, 36, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.mergeItemStack(stack, 0, 27, false)) { // merge with block input inventory
                	if (index < 66) { // not hotbar
                        if (!this.mergeItemStack(stack, 66, 75, false)) { // merge with hotbar
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 75) { // hotbar
                    	if (!this.mergeItemStack(stack, 39, 66, false)) { // merge with not hotbar
                    		return ItemStack.EMPTY;
                    	}
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemStack;
    }
    
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.tileEntity.isUsableByPlayer(playerIn);
	}
}