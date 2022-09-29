package com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.cool_down

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.repository.SettingsRepository

class GetButtonCoolDownUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(type: ButtonActionType): Long? {
        return settingsRepository.getButtonClickTime(type)
    }
}