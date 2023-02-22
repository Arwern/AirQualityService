package dev.jozwik.airquality.repository;

import dev.jozwik.airquality.entity.SettingKey;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;


@Repository
public class SettingRepository {

    private static final Map<SettingKey, String> settings = new EnumMap<>(SettingKey.class);

    static {
        Arrays.stream(SettingKey.values()).forEach(key -> settings.put(key, key.getDefaultValue()));
    }

    public boolean setSetting(final SettingKey settingKey, final String value) {
        settings.put(settingKey, value);
        return true;
    }

    public String getSetting(final SettingKey settingKey) {
        return settings.get(settingKey);
    }
}
