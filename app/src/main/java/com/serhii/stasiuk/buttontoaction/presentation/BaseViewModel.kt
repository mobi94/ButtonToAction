package com.serhii.stasiuk.buttontoaction.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.stasiuk.buttontoaction.utils.Logger
import com.serhii.stasiuk.buttontoaction.utils.livedata.LiveEvent
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    val loadingStateLiveData = MediatorLiveData<Boolean>()
    val errorStateLiveData = LiveEvent<ErrorResult>()

    fun showLoading(show: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            loadingStateLiveData.value = show
        }
    }

    fun showError(
        exception: Exception? = null,
        message: String? = null,
        @StringRes messageRes: Int? = null,
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            errorStateLiveData.value = ErrorResult(exception, message, messageRes)
        }
    }

    protected fun launchAsync(
        showLoading: Boolean = true,
        showError: Boolean = true,
        onError: ((Exception) -> Unit)? = null,
        finally: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            if (showLoading) showLoading(true)
            try {
                block.invoke(this)
            } catch (e: CancellationException) {

            } catch (e: Exception) {
                Logger.e("Error", "while launching async", e)
                if (showError) {
                    if (onError == null) showError(e)
                    else onError.invoke(e)
                }
            } finally {
                if (showLoading) showLoading(false)
                finally?.invoke()
            }
        }
    }

    protected suspend fun launch(
        showLoading: Boolean = true,
        showError: Boolean = true,
        onError: ((Exception) -> Unit)? = null,
        finally: (() -> Unit)? = null,
        block: suspend () -> Unit,
    ) {
        if (showLoading) showLoading(true)
        try {
            block.invoke()
        } catch (e: CancellationException) {

        } catch (e: Exception) {
            Logger.e("Error", "while launching async", e)
            if (showError) {
                if (onError == null) showError(e)
                else onError.invoke(e)
            }
        } finally {
            if (showLoading) showLoading(false)
            finally?.invoke()
        }
    }
}

data class ErrorResult(
    val exception: Exception?,
    val message: String? = null,
    @StringRes val messageRes: Int? = null,
)
