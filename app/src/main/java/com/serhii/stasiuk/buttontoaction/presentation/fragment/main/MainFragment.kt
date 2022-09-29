package com.serhii.stasiuk.buttontoaction.presentation.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.serhii.stasiuk.buttontoaction.R
import com.serhii.stasiuk.buttontoaction.databinding.FragmentMainBinding
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.presentation.fragment.BaseFragment
import com.serhii.stasiuk.buttontoaction.utils.extensions.animateRotation
import com.serhii.stasiuk.buttontoaction.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainFragmentViewModel by viewModel()

    override fun initialization(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        safeBind {
            mainButton.setOnClickListener {
                viewModel.findButtonAction()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.loadingStateLiveData.observe(this, ::onProgressChanged)
        viewModel.errorStateLiveData.listenErrorState()
        viewModel.actionLiveData.observe(this, ::onActionChanged)
    }

    private fun onProgressChanged(isLoading: Boolean) {
        safeBind {
            progressLayout.isVisible = isLoading
            mainButton.isEnabled = !isLoading
        }
    }

    private fun onActionChanged(action: ButtonActionType?) {
        when (action) {
            ButtonActionType.ANIMATION -> binding.mainButton.animateRotation(360f)
            ButtonActionType.TOAST -> showToast(getString(R.string.action_is_toast))
            ButtonActionType.CALL ->
                findNavController().navigate(MainFragmentDirections.actionToContactsFragment())
            ButtonActionType.NOTIFICATION -> {
                // Todo
            }
            null -> {
                // Nothing to do
            }
        }
    }
}