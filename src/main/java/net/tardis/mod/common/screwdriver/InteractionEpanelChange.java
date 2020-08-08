package net.tardis.mod.common.screwdriver;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tardis.mod.common.dimensions.TDimensions;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.util.common.helpers.Helper;
import net.tardis.mod.util.common.helpers.TardisHelper;
import net.tardis.mod.util.common.helpers.PlayerHelper;

public class InteractionEpanelChange implements IScrew {

	@Override
	public EnumActionResult performAction(World world, EntityPlayer player, EnumHand hand) {
		//switch Epanel here
		return EnumActionResult.SUCCESS;
	}

	@Override
	public EnumActionResult blockInteraction(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return EnumActionResult.FAIL;
	}

	@Override
	public boolean entityInteraction(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		return false;
	}

	@Override
	public String getName() {
		return "screw.tardis.EpanelChange";
	}

	@Override
	public int getCoolDownAmount() {
		return 10;
	}

	@Override
	public boolean causesCoolDown() {
		return true;
	}
	
	@Override
	public String getInfo() {
		return "screwdriver.info.EpanelChange";
	}
	
}
