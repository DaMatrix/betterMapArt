package net.daporkchop.bettermaps;

public class Block {
    public final int blockId;
    public final int blockMeta;
    public final MapColor color;
    public final String registryName;

    public Block(int id, int meta, MapColor color, String registryName) {
        this.blockId = id;
        this.blockMeta = meta;
        this.color = color;
        this.registryName = registryName;
    }
}
