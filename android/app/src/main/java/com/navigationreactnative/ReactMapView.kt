package com.navigationreactnative

import android.view.View
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext

class ReactMapView : SimpleViewManager<View>() {
    override fun getName() = REACT_CLASS

    override fun createViewInstance(context: ThemedReactContext) =
        View(context)

    companion object {
        const val REACT_CLASS = "MapView"
    }
}