package net.daporkchop.bettermaps;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Generator {
    public static final Set<Block> blocks = new LinkedHashSet<Block>();
    public static final Map<Integer, MapColor> colors = new Hashtable<Integer, MapColor>();

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
}
