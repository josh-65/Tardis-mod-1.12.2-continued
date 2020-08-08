package net.tardis.mod.common.screwdriver;

import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.tardis.mod.common.blocks.TBlocks;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.tardis.mod.Tardis;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.util.common.helpers.PlayerHelper;
import net.tardis.mod.client.guis.GuiRoomgen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = Tardis.MODID)
public class InteractionRoomgen implements IScrew {
	private int roomsMade = 0;

	@Override
	public EnumActionResult performAction(World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) return EnumActionResult.FAIL;
		
		if (!player.isSneaking()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiRoomgen());
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public EnumActionResult blockInteraction(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (world.isRemote) return EnumActionResult.FAIL;
		
		Block block = state.getBlock();

		if (!player.isSneaking() && block == TBlocks.epanel_room) {
			PlayerHelper.sendMessage(player, "Room Genaration not yet avaliable", true);
			
			roomsMade++;
			if (world.isRemote) {
				if (roomsMade == 1) {
						Minecraft.getMinecraft().player.sendChatMessage("/advancement grant @s only tardis:make_1_room");
				}
				if (roomsMade == 100) {
						Minecraft.getMinecraft().player.sendChatMessage("/advancement grant @s only tardis:make_100_room");
				}
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public boolean entityInteraction(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		return false;
	}
	private void markUpdate(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, state, 10);
		world.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	@Override
	public String getName() {
		return "screw.roomgen";
	}
	
	@Override
	public int getCoolDownAmount() {
		return 0;
	}
	
	@Override
	public boolean causesCoolDown() {
		return false;
	}
	
	@Override
	public String getInfo() {
		return "screwdriver.info.roomgen";
	}
	
	
}
