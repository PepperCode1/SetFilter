package me.pepperbell.setfilter.container;

import me.pepperbell.setfilter.registration.ContainerTypes;
import me.pepperbell.setfilter.screen.SetFilterModuleScreen;
import me.pepperbell.setfilter.util.ItemInventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.items.ItemStackHandler;

public class SetFilterModuleContainer extends BaseContainer {
	public static final IScreenFactory<SetFilterModuleContainer, SetFilterModuleScreen> SCREEN_FACTORY = SetFilterModuleScreen::new;
	
    private PlayerInventory playerInventory;
	private PlayerEntity playerEntity;
	private int currentSlot;
	private ItemStack itemStack;
	private ItemStackHandler itemHandler;
	
	public SetFilterModuleContainer(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		super(ContainerTypes.SET_FILTER_MODULE, windowId);
		
		this.playerInventory = playerInventory;
		this.playerEntity = playerEntity;
		
		this.currentSlot = this.playerInventory.currentItem;
		this.itemStack = this.playerInventory.getCurrentItem();
		this.itemHandler = ItemInventoryHelper.load(itemStack);
		
		this.addSlotRow(this.itemHandler, 0, 5, 46, 30, 18);
		
		this.addPlayerInventorySlots(this.playerInventory, 10, 70, this.currentSlot);
	}
	
	public static ContainerType<SetFilterModuleContainer> createType() {
		ContainerType<SetFilterModuleContainer> containerType = IForgeContainerType.create((int windowId, PlayerInventory playerInventory, PacketBuffer data) -> {
			ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
			return new SetFilterModuleContainer(windowId, playerInventory, playerEntity);
		});
		containerType.setRegistryName("set_filter_module");
		return containerType;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		ItemInventoryHelper.save(this.itemStack, this.itemHandler);
	}
	
	@Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            
            if (index < 5) { // item inventory
                if (!this.mergeItemStack(stack, 5, 41, true)) { // merge with player inventory
                    return ItemStack.EMPTY;
                }
                //slot.onSlotChange(stack, itemStack);
            } else { // player inventory
            	if (!this.mergeItemStack(stack, 0, 5, false)) { // merge with item inventory
                	if (index < 32) { // not hotbar
                        if (!this.mergeItemStack(stack, 32, 41, false)) { // merge with hotbar
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 41) { // hotbar
                    	if (!this.mergeItemStack(stack, 5, 32, false)) { // merge with not hotbar
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
		return playerIn.inventory.getCurrentItem() == this.itemStack;
	}
}