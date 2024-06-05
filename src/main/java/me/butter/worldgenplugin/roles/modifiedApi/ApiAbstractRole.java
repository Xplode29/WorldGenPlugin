package me.butter.worldgenplugin.roles.modifiedApi;

public class ApiAbstractRole {
    private UHCPlayer uhcPlayer;

    private final List<Power> powers;

    protected AbstractRole(Power... powers) {
        this.powers = Lists.newArrayList(Arrays.asList(powers));
    }

    public boolean isMoon() {
        return false;
    }
    public boolean isPilars() {
        return false;
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }

    //Quand ca devient la nuit
    @Override
    public void onDay() {

    }

    @Override
    public void onNight() {

    }

    @Override
    public void onDistribution(Player player) {

    }

    @Override
    public void getPlayerNameWhiteRole() {

    }

    @Override
    public void onDeath(Player death, Player killer) {

    }

    @Override
    public List<Power> getPowers() {
        return powers;
    }

    @Override
    public UHCPlayer getUHCPlayer() {
        return uhcPlayer;
    }

    @Override
    public void setUHCPlayer(UHCPlayer uhcPlayer) {
        this.uhcPlayer = uhcPlayer;
    }
}
