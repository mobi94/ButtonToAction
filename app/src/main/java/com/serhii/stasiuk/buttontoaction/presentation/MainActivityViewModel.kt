package com.serhii.stasiuk.buttontoaction.presentation

import androidx.lifecycle.MutableLiveData
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import com.serhii.stasiuk.buttontoaction.domain.usecase.FetchButtonPropertiesUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.GetButtonActionUseCase

class MainActivityViewModel(
    private val fetchButtonPropertiesUseCase: FetchButtonPropertiesUseCase,
    private val getButtonActionUseCase: GetButtonActionUseCase
) : BaseViewModel() {

    private var properties: List<ButtonProperty> = listOf()
    val actionLiveData = MutableLiveData<ButtonActionType?>()
    private var lastTimeClicked: Long? = null

    init {
        fetchButtonProperties()
    }

    private fun fetchButtonProperties() {
        launchAsync {
            properties = fetchButtonPropertiesUseCase()
        }
    }

    fun getAction() {
        lastTimeClicked = System.currentTimeMillis()
        actionLiveData.value = getButtonActionUseCase(properties, lastTimeClicked)
    }
}