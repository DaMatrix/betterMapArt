package net.daporkchop.bettermaps.element;

public class Block {
    public final int blockId;
    public final int blockMeta;
    public final MapColor color;
    public final String registryName;
    public final String displayName;

    public Block(int id, int meta, MapColor color, String registryName, String displayName) {
        this.blockId = id;
        this.blockMeta = meta;
        this.color = color;
        this.registryName = registryName;
        this.displayName = displayName;
    }
}
