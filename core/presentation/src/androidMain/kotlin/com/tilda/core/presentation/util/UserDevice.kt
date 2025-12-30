package com.tilda.core.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo

/**
 * A composable that provides information about the device's screen orientation.
 *
 * @return An [OrientationInfo] object containing details about the current orientation.
 */
@Composable
fun rememberScreenOrientation(): OrientationInfo {
    val configuration = LocalConfiguration.current
    return remember(configuration) {
        OrientationInfo(orientation = configuration.orientation)
    }
}

/**
 * A data class holding screen orientation details.
 *
 * @property orientation The raw orientation value from [Configuration].
 */
data class OrientationInfo(val orientation: Int) {
    /** True if the device is in a landscape orientation. */
    val isLandscape: Boolean
        get() = orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * A composable that provides information about the foldable state of a device.
 * It uses [WindowInfoTracker] to detect folding features.
 *
 * @return A [FoldableInfo] object containing details about the device's fold state.
 */
@SuppressLint("RestrictedApi")
@Composable
fun rememberFoldableInfo(): FoldableInfo {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val windowInfoTracker = remember(context) { WindowInfoTracker.getOrCreate(context) }

    val windowLayoutInfo by windowInfoTracker
        .windowLayoutInfo(lifecycleOwner as Activity)
        .collectAsState(initial = WindowLayoutInfo(emptyList()))

    val foldingFeature = windowLayoutInfo.displayFeatures
        .filterIsInstance<FoldingFeature>()
        .firstOrNull()

    return remember(foldingFeature) {
        FoldableInfo(foldingFeature)
    }
}

/**
 * A data class holding details about a device's folding capabilities.
 *
 * @property foldingFeature The underlying [FoldingFeature] from AndroidX Window Manager.
 */
data class FoldableInfo(
    private val foldingFeature: FoldingFeature?
) {
    /** True if the device has a folding feature (e.g., a hinge). */
    val isFoldable: Boolean
        get() = foldingFeature != null

    /** True if the fold is in a half-opened posture. */
    val isPostured: Boolean
        get() = foldingFeature?.state == FoldingFeature.State.HALF_OPENED

    /** True if the device is half-opened with a horizontal fold (like a laptop). */
    val isTabletop: Boolean
        get() = isPostured && foldingFeature?.orientation == FoldingFeature.Orientation.HORIZONTAL

    /** True if the device is half-opened with a vertical fold (like a book). */
    val isBookMode: Boolean
        get() = isPostured && foldingFeature?.orientation == FoldingFeature.Orientation.VERTICAL
}

data class DeviceWindowInfo(
    val foldableInfo: FoldableInfo,
    val orientationInfo: OrientationInfo
)

val LocalDeviceWindowInfo = staticCompositionLocalOf<DeviceWindowInfo> {
    error("No UserDeviceWindowInfo provided")
}
