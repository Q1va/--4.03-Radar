package com.example.demo.project;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    private ConfigDTO config;


    public ConfigService() {
        config = new ConfigDTO();
        config.setMeasurementsPerRotation(360);
        config.setRotationSpeed(25);
        config.setTargetSpeed(1500);
    }


    public ConfigDTO getConfig() {
        return config;
    }

    // Обновить настройки конфигурации
    public void updateConfig(ConfigDTO newConfig) {
        config.setMeasurementsPerRotation(newConfig.getMeasurementsPerRotation());
        config.setRotationSpeed(newConfig.getRotationSpeed());
        config.setTargetSpeed(newConfig.getTargetSpeed());
    }
}

