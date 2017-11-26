package net.daporkchop.bettermaps;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.daporkchop.bettermaps.element.Block;
import net.daporkchop.bettermaps.element.MapColor;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

public class Generator {
    public static final Set<Block> blocks = new LinkedHashSet<Block>();
    public static final Map<Integer, MapColor> colors = new Hashtable<Integer, MapColor>();

    /**
     * Reads block information from the config file
     *
     * @author DaPorkchop_
     */
    public static final void readBlocksFromConfig() {
        try {
            System.out.println("Loading block data...");
            blocks.clear();
            colors.clear();
            String config = IOUtils.toString(Generator.class.getResourceAsStream("/colors.json"), Charset.defaultCharset());
            JsonObject object = new JsonParser().parse(config).getAsJsonObject();

            {
                JsonObject colors = object.getAsJsonObject("colors");
                for (Map.Entry<String, JsonElement> entry : colors.entrySet()) {
                    JsonObject jColor = entry.getValue().getAsJsonObject();
                    int index = Integer.parseInt(entry.getKey());
                    int r = jColor.getAsJsonPrimitive("red").getAsInt();
                    int g = jColor.getAsJsonPrimitive("green").getAsInt();
                    int b = jColor.getAsJsonPrimitive("blue").getAsInt();
                    int a = jColor.getAsJsonPrimitive("alpha").getAsInt();
                    MapColor color = new MapColor(r, g, b, a, index);
                    Generator.colors.put(index, color);
                }
            }
            {
                JsonArray blocks = object.getAsJsonArray("blocks");
                for (JsonElement element : blocks) {
                    JsonObject jBlock = element.getAsJsonObject();
                    String registryName = jBlock.getAsJsonPrimitive("registryName").getAsString();
                    String localizedName = jBlock.getAsJsonPrimitive("localizedName").getAsString();
                    int id = jBlock.getAsJsonPrimitive("id").getAsInt();
                    int meta = jBlock.getAsJsonPrimitive("meta").getAsInt();
                    MapColor color = colors.get(Integer.parseInt(jBlock.getAsJsonPrimitive("color").getAsString()));
                    if (color == null) {
                        throw new IllegalStateException("Invalid map color: " + jBlock.getAsJsonPrimitive("color").getAsString());
                    }
                    Generator.blocks.add(new Block(id, meta, color, registryName, localizedName));
                }
            }
            System.out.println("Loaded!");
        } catch (IOException e) {
            throw new IllegalStateException(e); //this shouldn't ever be possible
        }

        return;
    }

    /**
     * Gets the closest color in the given set of colors to the given color
     *
     * @param color  the color to search for
     * @param colors the list of colors to search in
     * @return The closest color to the "color" argument
     * @author DaPorkchop_
     */
    public static MapColor getClosestColorToInput(Color color, Set<MapColor> colors) {
        if (color == null || colors == null) {
            throw new IllegalStateException("All arguments must be non-null!");
        }
        if (colors.size() == 0) {
            throw new IllegalArgumentException("Need to give at least one color!");
        }
        MapColor toReturn = null;
        double minValue = Double.MAX_VALUE;
        for (MapColor test : colors) {
            double dist = getColorDistance(color, new Color(test.r, test.g, test.b));
            if (dist < minValue) {
                minValue = dist;
                toReturn = test;
            }
        }

        return toReturn;
    }

    /**
     * Probably not needed
     *
     * @param disabledBlocks list of disabled blocks
     * @return a list of colors to use
     * @author DaPorkchop_
     */
    public static MapColor[] getPalette(Block[] disabledBlocks) {
        List<Block> allBlocks = new ArrayList<Block>();
        for (Block block : blocks) {
            allBlocks.add(block);
        }
        List<MapColor> allColors = new ArrayList<MapColor>();
        for (MapColor color : colors.values()) {
            allColors.add(color);
        }

        for (Block block : disabledBlocks) {
            allBlocks.remove(block);
            boolean noColor = true;
            for (Block remainingBlock : allBlocks) {
                if (remainingBlock.color == block.color) {
                    noColor = false;
                }
            }
            if (noColor) {
                allColors.remove(block.color);
            }
        }

        return allColors.toArray(new MapColor[allColors.size()]);
    }

    /**
     * Load an image path to a BufferedImage
     *
     * @param var0
     * @return
     */
    public static BufferedImage loadImage(String var0) {
        BufferedImage var1 = null;
        File var2 = new File(var0);

        try {
            var1 = ImageIO.read(var2);
        } catch (IOException var4) {
            System.out.println("Something went wrong:");
            var4.printStackTrace();
        }

        return var1;
    }

    /**
     * Gets the distance between two colors, for quick and easy similarity checks
     *
     * @param a color 1
     * @param b color 2
     * @return the distance, not sqrt because we don't need to and it's way faster without
     * @author DaPorkchop_
     */
    public static double getColorDistance(Color a, Color b) {
        int diffR = Math.abs(a.getRed() - b.getRed());
        int diffG = Math.abs(a.getGreen() - b.getGreen());
        int diffB = Math.abs(a.getBlue() - b.getBlue());
        return (diffR ^ 2) + (diffG ^ 2) + (diffB ^ 2);
    }

    /**
     * Generates a 2d map from the given BufferedImage
     *
     * @param image         the image to generate from
     * @param colorMappings the mappings of blocks to map colors to use
     * @return a 2d array of Blocks (as in Block[x][y])
     * @author DaPorkchop_
     */
    public Block[][] gen2dMap(BufferedImage image, Map<MapColor, Block> colorMappings) {
        if (image == null || colorMappings == null) {
            throw new IllegalStateException("All arguments must be non-null!");
        }
        if (image.getWidth() != 128 || image.getHeight() != 128) {
            throw new IllegalArgumentException("Image must be 128x128!");
        }
        if (colorMappings.size() == 0) {
            throw new IllegalArgumentException("Need to give at least one block->color mapping!");
        }

        Set<MapColor> palette = colorMappings.keySet();

        Block[][] blocksOut = new Block[128][128];
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                blocksOut[x][y] = colorMappings.get(getClosestColorToInput(new Color(image.getRGB(x, y)), palette));
            }
        }

        return blocksOut;
    }
}
