package net.tardis.mod;

import java.io.File;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import net.tardis.mod.api.disguise.DisguiseRegistry;
import net.tardis.mod.client.models.exteriors.TileEntityDoorTT;
import net.tardis.mod.common.ars.ConsoleRoom;
import net.tardis.mod.common.ars.Room;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.commands.TardisCommand;
import net.tardis.mod.common.dimensions.TDimensions;
import net.tardis.mod.common.dimensions.TDimensions.BiomeReg;
import net.tardis.mod.common.entities.EntityChair;
import net.tardis.mod.common.entities.EntityCompanion;
import net.tardis.mod.common.entities.EntityCorridor;
import net.tardis.mod.common.entities.EntityCybermanInvasion;
import net.tardis.mod.common.entities.EntityDalek;
import net.tardis.mod.common.entities.EntityDalekSkaro;
import net.tardis.mod.common.entities.EntityDefabric;
import net.tardis.mod.common.entities.EntityLaserRay;
import net.tardis.mod.common.entities.EntityTardis;
import net.tardis.mod.common.entities.brak.EntityDoorsBrakSecondary;
import net.tardis.mod.common.entities.controls.ControlDimChange;
import net.tardis.mod.common.entities.controls.ControlDirection;
import net.tardis.mod.common.entities.controls.ControlDoor;
import net.tardis.mod.common.entities.controls.ControlDoorSwitch;
import net.tardis.mod.common.entities.controls.ControlFastReturn;
import net.tardis.mod.common.entities.controls.ControlFuel;
import net.tardis.mod.common.entities.controls.ControlLandType;
import net.tardis.mod.common.entities.controls.ControlLaunch;
import net.tardis.mod.common.entities.controls.ControlMag;
import net.tardis.mod.common.entities.controls.ControlMonitor;
import net.tardis.mod.common.entities.controls.ControlPhone;
import net.tardis.mod.common.entities.controls.ControlRandom;
import net.tardis.mod.common.entities.controls.ControlStabilizers;
import net.tardis.mod.common.entities.controls.ControlTelepathicCircuts;
import net.tardis.mod.common.entities.controls.ControlWaypoint;
import net.tardis.mod.common.entities.controls.ControlX;
import net.tardis.mod.common.entities.controls.ControlY;
import net.tardis.mod.common.entities.controls.ControlZ;
import net.tardis.mod.common.entities.hellbent.EntityHellbentCorridor;
import net.tardis.mod.common.entities.hellbent.EntityHellbentDoor;
import net.tardis.mod.common.entities.vehicles.EntityBessie;
import net.tardis.mod.common.items.ItemSonic;
import net.tardis.mod.common.items.TItems;
import net.tardis.mod.common.protocols.ProtocolCCircuit;
import net.tardis.mod.common.protocols.ProtocolChangeInterior;
import net.tardis.mod.common.protocols.ProtocolConsole;
import net.tardis.mod.common.protocols.ProtocolEmerEscape;
import net.tardis.mod.common.protocols.ProtocolFindDimDRfit;
import net.tardis.mod.common.protocols.ProtocolForcefield;
import net.tardis.mod.common.protocols.ProtocolStealth;
import net.tardis.mod.common.protocols.ProtocolSystemReadout;
import net.tardis.mod.common.protocols.ProtocolToggleHum;
import net.tardis.mod.common.protocols.ProtocolWaypoints;
import net.tardis.mod.common.protocols.TardisProtocol;
import net.tardis.mod.common.recipes.RepairRecipes;
import net.tardis.mod.common.screwdriver.ScrewdriverHandler;
import net.tardis.mod.common.serializers.TDataSerializers;
import net.tardis.mod.common.strings.TStrings;
import net.tardis.mod.common.systems.SystemAntenna;
import net.tardis.mod.common.systems.SystemCCircuit;
import net.tardis.mod.common.systems.SystemDimension;
import net.tardis.mod.common.systems.SystemFlight;
import net.tardis.mod.common.systems.SystemFluidLinks;
import net.tardis.mod.common.systems.SystemStabilizers;
import net.tardis.mod.common.systems.SystemTemporalGrace;
import net.tardis.mod.common.systems.SystemThermo;
import net.tardis.mod.common.systems.TardisSystems;
import net.tardis.mod.common.tileentity.TileEntityAlembic;
import net.tardis.mod.common.tileentity.TileEntityAlembic.AlembicRecipe;
import net.tardis.mod.common.tileentity.TileEntityComponentRepair;
import net.tardis.mod.common.tileentity.TileEntityDoor;
import net.tardis.mod.common.tileentity.TileEntityEPanelItem;
import net.tardis.mod.common.tileentity.TileEntityEPanelRoom;
import net.tardis.mod.common.tileentity.TileEntityEPanelLight;
import net.tardis.mod.common.tileentity.TileEntityEgg;
import net.tardis.mod.common.tileentity.TileEntityFoodMachine;
import net.tardis.mod.common.tileentity.TileEntityHellbentLight;
import net.tardis.mod.common.tileentity.TileEntityLight;
import net.tardis.mod.common.tileentity.TileEntityInvislight;
import net.tardis.mod.common.tileentity.TileEntityMultiblock;
import net.tardis.mod.common.tileentity.TileEntityMultiblockMaster;
import net.tardis.mod.common.tileentity.TileEntitySonicWorkbench;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.common.tileentity.TileEntityTardisCoral;
import net.tardis.mod.common.tileentity.TileEntityTractorBeam;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis01;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis02;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis03;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis04;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis05;
import net.tardis.mod.common.tileentity.decoration.TileEntityAmSphere;
import net.tardis.mod.common.tileentity.decoration.TileEntityChair;
import net.tardis.mod.common.tileentity.decoration.TileEntityHelbentRoof;
import net.tardis.mod.common.tileentity.decoration.TileEntityHellbentMonitor;
import net.tardis.mod.common.tileentity.decoration.TileEntityHellbentPole;
import net.tardis.mod.common.tileentity.decoration.TileEntityToyotaSpin;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoor01;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoor03;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoor04;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoor05;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoorCC;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoorClock;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoorWardrobe;
import net.tardis.mod.common.tileentity.exteriors.TileEntityDoorWood;
import net.tardis.mod.common.world.TardisLoadingCallback;
import net.tardis.mod.common.world.WorldGenTardis;
import net.tardis.mod.config.TardisConfig;
import net.tardis.mod.handlers.GuiHandlerTardis;
import net.tardis.mod.network.NetworkHandler;
import net.tardis.mod.proxy.ServerProxy;
import net.tardis.mod.util.common.helpers.EntityHelper;
import net.tardis.mod.util.common.helpers.FileHelper;


