package com.navigationreactnative

import android.os.Bundle
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.ui.MapView

class ReactMapView(private val reactContext: ReactApplicationContext) :
    SimpleViewManager<MapView>(), LifecycleEventListener {

    private var mapViewResumed: Boolean = false
    private var disposed: Boolean = false
    private lateinit var mapView: MapView

    override fun getName() = REACT_CLASS

    override fun createViewInstance(context: ThemedReactContext): MapView {
        mapView = MapView(reactContext, MapOptions(mapKey = BuildConfig.TOMTOM_API_KEY))
        mapView.onCreate(Bundle())
        reactContext.addLifecycleEventListener(this)
        return mapView
    }

    override fun onHostResume() {
        if (!mapViewResumed) {
            mapView.onStart()
            mapView.onResume()
            mapViewResumed = true
        }
    }

    override fun onHostPause() {
        if (mapViewResumed) {
            mapView.onPause()
            mapView.onStop()
            mapViewResumed = false
        }
    }

    override fun onHostDestroy() {
        if (disposed) {
            return
        }
        destroyMapView()
    }

    private fun destroyMapView() {
        reactContext.removeLifecycleEventListener(this)
        mapView.onDestroy()
        mapViewResumed = false
        disposed = true
    }

    companion object {
        const val REACT_CLASS = "RCTMapView"
    }
}