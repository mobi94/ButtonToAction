package com.serhii.stasiuk.buttontoaction.presentation.fragment.main

import androidx.lifecycle.MutableLiveData
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import com.serhii.stasiuk.buttontoaction.domain.usecase.FetchButtonPropertiesUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.GetButtonActionUseCase
import com.serhii.stasiuk.buttontoaction.presentation.BaseViewModel
import com.serhii.stasiuk.buttontoaction.utils.Logger
import com.serhii.stasiuk.buttontoaction.utils.livedata.LiveEvent
import kotlin.system.measureTimeMillis

class MainFragmentViewModel(
    private val fetchButtonPropertiesUseCase: FetchButtonPropertiesUseCase,
    private val getButtonActionUseCase: GetButtonActionUseCase
) : BaseViewModel() {

    companion object {
        private val TAG = MainFragmentViewModel::class.simpleName
    }

    private var properties: List<ButtonProperty> = listOf()
    val actionLiveData = LiveEvent<ButtonActionType?>()
    private var lastTimeClicked: Long? = null

    init {
        fetchButtonProperties()
    }

    private fun fetchButtonProperties() {
        launchAsync {
            val time = measureTimeMillis {
                properties = fetchButtonPropertiesUseCase()
            }
            Logger.d(TAG, "Network request measured time: $time ms")
        }
    }

    fun getAction() {
        actionLiveData.value = ButtonActionType.CALL//getButtonActionUseCase(properties, lastTimeClicked)
        lastTimeClicked = System.currentTimeMillis()
    }
}