@Mod(modid = Tardis.MODID, name = Tardis.NAME, dependencies = Tardis.DEP, version = "0.1.7", updateJSON = "https://raw.githubusercontent.com/josh-65/Tardis-mod-1.12.2-continued/master/update.json")
public class Tardis {

	public static final String MODID = "tardis";
	public static final String NAME = "Tardis Mod";
	public static final String DEP = "required-after:forge@[14.23.2.2638,)";
	public static final String VERSION = "";
	public static final boolean updateChangesConfig = false;
	private static File configDir = new File(".");

	@Instance(MODID)
	public static Tardis instance;

	@SidedProxy(clientSide = "net.tardis.mod.proxy.ClientProxy", serverSide = "net.tardis.mod.proxy.ServerProxy")
	public static ServerProxy proxy;

	public static void registerTileEntity(final Class<? extends TileEntity> clazz, final String name) {
		GameRegistry.registerTileEntity(clazz, new ResourceLocation(Tardis.MODID, name));
	}

	public static boolean getIsDev() {
		return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit();
		TItems.init();
		TBlocks.register();
		BiomeReg.init();
		EntityHelper.makeGoodBiomes();
		EntityHelper.registerStatic(ControlLaunch.class, "launch_lever");
		EntityHelper.registerStatic(ControlX.class, "x_valve");
		EntityHelper.registerStatic(ControlY.class, "y_valve");
		EntityHelper.registerStatic(ControlZ.class, "z_valve");
		EntityHelper.registerStatic(ControlDimChange.class, "dim_change");
		EntityHelper.registerStatic(ControlRandom.class, "rand_control");
		EntityHelper.registerStatic(ControlDoor.class, "tardis_door");
		EntityHelper.registerStatic(ControlFuel.class, "fuel");
		EntityHelper.registerStatic(ControlLandType.class, "land_type");
		EntityHelper.registerStatic(ControlDirection.class, "direction_control");
		EntityHelper.registerStatic(ControlFastReturn.class, "tardis_fast_return");
		EntityHelper.registerStatic(ControlTelepathicCircuts.class, "telepathic_circuts");
		EntityHelper.registerStatic(ControlDoorSwitch.class, "tardis_door_control");
		EntityHelper.registerStatic(ControlPhone.class, "tardis_phone");
		EntityHelper.registerStatic(ControlMag.class, "tardis_magnitude");
		EntityHelper.registerStatic(ControlStabilizers.class, "stabilizers");
		EntityHelper.registerStatic(ControlMonitor.class, "monitor");
		EntityHelper.registerStatic(ControlWaypoint.class, "waypoint_select");
		EntityHelper.registerProjectiles(EntityLaserRay.class, "cyber_ray");
		EntityHelper.registerNoSpawn(EntityHellbentCorridor.class, "hellbent_corridor");
		EntityHelper.registerNoSpawn(EntityHellbentDoor.class, "hellbent_door");
		EntityHelper.registerNoSpawn(EntityBessie.class, "bessie");
		EntityHelper.registerNoSpawn(EntityCompanion.class, "companion");
		EntityHelper.registerNoSpawn(EntityDalekSkaro.class, "dalek_scaro");
		EntityHelper.registerStatic(EntityChair.class, "chair");
		EntityHelper.registerStatic(EntityDoorsBrakSecondary.class, "doors_brak_second");
		EntityHelper.registerNoSpawn(EntityTardis.class, "tardis");
		EntityHelper.registerProjectiles(EntityDefabric.class, "defabric");

		registerTileEntity(TileEntityTardis.class, "TileEntityTardis");
		registerTileEntity(TileEntityDoor.class, "TileEntityDoor");
		registerTileEntity(TileEntityAlembic.class, "TileEntityAlembic");
		registerTileEntity(TileEntityFoodMachine.class, "TileEntityFoodMachine");
		registerTileEntity(TileEntityEPanelItem.class, "TileEntityEPanelItem");
		registerTileEntity(TileEntityEPanelRoom.class, "TileEntityEPanelRoom");
		registerTileEntity(TileEntityEPanelLight.class, "TileEntityEPanelLight");
		registerTileEntity(TileEntityTardisCoral.class, "TileEntityTardisCoral");
		registerTileEntity(TileEntityLight.class, "TileEntityLight");
		registerTileEntity(TileEntityHellbentLight.class, "TileEntityHellbentLight");
		registerTileEntity(TileEntityHellbentMonitor.class, "TileEntityHellbentMonitor");
		registerTileEntity(TileEntityHellbentPole.class, "TileEntityHellbentPole");
		registerTileEntity(TileEntityHelbentRoof.class, "TileEntityHelbentRoof");
		registerTileEntity(TileEntityComponentRepair.class, "TileEntityComponentRepair");
		registerTileEntity(TileEntityChair.class, "chair");
		registerTileEntity(TileEntityAmSphere.class, "am_sphere");
		registerTileEntity(TileEntityToyotaSpin.class, "toyota_spinnything");

		registerTileEntity(TileEntityMultiblockMaster.class, "multi_master");
		registerTileEntity(TileEntityMultiblock.class, "multi");

		//Exteriors
		registerTileEntity(TileEntityDoor01.class, "TileEntityDoor01");
		registerTileEntity(TileEntityDoor03.class, "TileEntityDoor03");
		registerTileEntity(TileEntityDoor04.class, "TileEntityDoor04");
		registerTileEntity(TileEntityDoor05.class, "TileEntityDoor05");
		registerTileEntity(TileEntityDoorCC.class, "TileEntityDoorCC");
		registerTileEntity(TileEntityDoorClock.class, "TileEntityDoorClock");
		registerTileEntity(TileEntityDoorTT.class, "TileEntityDoorTT");
		registerTileEntity(TileEntityDoorWood.class, "TileEntityDoorWood");
		registerTileEntity(TileEntityDoorWardrobe.class, "exterior_wardrobe");

		//Interiors
		registerTileEntity(TileEntityTardis01.class, "TileEntityTardis01");
		registerTileEntity(TileEntityTardis02.class, "TileEntityTardis02");
		registerTileEntity(TileEntityTardis03.class, "console_3");
		registerTileEntity(TileEntityTardis04.class, "console_4");
		registerTileEntity(TileEntityTardis05.class, "console_5");
		
		//Machines
		registerTileEntity(TileEntitySonicWorkbench.class, "sonic_workbench");
		registerTileEntity(TileEntityEgg.class, "ars_egg");
		registerTileEntity(TileEntityTractorBeam.class, "tractor_beam");

		registerTileEntity(TileEntityInvislight.class, "Invislight");

		NetworkHandler.init();
		ScrewdriverHandler.init();
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new TardisLoadingCallback());

