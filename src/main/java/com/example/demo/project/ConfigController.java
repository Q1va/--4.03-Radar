package com.example.demo.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/config")
    public String getConfigForm(Model model) {
        model.addAttribute("config", configService.getConfig());
        return "Config";
    }

    @PostMapping("/config")
    public String updateConfig(@ModelAttribute ConfigDTO config) {
        configService.updateConfig(config);
        return "redirect:/config";
    }
}
