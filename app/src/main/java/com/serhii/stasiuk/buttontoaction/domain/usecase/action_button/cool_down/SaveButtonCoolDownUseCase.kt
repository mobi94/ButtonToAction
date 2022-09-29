package com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.cool_down

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.repository.SettingsRepository

class SaveButtonCoolDownUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(type: ButtonActionType) {
        settingsRepository.saveButtonClickTime(type, System.currentTimeMillis())
    }
}