		TardisProtocol.register(new ProtocolEmerEscape());
		TardisProtocol.register(new ProtocolCCircuit());
		TardisProtocol.register(new ProtocolSystemReadout());
		TardisProtocol.register(new ProtocolConsole());
		if (Loader.isModLoaded(TStrings.ModIds.DIM_DOORS))
			TardisProtocol.register(new ProtocolFindDimDRfit());
		TardisProtocol.register(new ProtocolWaypoints());
		TardisProtocol.register(new ProtocolToggleHum());
		TardisProtocol.register(new ProtocolChangeInterior());
		TardisProtocol.register(new ProtocolStealth());
		TardisProtocol.register(new ProtocolForcefield());

		// Register All Mobs Here.
		EntityHelper.registerMobEgg(EntityCybermanInvasion.class, "invasion_cyberman", TardisConfig.USE_ENTITIES.cybermanSpawnChance, 5, 4);
		EntityHelper.registerNoSpawnEgg(EntityDalek.class, "dalek", 0x8a7a28, 0x553420);

		proxy.preInit();

		TardisSystems.register("flight", SystemFlight.class);
		TardisSystems.register("dimensional", SystemDimension.class);
		TardisSystems.register("fluid_links", SystemFluidLinks.class);
		TardisSystems.register("antenna", SystemAntenna.class);
		TardisSystems.register("chameleon", SystemCCircuit.class);
		TardisSystems.register("temporal_grace", SystemTemporalGrace.class);
		TardisSystems.register("stabilizers", SystemStabilizers.class);
		TardisSystems.register("thermo", SystemThermo.class);

