package net.daporkchop.bettermaps;

public class Block {
    public final int blockId;
    public final int blockMeta;
    public final MapColor color;
    public final String registryName;

    GLASS(20,-1,MapColor.TRANSPARENT, "minecraft:glass"),

    STAINED_GLASS(95,-1,MapColor.TRANSPARENT, "minecraft:stained_glass"),

    GLASS_PANE(102,-1,MapColor.TRANSPARENT, "minecraft:glass_pane"),

    STAINED_GLASS_PANE(160,-1,MapColor.TRANSPARENT, "minecraft:stained_glass_pane"),

    FLOWER_POT(140,-1,MapColor.TRANSPARENT, "minecraft:flower_pot"),

    LADDER(65,-1,MapColor.TRANSPARENT, "minecraft:ladder"),

    STONE_BUTTON(77,-1,MapColor.TRANSPARENT, "minecraft:stone_button"),

    WOODEN_BUTTON(143,-1,MapColor.TRANSPARENT, "minecraft:wooden_button"),

    LEVER(69,-1,MapColor.TRANSPARENT, "minecraft:lever"),

    REPEATER_OFF(93,-1,MapColor.TRANSPARENT, "minecraft:unpowered_repeater"),

    REPEATER_ON(94,-1,MapColor.TRANSPARENT, "minecraft:powered_repeater"),

    COMPARATOR_OFF(149,-1,MapColor.TRANSPARENT, "minecraft:unpowered_comparator"),

    COMPARATOR_ON(150,-1,MapColor.TRANSPARENT, "minecraft:powered_comparator"),

    REDSTONE(55,-1,MapColor.TRANSPARENT, "minecraft:redstone_wire"),

    TORCH(50,-1,MapColor.TRANSPARENT, "minecraft:torch"),

    REDSTONE_TORCH_OFF(75,-1,MapColor.TRANSPARENT, "minecraft:unlit_redstone_torch"),

    REDSTONE_TORCH_ON(76,-1,MapColor.TRANSPARENT, "minecraft:redstone_torch"),

    REDSTONE_LAMP_OFF(123,-1,MapColor.TRANSPARENT, "minecraft:redstone_lamp"),

    REDSTONE_LAMP_ON(124,-1,MapColor.TRANSPARENT, "minecraft:lit_redstone_lamp"),

    RAIL(66,-1,MapColor.TRANSPARENT, "minecraft:grail"),

    POWERED_RAIL(27,-1,MapColor.TRANSPARENT, "minecraft:golden_rail"),

    DETECTOR_RAIL(28,-1,MapColor.TRANSPARENT, "minecraft:detector_rail"),

    ACTIVATOR_RAIL(157,-1,MapColor.TRANSPARENT, "minecraft:activator_rail"),

    TRIPWIRE(132,-1,MapColor.TRANSPARENT, "minecraft:tripwire_hook"), //no, that's not a typo

    TRIPWIRE_HOOK(131,-1,MapColor.TRANSPARENT, "minecraft:tripwire_hook"),

    CAKE(92,-1,MapColor.TRANSPARENT, "minecraft:cake"),

    SKULL(144,-1,MapColor.TRANSPARENT, "minecraft:skull"),

    NETHER_PORTAL(90,-1,MapColor.TRANSPARENT, "minecraft:portal");

    Block(int id, int meta, MapColor color, String registryName) {
        this.blockId = id;
        this.blockMeta = meta;
        this.color = color;
        this.registryName = registryName;
    }
}
