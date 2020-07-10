package me.pepperbell.setfilter.screen.button;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public abstract class MultiImageButton extends AbstractButton {
	private final ResourceLocation texture;
	private final int xTexStart;
	private final int yTexStart;
	
	public MultiImageButton(int xIn, int yIn, int widthIn, int heightIn, ResourceLocation textureIn, int xTexStartIn, int yTexStartIn) {
		this(xIn, yIn, widthIn, heightIn, textureIn, xTexStartIn, yTexStartIn, "");
	}
	
	public MultiImageButton(int xIn, int yIn, int widthIn, int heightIn, ResourceLocation textureIn, int xTexStartIn, int yTexStartIn, String msg) {
		super(xIn, yIn, widthIn, heightIn, msg);
		texture = textureIn;
		xTexStart = xTexStartIn;
		yTexStart = yTexStartIn;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		GlStateManager.disableDepthTest();
		
		int xTex = xTexStart + getTexture()*width;
		
		int yTex = yTexStart;
		if (this.isHovered()) {
			yTex += height;
		}
		
		blit(x, y, (float)xTex, (float)yTex, width, height, 256, 256);
		GlStateManager.enableDepthTest();
	}
	
	public abstract int getTexture();
}