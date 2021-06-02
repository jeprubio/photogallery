package com.rumosoft.feature_images.infrastructure.extensions

import android.Manifest
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fondesa.kpermissions.anyGranted
import com.fondesa.kpermissions.extension.liveData
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.rumosoft.feature_images.R

fun Fragment.askForCameraPermissions(onGranted: () -> Unit) {
    val request = permissionsBuilder(
            Manifest.permission.CAMERA
    ).build()
    request.liveData().observe(viewLifecycleOwner) { result ->
        if (result.anyGranted()) {
            onGranted()
        } else {
            Toast.makeText(requireContext(), getString(R.string.permissions_not_granted), Toast.LENGTH_SHORT).show()
        }
    }
    request.send()
}