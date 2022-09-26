package com.serhii.stasiuk.buttontoaction.data.datasource.remote

import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import retrofit2.http.GET

interface ServerApi {
    @GET("/androidexam/butto_to_action_config.json")
    suspend fun getButtonProperties(): List<ButtonProperty>
}