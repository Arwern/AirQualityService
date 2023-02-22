package dev.jozwik.airquality.entity;

public class Setting {
    private SettingKey key;
    private Object value;

    public Setting() {
    }

    public Setting(final SettingKey key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public SettingKey getKey() {
        return key;
    }

    public void setKey(final SettingKey key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}
