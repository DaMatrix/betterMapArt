package net.daporkchop.bettermaps;

public class Config {
    public static final Config instance = new Config();
    public boolean dithering = true;
    public boolean force2d = true;
    public boolean greyscale = false;

    private Config() {
    }
}
