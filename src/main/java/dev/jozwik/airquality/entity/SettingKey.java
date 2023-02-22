package dev.jozwik.airquality.entity;

public enum SettingKey {
    LIMIT("20");

    private final String defaultValue;

    SettingKey(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
