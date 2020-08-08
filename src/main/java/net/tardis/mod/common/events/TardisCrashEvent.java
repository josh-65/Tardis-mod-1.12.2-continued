package net.tardis.mod.common.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.tardis.mod.common.tileentity.TileEntityTardis;

/**
 * Event triggered when a TARDIS Crash
 */
public class TardisCrashEvent extends Event {
    private TileEntityTardis tardis;
    private BlockPos crashPos;
    private int crashDimension;

    /**
     * @param tardis
     */
    public TardisCrashEvent(TileEntityTardis tardis, BlockPos crashPos, int crashDimension) {
        this.tardis = tardis;
        this.crashPos = crashPos;
        this.crashDimension = crashDimension;
        Minecraft.getMinecraft().player.sendChatMessage("/advancement grant @s only tardis:crash_tardis");
    }

    /**
     * @return TileEntityTardis
     */
    public TileEntityTardis getTardis() {
        return tardis;
    }

    /**
     * @return Crash location of the TARDIS
     */
    public BlockPos getCrashPos() {
        return crashPos;
    }

    public int getCrashDimension() {
        return crashDimension;
    }
}
