package com.serhii.stasiuk.buttontoaction.presentation.fragment.contacts

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.serhii.stasiuk.buttontoaction.R
import com.serhii.stasiuk.buttontoaction.databinding.FragmentContactsBinding
import com.serhii.stasiuk.buttontoaction.presentation.adapter.ContactsListAdapter
import com.serhii.stasiuk.buttontoaction.presentation.fragment.BaseFragment
import com.serhii.stasiuk.buttontoaction.presentation.model.ContactAdapterItem
import com.serhii.stasiuk.buttontoaction.utils.PermissionChecker
import com.serhii.stasiuk.buttontoaction.utils.extensions.callFromDialer
import com.serhii.stasiuk.buttontoaction.utils.extensions.sendEmail
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    ContactsListAdapter.ContactClickListener {

    private val contactsPermissionChecker = PermissionChecker(
        this,
        arrayOf(Manifest.permission.READ_CONTACTS),
        R.string.contacts_permission_rationale_title,
        R.string.contacts_permission_rationale_message,
        R.string.contacts_permission_rationale_message_never_ask
    )
    private val viewModel: ContactsFragmentViewModel by viewModel()
    private val adapter by lazy { ContactsListAdapter(this) }

    override fun initialization(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeViewModel()
        checkPermission()
    }

    private fun checkPermission() {
        contactsPermissionChecker.checkPermissions {
            if (it) viewModel.fetchContacts()
            else popBackStack()
        }
    }

    private fun initViews() {
        safeBind {
            toolbar.setupWithNavController(findNavController())
            contactsList.adapter = adapter
        }
    }

    override fun onContactClick(item: ContactAdapterItem) {
        context?.apply {
            if (item.phoneNumber.isNotBlank()) callFromDialer(item.phoneNumber)
            else if (item.email.isNotBlank()) sendEmail(item.email)
        }
    }

    private fun observeViewModel() {
        viewModel.loadingStateLiveData.listenLoadingState(binding.progressLayout)
        viewModel.errorStateLiveData.listenErrorState()
        viewModel.contactsLiveData.observe(viewLifecycleOwner, ::onContactsLoaded)
    }

    private fun onContactsLoaded(contacts: List<ContactAdapterItem>) {
        binding.emptyListText.isVisible = contacts.isEmpty()
        adapter.submitList(contacts)
    }
}