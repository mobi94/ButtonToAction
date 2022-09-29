package com.serhii.stasiuk.buttontoaction.presentation.fragment.main

import androidx.lifecycle.MutableLiveData
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonProperty
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.CheckButtonCoolDownUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.FetchButtonPropertiesUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.FindButtonActionUseCase
import com.serhii.stasiuk.buttontoaction.domain.usecase.action_button.cool_down.SaveButtonCoolDownUseCase
import com.serhii.stasiuk.buttontoaction.presentation.BaseViewModel
import com.serhii.stasiuk.buttontoaction.utils.Logger
import com.serhii.stasiuk.buttontoaction.utils.livedata.mapLiveEvent
import kotlin.system.measureTimeMillis

class MainFragmentViewModel(
    private val fetchButtonPropertiesUseCase: FetchButtonPropertiesUseCase,
    private val findButtonActionUseCase: FindButtonActionUseCase,
    private val checkButtonCoolDownUseCase: CheckButtonCoolDownUseCase,
    private val saveButtonCoolDownUseCase: SaveButtonCoolDownUseCase
) : BaseViewModel() {

    companion object {
        private val TAG = MainFragmentViewModel::class.simpleName
    }

    private var properties: List<ButtonProperty> = listOf()
    private val buttonPropertyLiveData = MutableLiveData<ButtonProperty?>()
    val actionLiveData = buttonPropertyLiveData.mapLiveEvent { it?.type }

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

    fun findButtonAction() {
        buttonPropertyLiveData.value = findButtonActionUseCase(properties)?.also {
            buttonPropertyLiveData.value?.let {
                val isClickable = checkButtonCoolDownUseCase(it.type, it.coolDownMillis)
                if (isClickable) saveButtonCoolDownUseCase(it.type)
            }
        }
    }
}