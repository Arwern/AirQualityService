package dev.jozwik.airquality.controller;

import dev.jozwik.airquality.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
@RequestMapping("api/v1/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingService;

    @PostMapping(params = {"setting", "value"})
    public ResponseEntity<Object> setSetting(@RequestParam final String setting, @RequestParam final String value) {
        return ResponseEntity.status(this.settingService.setSetting(setting, value) ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(params = {"setting"})
    public ResponseEntity<Object> getSetting(@RequestParam final String setting) {
        final Object value = this.settingService.getSetting(setting);
        if(Objects.isNull(value)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(value);
    }

}
