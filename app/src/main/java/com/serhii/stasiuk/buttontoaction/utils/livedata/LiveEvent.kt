package com.serhii.stasiuk.buttontoaction.utils.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.serhii.stasiuk.buttontoaction.utils.model.Event

class LiveEvent<T> : MediatorLiveData<Event<T>> {
    constructor() : super()
    constructor(value: T) : super() {
        setValue(value)
    }

    @MainThread
    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) {
        val wrapper = ObserverWrapper(observer)
        super.observe(owner, wrapper)
    }

    fun safeObserve(owner: LifecycleOwner, action: (T & Any) -> Unit) {
        observe(owner) {
            it?.let(action)
        }
    }

    var value: T?
        @JvmName("get1")
        get() = super.getValue()?.peekContent()
        @JvmName("set1")
        set(value) {
            setValue(value)
        }

    @JvmName("setValue1")
    @MainThread
    fun setValue(value: T?) {
        super.setValue(value?.map())
    }

    @JvmName("postValue1")
    fun postValue(value: T?) {
        super.postValue(value?.map())
    }

    inline fun <Y> map(crossinline transform: (T) -> Y): LiveEvent<Y> {
        val result = LiveEvent<Y>()
        result.addSource(this) {
            val transformed: Y = transform(it.peekContent())
            result.value = transformed
        }
        return result
    }

    private fun T.map(): Event<T> = Event(this)

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<Event<T>> {
        override fun onChanged(event: Event<T>?) {
            event?.getContentIfNotHandled()?.let {
                observer.onChanged(it)
            }
        }
    }
}

inline fun <T, Y> LiveData<T>.mapLiveEvent(crossinline transform: (T) -> Y): LiveEvent<Y> {
    val result = LiveEvent<Y>()
    result.addSource(this) {
        val transformed: Y = transform(it)
        result.value = transformed
    }
    return result
}