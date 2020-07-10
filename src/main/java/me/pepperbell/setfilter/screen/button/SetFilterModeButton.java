package me.pepperbell.setfilter.screen.button;

import me.pepperbell.setfilter.SetFilter;
import me.pepperbell.setfilter.network.message.UpdateSetFilterMessage;
import me.pepperbell.setfilter.screen.SetFilterScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

public class SetFilterModeButton extends MultiImageButton {
	private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/gui/resource_packs.png");
	
	private SetFilterScreen screen;
	private int mode;
	
	public SetFilterModeButton(SetFilterScreen screen, int x, int y) {
		//super(x, y, 32, 32, 0, 0, 32, TEXTURE, null);
		super(x, y, 32, 32, TEXTURE, 0, 0);
		this.screen = screen;
		this.mode = this.screen.getContainer().getTileEntity().getMode();
	}
	
	@Override
	public void onPress() {
		if (mode < 3) {
			mode++;
		} else {
			mode = 0;
		}
		
		SetFilter.NETWORK.sendToServer(new UpdateSetFilterMessage(mode));
	}
	
	@Override
	public void renderToolTip(int mouseX, int mouseY) {
		if (this.visible) {
			if (this.isHovered()) {
				screen.renderTooltip(getTooltip(), mouseX, mouseY);
			}
		}
	}
	
	private String getTooltip() {
		if (!Screen.hasShiftDown()) {
			return "Hold shift for info";
		} else {
			switch (mode) {
				case 0: return "Mode 1";
				case 1: return "Mode 2";
				case 2: return "Mode 3";
				case 3: return "Mode 4";
				default: return "Something went wrong";
			}
		}
	}

	@Override
	public int getTexture() {
		return mode;
	}
}