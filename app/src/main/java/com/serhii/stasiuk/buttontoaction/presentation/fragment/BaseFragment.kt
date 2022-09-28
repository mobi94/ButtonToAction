package com.serhii.stasiuk.buttontoaction.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.serhii.stasiuk.buttontoaction.presentation.ErrorResult
import com.serhii.stasiuk.buttontoaction.utils.Logger
import com.serhii.stasiuk.buttontoaction.utils.extensions.showToast
import com.serhii.stasiuk.buttontoaction.utils.model.Event

abstract class BaseFragment<VBinding : ViewDataBinding>(
    private val provideBindingFactory: (
        inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
    ) -> VBinding?
) : Fragment() {

    private val TAG = this::class.java.simpleName
    private var toast: Toast? = null

    private var _binding: VBinding? = null
    protected val binding: VBinding
        get() = _binding
            ?: throw IllegalStateException("Cannot access view in after view destroyed and before view creation")

    protected abstract fun initialization(view: View, savedInstanceState: Bundle?)

    protected inline fun safeBind(block: VBinding.() -> Unit = {}): VBinding? {
        val tag = this::class.simpleName ?: "BaseFragment"
        return try {
            binding
        } catch (e: Exception) {
            Logger.w(tag, "failed to get binding", e)
            null
        }?.also {
            try {
                block(it)
            } catch (e: Exception) {
                Logger.w(tag, "failed to use binding", e)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return bind(inflater, container)
    }

    private fun bind(inflater: LayoutInflater, container: ViewGroup?): View? {
        return provideBindingFactory(inflater, container, false)?.apply {
            _binding = this
            setLifecycleOwner { lifecycle }
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization(view, savedInstanceState)
    }

    protected open fun popBackStack() {
        findNavController().popBackStack()
        Logger.d(TAG, "PoppedBack")
    }

    protected fun LiveData<Boolean>.listenLoadingState(progressView: View) {
        observe(viewLifecycleOwner) { isLoading ->
            progressView.isVisible = isLoading
        }
    }

    protected fun LiveData<Event<ErrorResult>>.listenErrorState() {
        observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let(::showErrorState)
        }
    }

    protected fun showErrorState(error: ErrorResult) {
        toast?.cancel()
        toast = error.exception?.message?.let { showToast(it) }
            ?: error.message?.let { showToast(it) }
                    ?: error.messageRes?.let { showToast(getString(it)) }
    }
}