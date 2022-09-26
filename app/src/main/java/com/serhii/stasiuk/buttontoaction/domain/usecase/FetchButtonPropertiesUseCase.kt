package com.serhii.stasiuk.buttontoaction.domain.usecase

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import com.serhii.stasiuk.buttontoaction.domain.repository.ServerRepository

class FetchButtonPropertiesUseCase(
    private val repository: ServerRepository
) {
    suspend operator fun invoke(): List<ButtonProperty> {
        return repository.getButtonProperties()
    }
}