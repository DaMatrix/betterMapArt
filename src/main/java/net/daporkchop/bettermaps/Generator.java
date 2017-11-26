package net.daporkchop.bettermaps;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;

public class Generator {
    public static final Set<Block> blocks = new LinkedHashSet<Block>();

    public static final void readBlocksFromConfig() {
        try {
            blocks.clear();
            String config = IOUtils.toString(Generator.class.getResourceAsStream("/colors.json"), Charset.defaultCharset());
            System.out.println(config);
        } catch (IOException e) {
            throw new IllegalStateException(e); //this shouldn't ever be possible
        }
    }
}
