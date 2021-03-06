package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.common.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.common.entites.*;
import com.cgessinger.creaturesandbeasts.common.init.ModBlockRegistry;
import com.cgessinger.creaturesandbeasts.common.init.ModEntityTypes;
import com.cgessinger.creaturesandbeasts.common.init.ModItems;
import com.cgessinger.creaturesandbeasts.common.init.ModSoundEventTypes;
import com.cgessinger.creaturesandbeasts.common.world.gen.ModEntitySpawns;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(CreaturesAndBeasts.MOD_ID)
public class CreaturesAndBeasts
{
	public static final String MOD_ID = "cnb";

	public CreaturesAndBeasts ()
	{
		ModLoadingContext.get().registerConfig(Type.COMMON, CNBConfig.COMMON_SPEC);

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register the setup method for modloading
		eventBus.addListener(this::setup);
		// Register the doClientStuff method for modloading
		eventBus.addListener(this::doClientStuff);

		ModSoundEventTypes.SOUND_EVENTS.register(eventBus);
		ModEntityTypes.ENTITY_TYPES.register(eventBus);
		ModBlockRegistry.BLOCKS.register(eventBus);
		ModItems.ITEMS.register(eventBus);
		MinecraftForge.EVENT_BUS.register(this);

		GeckoLib.initialize();
	}

	private void setup (final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(ModEntityTypes.LITTLE_GREBE.get(), GrebeEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.CINDERSHELL.get(), CindershellEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.LIZARD.get(), LizardEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.LILYTAD.get(), LilytadEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.FRIENDLY_SPORELING.get(), AbstractSporelingEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.HOSTILE_SPORELING.get(), HostileSporelingEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.NEUTRAL_SPORELING.get(), NeutralSporelingEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.YETI.get(), YetiEntity.setCustomAttributes().create());
		});
		/*
		 * This registers the spawn placement settings we config for any mob that needs
		 * it.
		 */
		ModEntitySpawns.entitySpawnPlacementRegistry();
	}

	private void doClientStuff (final FMLClientSetupEvent event)
	{
		RenderTypeLookup.setRenderLayer(ModBlockRegistry.POTTED_LILYTAD_FLOWER.get(), RenderType.getCutout());
	}

	public static final ItemGroup TAB = new ItemGroup("cnb_tab")
	{
		@Override
		public ItemStack createIcon ()
		{
			return new ItemStack(ModItems.GREBE_SPAWN_EGG.get());
		}
	};
}
