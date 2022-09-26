package com.serhii.stasiuk.buttontoaction.data.repository

import com.serhii.stasiuk.buttontoaction.data.datasource.remote.ServerApi
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import com.serhii.stasiuk.buttontoaction.domain.repository.ServerRepository

class ServerRepositoryImpl(
    private val serverApi: ServerApi
): ServerRepository {
    override suspend fun getButtonProperties(): List<ButtonProperty> {
        return serverApi.getButtonProperties()
    }
}