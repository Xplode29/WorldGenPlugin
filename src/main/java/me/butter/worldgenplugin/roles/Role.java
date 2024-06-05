package me.butter.worldgenplugin.roles;

import me.butter.worldgenplugin.roles.demons.Rui;

public enum Role {
    RUI(new Rui());

    private final AbstractRole role;

    Role(AbstractRole role) {
        this.role = role;
    }

    public AbstractRole getRole() {
        return role;
    }
}
