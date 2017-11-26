package net.daporkchop.bettermaps;

import net.daporkchop.bettermaps.element.Block;
import net.daporkchop.bettermaps.element.MapColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Generator.readBlocksFromConfig();
        long a = System.currentTimeMillis();
        Block[][] blocks = Generator.gen2dMap(Generator.loadImage("/media/daporkchop/TooMuchStuff/Misc/DaPorkchop_128x128.png"), test_genMappings());
        System.out.println("Generation took " + (System.currentTimeMillis() - a) + "ms");
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < blocks.length; x++) {
            Block[] listX = blocks[x];
            for (int y = 0; y < listX.length; y++) {
                Block block = listX[y];
                MapColor color = block.color;
                image.setRGB(x, y, new Color(color.r, color.g, color.b).getRGB());
            }
        }
        ImageIO.write(image, "png", new File(".", "xd.png"));
    }

    private static Map<MapColor, Block> test_genMappings() {
        Map<MapColor, Block> map = new HashMap<MapColor, Block>();
        for (Block block : Generator.blocks) {
            map.put(block.color, block);
        }
        return map;
    }
}
