package net.tardis.mod.common.screwdriver;

import net.tardis.mod.Tardis;

import java.util.ArrayList;
import java.util.List;

public class ScrewdriverHandler {

	public static List<IScrew> MODES = new ArrayList<>();

	public static void register(IScrew screw) {
		MODES.add(screw);
	}

	public static void init() {
		register(new InteractionImhelping());
		register(new InteractionGeneral());
		register(new InteractionEntity());
		register(new InteractionSignal());
		register(new InteractionRoomgen());
		register(new InteractionLockdown());
		register(new InteractionEpanelChange());
	}
}
