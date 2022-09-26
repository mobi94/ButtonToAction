package com.serhii.stasiuk.buttontoaction.domain.repository

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty

interface ServerRepository {
    suspend fun getButtonProperties(): List<ButtonProperty>
}