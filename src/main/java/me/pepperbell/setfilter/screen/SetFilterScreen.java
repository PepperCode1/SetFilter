package me.pepperbell.setfilter.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import me.pepperbell.setfilter.SetFilter;
import me.pepperbell.setfilter.container.SetFilterContainer;
import me.pepperbell.setfilter.screen.button.SetFilterModeButton;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SetFilterScreen extends ContainerScreen<SetFilterContainer> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(SetFilter.MODID, "textures/gui/container/set_filter.png");
	
	private SetFilterModeButton modeButton;
	
	public SetFilterScreen(SetFilterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);		
	}
	
	@Override
	protected void init() {
		super.init();
		modeButton = new SetFilterModeButton(this, 16, 16);
		/*
		System.out.println(this.width);
		System.out.println(this.height);
		System.out.println(this.guiLeft);
		System.out.println(this.guiTop);
		System.out.println(this.xSize);
		System.out.println(this.ySize);*/
	}
	
	@Override
	public void tick() {
		super.tick();
        this.addButton(modeButton);
	}
	
	@Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        
        super.render(mouseX, mouseY, partialTicks);
        
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
        modeButton.renderToolTip(mouseX, mouseY);
	}
}