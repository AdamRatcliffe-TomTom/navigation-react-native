package com.navigationreactnative

import android.view.View
import android.widget.TextView
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp


class ReactMapView(private val callerContext: ReactApplicationContext) : SimpleViewManager<TextView>() {
    private lateinit var view: TextView;

    override fun getName() = REACT_CLASS

    override fun createViewInstance(context: ThemedReactContext):TextView {
        view = TextView(context)
        view.text = "Hello Android!"
        view.textSize = 20f
        return view
    }

    @ReactProp(name = "style")
    fun setStyle(view: TextView, style: ReadableMap) {
        if (style.hasKey("color")) {
            val color = style.getInt("color")
            view.setTextColor(color)
        }
    }

    companion object {
        const val REACT_CLASS = "RCTMapView"
    }
}