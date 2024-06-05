package me.butter.worldgenplugin.roles;

public enum Camp {
    SLAYER("uhc.roles.slayer"),
    DEMON("uhc.roles.demon"),
    SOLO("uhc.roles.solitaire");

    private final String key;

    Camp(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}