		GameRegistry.registerWorldGenerator(new WorldGenTardis(), 1);

		DisguiseRegistry.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerTardis());

		RepairRecipes.registerRecipe(TItems.fluid_link, TItems.mercuryBottle);
		RepairRecipes.registerRecipe(TItems.demat_circut, Items.ENDER_PEARL);
		RepairRecipes.registerRecipe(TItems.antenna, TItems.circuts);
		RepairRecipes.registerRecipe(TItems.stabilizers, TItems.circuts);
		RepairRecipes.registerRecipe(TItems.time_vector_generator, Items.ENDER_PEARL);
		RepairRecipes.registerRecipe(TItems.chameleon_circuit, TItems.circuts);
		RepairRecipes.registerRecipe(TItems.temporal_grace_circuits, Items.SHIELD);
		RepairRecipes.registerRecipe(TItems.thermo, Items.IRON_INGOT);
		
		//Tardis interiors
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_0.png", "tardis/interior_0", new BlockPos(10, 2, 9));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_1.png", "tardis/interior_1", new BlockPos(9, 2, 9));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_greymatter.png", "tardis/interior_greymatter", new BlockPos(17 ,2 ,17));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_study.png", "tardis/interior_study", new BlockPos(14,2,8));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_lodge.png", "tardis/interior_lodge", new BlockPos(17, 2, 17));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_aquatic.png", "tardis/interior_aquatic", new BlockPos(19,2,18));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_industrial.png", "tardis/interior_industrial", new BlockPos(11, 2, 18));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_helian.png", "tardis/interior_helian", new BlockPos(18,1,18));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_magmatic.png", "tardis/interior_magmatic", new BlockPos(21, 3, 16));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_explorer.png", "tardis/interior_explorer", new BlockPos(23, 3, 20));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_iceshrine.png", "tardis/interior_iceshrine", new BlockPos(11, 2, 11));
		ConsoleRoom.registerConsoleRoom("textures/gui/previews/interior_builder.png", "tardis/interior_builder", new BlockPos(9, 1, 9));
		
		//Tardis corridors
		Room.registerRoom("textures/gui/previews/corridor_stright_short.png", "tardis/corridor_straight_short", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/corridor_stright_long.png", "tardis/corridor_straight_long", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/corridor_+way.png", "tardis/corridor_+way", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/corridor_Tway.png", "tardis/corridor_Tway", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/corridor_left.png", "tardis/corridor_left", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/corridor_right.png", "tardis/corridor_right", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/corridor_lift.png", "tardis/corridor_lift", new BlockPos(9, 1, 9));

		//Tardis rooms
		Room.registerRoom("textures/gui/previews/room_builder.png", "tardis/room_builder", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/room_lab.png", "tardis/room_builder", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/room_farm.png", "tardis/room_builder", new BlockPos(9, 1, 9));
		Room.registerRoom("textures/gui/previews/room_bedroom.png", "tardis/room_builder", new BlockPos(9, 1, 9));


		TileEntitySonicWorkbench.RECIPES.put(TItems.key, new Item[]{TItems.key_01});
		final Item[] sonics = new Item[ItemSonic.SONICS.size()];
		for(int sonicID = 0; sonicID < sonics.length; ++sonicID) {
			sonics[sonicID] = ItemSonic.SONICS.get(sonicID);
		}
		TileEntitySonicWorkbench.RECIPES.put(TItems.sonic_screwdriver, sonics);
		TileEntitySonicWorkbench.RECIPES.put(Item.getItemFromBlock(Blocks.WOOL), new Item[]{TItems.void_specs});
		
		this.configDir = event.getModConfigurationDirectory();
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		proxy.init();
		// Ore Dictionary
		OreDictionary.registerOre("oreUranium", TItems.power_cell);
		OreDictionary.registerOre("dustCinnabar", TItems.crushedCinnabar);
		OreDictionary.registerOre("oreCinnabar", TBlocks.cinnabar_ore);

		//Permissions
		PermissionAPI.registerNode(TStrings.Permissions.TP_IN_TARDIS, DefaultPermissionLevel.OP, "Allows players to teleport themself in their TARDIS");
		PermissionAPI.registerNode(TStrings.Permissions.TP_IN_TARDIS_OTHER, DefaultPermissionLevel.OP, "Allows players to teleport themself in the TARDIS of a specified player");
		PermissionAPI.registerNode(TStrings.Permissions.REMOVE_TARDIS, DefaultPermissionLevel.OP, "Allows players to delete a TARDIS");
		PermissionAPI.registerNode(TStrings.Permissions.RESTORE_TARDIS, DefaultPermissionLevel.OP, "Allows players to restore their TARDIS Systems");
		PermissionAPI.registerNode(TStrings.Permissions.GROW, DefaultPermissionLevel.OP, "Allows players to grow their TARDIS Coral faster");
		PermissionAPI.registerNode(TStrings.Permissions.TP_OUT_TARDIS, DefaultPermissionLevel.OP, "Allows players to teleport themself out of their TARDIS");

		//This should be in pre-init, but it seems some mods have a weird obsession with claiming already taken IDs
		TDimensions.register();
		
		DataSerializers.registerSerializer(TDataSerializers.VEC3D);
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit();
		for (final ItemStack cinnabar : OreDictionary.getOres("dustCinnabar")) {
			AlembicRecipe.registerRecipe(cinnabar.getItem(), TItems.mercuryBottle);
		}
		FileHelper.readOrWriteARS(configDir);

	}

	@EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {
		event.registerServerCommand(new TardisCommand());
	}
}
