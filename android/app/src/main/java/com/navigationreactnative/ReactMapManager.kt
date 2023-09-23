package com.navigationreactnative

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class ReactMapManager(private val callerContext: ReactApplicationContext) :
    SimpleViewManager<ReactMapView>() {

    private lateinit var mapView: ReactMapView

    override fun getName() = REACT_CLASS

    override fun onAfterUpdateTransaction(view: ReactMapView) {
        view.onAfterUpdateTransaction {
            super.onAfterUpdateTransaction(view)
        }
    }

    override fun createViewInstance(context: ThemedReactContext): ReactMapView {
        mapView = ReactMapView(context)
        mapView.onCreateViewInstance()
        return mapView
    }

    @ReactProp(name = "center")
    @Suppress("UNUSED")
    fun setCenterCoordinates(view: ReactMapView, center: ReadableMap?) {
        view.setCenter(center)
    }

    @ReactProp(name = "zoom")
    @Suppress("UNUSED")
    fun setZoom(view: ReactMapView, zoom: Int?) {
        view.setZoom(zoom)
    }

    companion object {
        const val REACT_CLASS = "RCTMapView"
    }
}