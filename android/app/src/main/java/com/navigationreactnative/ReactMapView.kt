package com.navigationreactnative

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.ui.MapView

class ReactMapView constructor(private val reactContext: ReactContext) :
    LinearLayout(reactContext), LifecycleEventListener {

    private var position: GeoPoint? = null
    private var zoom: Double? = null
    private var mapViewResumed: Boolean = false
    private var disposed: Boolean = false
    private var shouldUpdateCamera = false
    private lateinit var mapView: MapView

    fun setCenter(center: ReadableMap?) {
        if (center == null) {
            position = null
            return
        }

        val latitude = center?.getDouble("latitude") ?: return
        val longitude = center?.getDouble("longitude") ?: return
        position = GeoPoint(latitude, longitude)

        shouldUpdateCamera = true
    }

    fun setZoom(zoom: Int?) {
        this.zoom = zoom?.toDouble()
        shouldUpdateCamera = true
    }

    fun updateCamera(map: TomTomMap) {
        Log.d("ReactMapView", "updateCamera, position: $position")

        val cameraOptions = CameraOptions(position = position, zoom = zoom)
        map.moveCamera(cameraOptions)
    }

    fun onAfterUpdateTransaction(superMethod: () -> Unit) {
        mapView.getMapAsync { map ->
            superMethod()
            if (shouldUpdateCamera) {
                updateCamera(map)
                shouldUpdateCamera = false
            }
        }
    }

    fun onCreateViewInstance() {
        mapView = MapView(reactContext, MapOptions(mapKey = BuildConfig.TOMTOM_API_KEY))
        mapView.onCreate(Bundle())
        addView(mapView)
        reactContext.addLifecycleEventListener(this)
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
}