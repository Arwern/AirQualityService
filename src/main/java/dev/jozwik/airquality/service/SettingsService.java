package dev.jozwik.airquality.service;

import dev.jozwik.airquality.entity.SettingKey;
import dev.jozwik.airquality.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SettingsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

    @Autowired
    private SettingRepository settingRepository;
    public boolean setSetting(final String setting, final String value) {
        try {
            return this.settingRepository.setSetting(getSettingKey(setting), value);
        } catch(final Exception e) {
            LOGGER.error("Invalid setting key");
            return false;
        }
    }

    public String getSetting(final String setting) {
        try {
            return this.settingRepository.getSetting(getSettingKey(setting));
        } catch (final Exception e) {
            LOGGER.error("Invalid setting key");
            return null;
        }
    }

    private SettingKey getSettingKey(final String setting) {
        return SettingKey.valueOf(setting.toUpperCase());
    }
}
