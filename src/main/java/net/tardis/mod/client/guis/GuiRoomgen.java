package net.tardis.mod.client.guis;

import java.io.IOException;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.tardis.mod.client.guis.elements.ButtonMonitor;
import net.tardis.mod.common.ars.Room;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.network.NetworkHandler;
import net.tardis.mod.network.packets.MessageChangeRoom;
import net.tardis.mod.Tardis;

public class GuiRoomgen extends GuiScreen{
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Tardis.MODID, "textures/gui/roomgen.png");
	private TileEntityTardis tardis;
	private int index = 0;

	private static int guiWidth = 242;
	private static int guiHeight = 132;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GuiRoomgen.drawMonitorBackground(this, width, height);
		super.drawScreen(mouseX, mouseY, partialTicks);
		Minecraft.getMinecraft().getTextureManager().bindTexture(Room.ROOMS.get(index).getPreview());
		double ratio = 1.77;
		int pX = width / 2 - 100, pY = height / 2 - 50, pHeight = 75, pWidth = (int) Math.floor(pHeight * ratio);
		pX += pWidth / 4;
		BufferBuilder bb = Tessellator.getInstance().getBuffer();
		bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		bb.pos(pX, pY, 200).tex(0, 0).endVertex();
		bb.pos(pX, pY + pHeight, 200).tex(0, 1).endVertex();
		bb.pos(pX + pWidth, pY + pHeight, 200).tex(1, 1).endVertex();
		bb.pos(pX + pWidth, pY, 200).tex(1, 0).endVertex();
		Tessellator.getInstance().draw();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button instanceof ButtonMonitor) {
			((ButtonMonitor)button).doAction();
		}
	}
	
	public static void drawMonitorBackground(GuiScreen screen, int width, int height) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		screen.drawTexturedModalRect(width / 2 - 242 / 2, height / 2 - 132 / 2, 0, 0, 242, 132);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		this.addButton(this.addButton(2, "> Next Room")).addAction(() ->{
			if(index + 1 > Room.ROOMS.size() - 1)
				index = 0;
			else ++index;
		});
		this.addButton(this.addButton(1, "> Prev Room")).addAction(() -> {
			if(index - 1 < 0)
				index = Room.ROOMS.size() - 1;
			else --index;
		});
		this.addButton(this.addButton(0, "> Select Room")).addAction(() -> {
			//save NBT tag with room/corridor name
			Minecraft.getMinecraft().displayGuiScreen(null);
		});
	}

	public ButtonMonitor addButton(int id, String name) {
		return new ButtonMonitor(id, width / 2 - 110, (height / 2 + 50) - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * id, name);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public static int getGuiWidth() {
		return guiWidth;
	}

	public static int getGuiHeight() {
		return guiHeight;
	}
}