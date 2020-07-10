package me.pepperbell.setfilter.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class BaseContainer extends Container {
	protected BaseContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	// from IInventory
	protected int addSlotRow(IInventory inventory, int index, int amount, int x, int y, int xDistance) {
        return this.addSlotRow(inventory, index, amount, x, y, xDistance, Slot::new);
    }
	// from IInventory using SlotFactory
	protected int addSlotRow(IInventory inventory, int index, int amount, int x, int y, int xDistance, ISlotFactory slotFactory) {
        for (int i = 0; i < amount; i++) {
            this.addSlot(slotFactory.create(inventory, index, x, y));
            x += xDistance;
            index++;
        }
        return index;
    }
	// from IItemHandler
	protected int addSlotRow(IItemHandler itemHandler, int index, int amount, int x, int y, int xDistance) {
        for (int i = 0; i < amount; i++) {
            this.addSlot(new SlotItemHandler(itemHandler, index, x, y));
            x += xDistance;
            index++;
        }
        return index;
    }
	
	// from IInventory
	protected int addSlotBox(IInventory inventory, int index, int xAmount, int yAmount, int x, int y, int xDistance, int yDistance) {
		return this.addSlotBox(inventory, index, xAmount, yAmount, x, y, xDistance, yDistance, Slot::new);
    }
	// from IInventory using SlotFactory
	protected int addSlotBox(IInventory inventory, int index, int xAmount, int yAmount, int x, int y, int xDistance, int yDistance, ISlotFactory slotFactory) {
        for (int j = 0; j < yAmount; j++) {
            index = this.addSlotRow(inventory, index, xAmount, x, y, xDistance, slotFactory);
            y += yDistance;
        }
        return index;
    }
    // from IItemHandler
	protected int addSlotBox(IItemHandler itemHandler, int index, int xAmount, int yAmount, int x, int y, int xDistance, int yDistance) {
        for (int j = 0; j < yAmount; j++) {
            index = this.addSlotRow(itemHandler, index, xAmount, x, y, xDistance);
            y += yDistance;
        }
        return index;
    }
	

    protected void addPlayerInventorySlots(PlayerInventory playerInventory, int leftCol, int topRow) {
        // player inventory
        this.addSlotBox(playerInventory, 9, 9, 3, leftCol, topRow, 18, 18);

        // hotbar
        topRow += 58;
        this.addSlotRow(playerInventory, 0, 9, leftCol, topRow, 18);
    }
    
    protected void addPlayerInventorySlots(PlayerInventory playerInventory, int leftCol, int topRow, int lockedHotbarSlot) {
        // player inventory
        this.addSlotBox(playerInventory, 9, 9, 3, leftCol, topRow, 18, 18);

        // hotbar
        topRow += 58;
        this.addSlotRow(playerInventory, 0, 9, leftCol, topRow, 18, 
	        (IInventory inventoryIn, int index, int xPosition, int yPosition) ->
        	new Slot(inventoryIn, index, xPosition, yPosition) {
	        	@Override
	        	public boolean canTakeStack(PlayerEntity playerIn) {
	        		if (index == lockedHotbarSlot) {
	        			return false;
	        		}
	        		return true;
	        	}
	        });
    }
    
    
    protected interface ISlotFactory {
    	Slot create(IInventory inventoryIn, int index, int xPosition, int yPosition);
    }
}