package com.serhii.stasiuk.buttontoaction.presentation

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.serhii.stasiuk.buttontoaction.databinding.ActivityMainBinding
import com.serhii.stasiuk.buttontoaction.domain.entity.ButtonActionType
import com.serhii.stasiuk.buttontoaction.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModel()
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        binding.mainButton.setOnClickListener {
            viewModel.getAction()
        }
    }

    private fun observeViewModel() {
        viewModel.loadingStateLiveData.observe(this, ::onProgressChanged)
        viewModel.errorStateLiveData.observe(this, ::onError)
        viewModel.actionLiveData.observe(this, ::onError)
    }

    private fun onProgressChanged(isLoading: Boolean) {
        binding.apply {
            progressLayout.isVisible = isLoading
            mainButton.isEnabled = !isLoading
        }
    }

    private fun onError(error: ErrorResult) {
        toast?.cancel()
        toast = error.exception?.message?.let { showToast(it) }
            ?: error.message?.let { showToast(it) }
                    ?: error.messageRes?.let { showToast(getString(it)) }
    }

    private fun onError(action: ButtonActionType?) {
        when (action) {
            ButtonActionType.ANIMATION -> {
                binding.mainButton.animate().rotation(360f)
                    .setDuration(700).setInterpolator(AccelerateDecelerateInterpolator()).start()
            }
            ButtonActionType.TOAST -> showToast("Action is Toast!")
            ButtonActionType.CALL -> {
                // Todo
            }
            ButtonActionType.NOTIFICATION -> {
                // Todo
            }
            null -> {
                // Nothing to do
            }
        }
    }
}