package com.serhii.stasiuk.buttontoaction.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.serhii.stasiuk.buttontoaction.R
import com.serhii.stasiuk.buttontoaction.utils.extensions.getActivityResultLauncher
import com.serhii.stasiuk.buttontoaction.utils.extensions.getPermissionsRequestLauncher

class PermissionChecker(
    private val fragment: Fragment,
    private val permissions: Array<String>,
    @StringRes
    private val rationaleTitle: Int,
    @StringRes
    private val rationaleMessageText: Int,
    @StringRes
    private val rationaleMessageNeverAskText: Int = rationaleMessageText,
) {
    private val requestsLauncher = fragment.getPermissionsRequestLauncher()
    private val resultLauncher = fragment.getActivityResultLauncher()
    private var onResult: ((Boolean) -> Unit)? = null
    private var rationaleDialog: AlertDialog? = null
    private var neverAskAgainDialog: AlertDialog? = null


    private val context: Context
        get() = fragment.requireContext()

    fun checkPermissions(onResult: (Boolean) -> Unit) {
        this.onResult = onResult
        if (shouldShowPermissionRationale()) showPermissionRationale()
        else runRequestLauncher()
    }

    fun resetState() {
        onResult = null
        rationaleDialog?.dismiss()
        neverAskAgainDialog?.dismiss()
    }

    private fun runRequestLauncher() {
        rationaleDialog?.dismiss()
        requestsLauncher.launch(permissions) { results ->
            if (results.all { it.value }) {
                onResult?.invoke(true)
            } else {
                if (shouldShowPermissionRationale()) onResult?.invoke(false)
                else showPermissionsNeverAskAgainDialog()
            }
        }
    }

    private fun shouldShowPermissionRationale(): Boolean {
        return permissions.any { fragment.shouldShowRequestPermissionRationale(it) }
    }

    private fun showPermissionRationale() {
        rationaleDialog?.dismiss()
        rationaleDialog = MaterialAlertDialogBuilder(context)
            .setTitle(rationaleTitle)
            .setMessage(rationaleMessageText)
            .setPositiveButton(R.string.ok) { _, _ ->
                runRequestLauncher()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                onResult?.invoke(false)
            }
            .setCancelable(false)
            .show()
    }

    private fun showPermissionsNeverAskAgainDialog() {
        neverAskAgainDialog?.dismiss()
        neverAskAgainDialog = MaterialAlertDialogBuilder(context)
            .setTitle(rationaleTitle)
            .setMessage(rationaleMessageNeverAskText)
            .setPositiveButton(R.string.settings) { _, _ ->
                openSettings()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                onResult?.invoke(false)
            }
            .setCancelable(false)
            .show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        resultLauncher.launch(intent) {
            runRequestLauncher()
        }
    }
}
