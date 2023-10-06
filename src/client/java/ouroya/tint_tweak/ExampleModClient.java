package ouroya.tint_tweak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.world.biome.ColorResolver;
import ouroya.tint_tweak.WaterColors;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ExampleModClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("tint-tweak");
	public static final ColorResolver WATER_COLOR = (biome, x, z) -> biome.getWaterColor();

	private static BlockColorProvider getFoliageColorProvider(int defaultColor) {
		return (state, view, pos, tintIndex) -> {
			if (view == null || pos == null) {
				return defaultColor;
			}
			return BiomeColors.getFoliageColor(view, pos);
		};
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info("Tweaking Tints");
		ColorProviderRegistry.BLOCK.register(
				getFoliageColorProvider(FoliageColors.getSpruceColor()),
				Blocks.SPRUCE_LEAVES);
		ColorProviderRegistry.BLOCK.register(
				getFoliageColorProvider(FoliageColors.getBirchColor()),
				Blocks.BIRCH_LEAVES);
		ColorProviderRegistry.BLOCK.register(
				getFoliageColorProvider(FoliageColors.getDefaultColor()),
				Blocks.CHERRY_LEAVES, Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
			if (world == null || pos == null) {
				return -1;
			}
			return BiomeColors.getWaterColor(world, pos);
		}, Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.WATER_CAULDRON);
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.WATER_CAULDRON, RenderLayer.getTranslucent());
	}
}
