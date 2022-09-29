package com.serhii.stasiuk.buttontoaction.domain.usecase.action_button

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.cool_down.GetButtonCoolDownUseCase

class CheckButtonCoolDownUseCase(
    private val getButtonCoolDownUseCase: GetButtonCoolDownUseCase
) {
    operator fun invoke(type: ButtonActionType, coolDownMillis: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastTimeClicked = getButtonCoolDownUseCase(type)
        return lastTimeClicked?.let {
            currentTime - it > coolDownMillis
        } ?: true
    }
}