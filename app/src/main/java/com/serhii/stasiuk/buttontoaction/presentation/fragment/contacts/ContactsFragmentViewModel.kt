package com.serhii.stasiuk.buttontoaction.presentation.fragment.contacts

import androidx.lifecycle.MutableLiveData
import com.serhii.stasiuk.buttontoaction.domain.usecase.FetchUserContactsUseCase
import com.serhii.stasiuk.buttontoaction.presentation.BaseViewModel
import com.serhii.stasiuk.buttontoaction.presentation.model.ContactAdapterItem

class ContactsFragmentViewModel(
    private val fetchUserContactsUseCase: FetchUserContactsUseCase
) : BaseViewModel() {
    val contactsLiveData = MutableLiveData<List<ContactAdapterItem>>()

    fun fetchContacts() {
        launchAsync {
            val list = fetchUserContactsUseCase()
            contactsLiveData.postValue(list)
        }
    }
}