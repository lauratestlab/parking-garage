package com.example.parkinglot.service;

import com.example.parkinglot.entity.Setting;
import com.example.parkinglot.repo.SettingRepository;

import java.math.BigDecimal;

import static com.example.parkinglot.config.Constants.MEMBER_DISCOUNT_SETTING;

public class SettingService {
    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    BigDecimal getMemberDiscount() {
        Setting setting = settingRepository.findByKey(MEMBER_DISCOUNT_SETTING).orElseThrow(() -> new RuntimeException("Member discount setting not found"));
        return new BigDecimal(setting.getValue());
    }

    void updateMemberDiscount(BigDecimal discount) {
        settingRepository.save(new Setting(MEMBER_DISCOUNT_SETTING, discount.toString()));
    }
}
