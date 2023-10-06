package ouroya.tint_tweak;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.biome.ColorResolver;
import net.minecraft.world.biome.Biome;

public class WaterColors {
    private static int[] colorMap = new int[65536];
    public static final ColorResolver GRASS_COLOR = Biome::getGrassColorAt;

    public static void setColorMap(int[] map) {
        colorMap = map;
    }

    public static int getColor(double temperature, double humidity) {
        int j = (int) ((1.0 - (humidity *= temperature)) * 255.0);
        int i = (int) ((1.0 - temperature) * 255.0);
        int k = j << 8 | i;
        if (k >= colorMap.length) {
            return -1;
        }
        return colorMap[k];
    }

    public static int getWaterColor(BlockRenderView view, BlockPos pos) {
        return BiomeColors.getGrassColor(view, pos);
    }

}
