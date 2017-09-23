package com.jasonbutwell.kotlinankodemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.view.ViewManager
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

// Extension function
fun ViewManager.mapView() = mapView {}
fun ViewManager.mapView(init: MapView.() -> Unit) = ankoView({ MapView(it) }, init)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                // Note: There is no setContentView here!

        // Required to get the mapping tiles
        Configuration.getInstance().load( this, PreferenceManager.getDefaultSharedPreferences( this ) );

        // Lat / Lon
        val aLatitude = 51.05
        val aLongitude = -0.72

        // For setting up the center location and marker
        val center = GeoPoint( aLatitude, aLongitude )
        val zoomLevel = 17
        val markerTitle = "Some title text here"
        val markerAlpha = 0.75f

        // Create our layout using the Anko DSL

        val layout = verticalLayout {
            val texTV = textView( "This is a textView" ) {
                id = 1 }

            val name = editText() {
                hint = "this is the hint"
            }

            val button = button("Ok") {
                onClick { if (name.length() > 0) toast(name.text) }
            }

            val mapView = mapView() {
                setBuiltInZoomControls(true)

                controller.setZoom(zoomLevel)
                controller.setCenter(center)

            }.lparams {
                width = matchParent
                height = matchParent
            }

            texTV.text = "This is more text"

            // Set up a marker and display over the map as an Overlay

            val startMarker = Marker( mapView )

            with ( startMarker ) {
                alpha = markerAlpha
                title = markerTitle
                position = center

                setIcon( ContextCompat.getDrawable(context,R.drawable.map_marker_small ) )
                setAnchor( Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM )
            }

            // Adds the newly created marker

            mapView.overlays.add( startMarker )
        }

        async {

            // Code here happens in secondary thread - like async task

            uiThread {

                // Code here happens in UI thread

            }
        }

    }
}
