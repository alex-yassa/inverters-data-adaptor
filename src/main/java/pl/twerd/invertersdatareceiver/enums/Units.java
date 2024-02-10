package pl.twerd.invertersdatareceiver.enums;

public enum Units {
    VOLTAGE("V"),
    CURRENT("A"),
    POWER("W"),
    POWER_KILOWATT("kW"),
    ENERGY("Wh"),
    VAR("Var"),
    KILO_VAR("kVar"),
    UNKNOWN("");

    private final String unit;

    Units(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
