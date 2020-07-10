package me.pepperbell.setfilter.item;

import java.util.List;

import javax.annotation.Nullable;

import me.pepperbell.setfilter.container.SetFilterModuleContainer;
import me.pepperbell.setfilter.util.ItemInventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SetFilterModuleItem extends Item implements INamedContainerProvider {
	public SetFilterModuleItem() {
		super(new Item.Properties()
			.group(ItemGroups.SET_FILTER)
			.maxStackSize(1)
		);
		
		this.setRegistryName("set_filter_module");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		
		if (!itemStack.hasTag()) {
			ItemInventoryHelper.create(itemStack, 5);
		}
		
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, this);
				return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
			}
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		Screen screen = Minecraft.getInstance().currentScreen;
		if (screen != null) {
			if (screen.hasShiftDown()) {
				tooltip.add(new StringTextComponent("Info!!"));
				//new TranslationTextComponent("gui.itemInfo.set_filter_module")
			} else {
				tooltip.add(new StringTextComponent("Hold shift for info"));
				//new TranslationTextComponent("gui.itemInfo")
			}
		}
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.set_filter_module");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new SetFilterModuleContainer(id, playerInventory, playerEntity);
	}
}