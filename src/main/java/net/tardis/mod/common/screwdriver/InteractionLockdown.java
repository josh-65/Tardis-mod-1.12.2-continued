package net.tardis.mod.common.screwdriver;

import net.minecraft.client.Minecraft;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.tardis.mod.Tardis;
import net.tardis.mod.common.dimensions.TDimensions;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.common.systems.SystemStabilizers;
import net.tardis.mod.util.common.helpers.Helper;
import net.tardis.mod.util.common.helpers.TardisHelper;
import net.tardis.mod.util.common.helpers.PlayerHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.minecraft.init.Blocks.REDSTONE_LAMP;

@Mod.EventBusSubscriber(modid = Tardis.MODID)
public class InteractionLockdown implements IScrew {

	@Override
	public EnumActionResult performAction(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			TileEntityTardis e = new TileEntityTardis();
			if (!player.isSneaking()) {
				if (TardisHelper.hasTardis(player.getGameProfile().getId())) {
					TileEntityTardis tardis = Helper.getTardis(world.getMinecraftServer().getWorld(TDimensions.TARDIS_ID).getTileEntity(TardisHelper.getTardis(player.getGameProfile().getId())));

					tardis.getSystem(SystemStabilizers.class).setOn(true);
					tardis.setDesination(world.getSpawnPoint(), 0);
					tardis.getDoor().setOpen(false);
					tardis.startFlight();
					Minecraft.getMinecraft().player.sendChatMessage("/advancement grant @s only tardis:sonic_lockdown");
					return EnumActionResult.SUCCESS;
				}
				PlayerHelper.sendMessage(player, "screw.fail.noTardis", true);
				return EnumActionResult.FAIL;
			}
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public EnumActionResult blockInteraction(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
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
		return "screw.lockdown";
	}
	
	@Override
	public int getCoolDownAmount() {
		return 120;
	}
	
	@Override
	public boolean causesCoolDown() {
		return true;
	}
	
	@Override
	public String getInfo() {
		return "screwdriver.info.lockdown";
	}
	
	
}
