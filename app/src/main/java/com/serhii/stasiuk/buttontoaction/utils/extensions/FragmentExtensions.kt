package com.serhii.stasiuk.buttontoaction.utils.extensions

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


data class ActivityResultWrapper<IN, OUT>(
    var onResult: ((OUT) -> Unit) = {},
    var launcher: ActivityResultLauncher<IN>? = null,
) {
    fun launch(data: IN, onResult: (OUT) -> Unit) {
        this.onResult = onResult
        launcher?.launch(data)
    }
}

fun Fragment.getActivityResultLauncher(): ActivityResultWrapper<Intent, ActivityResult> {
    val wrapper = ActivityResultWrapper<Intent, ActivityResult>()
    wrapper.launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        wrapper.onResult.invoke(it)
    }
    return wrapper
}

fun Fragment.getPermissionRequestLauncher(): ActivityResultWrapper<String, Boolean> {
    val wrapper = ActivityResultWrapper<String, Boolean>()
    wrapper.launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        wrapper.onResult.invoke(it)
    }
    return wrapper
}

fun Fragment.getPermissionsRequestLauncher(): ActivityResultWrapper<Array<String>, Map<String, Boolean>> {
    val wrapper = ActivityResultWrapper<Array<String>, Map<String, Boolean>>()
    wrapper.launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            wrapper.onResult.invoke(it)
        }
    return wrapper
}

fun Fragment.showToast(message: String): Toast? {
    return activity?.run {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).also {
            it.show()
        }
    }
}