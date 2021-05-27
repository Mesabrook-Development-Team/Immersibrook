package rz.mesabrook.wbtc.util;

import net.minecraft.util.IStringSerializable;

public enum EnumSimpleRotation implements IStringSerializable {
    NORTH(0, "north"),
    EAST(1, "east"),
    SOUTH(2, "south"),
    WEST(3, "west");

    private static final EnumSimpleRotation[] META_LOOKUP = new EnumSimpleRotation[values().length];
    private final int meta;
    private final String name;

    private EnumSimpleRotation(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getMetadata() {
        return this.meta;
    }

    public static EnumSimpleRotation byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    static {
        for (EnumSimpleRotation type: values()) {
            META_LOOKUP[type.getMetadata()] = type;
        }
    }
}