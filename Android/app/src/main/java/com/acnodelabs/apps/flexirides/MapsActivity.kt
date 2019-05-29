package com.acnodelabs.apps.flexirides

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private lateinit var mMap: GoogleMap

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Set Spinner
        val ride_options : Spinner = findViewById(R.id.spinnerOptions)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)

        // Add a marker in Sydney and move the camera
        FlexiAddMeOnTheMap()
        FlexiAddAllTaxisAroundMe()
        FlexiHomeOnMe()
    }

    private fun FlexiHomeOnMe() {
        XShow(XMyLoc())
    }

    class Veh {
        var aTitle: String = ""
        var lat: Double = 0.0
        var lon: Double = 0.0
    }

    private fun FlexiDBGetTaxis() : Array<Veh> {
        var arr : Array<Veh> = arrayOf()
        for (i in 1..10) {
            var veh: Veh = Veh()
            veh.aTitle = "Taxi" + (i).toString()
            var pos: LatLng = XShake(XMyLoc())
            veh.lat = pos.latitude
            veh.lon = pos.longitude
            arr += veh
        }
        return arr
    }



    private fun FlexiAddAllTaxisAroundMe() {

        //TODO: Did Not Appear
        var camPos : LatLng = mMap.cameraPosition.target
        XMark(camPos,"From")

        for (v in FlexiDBGetTaxis()) {
            XMark(LatLng(v.lat,v.lon), v.aTitle)
        }
    }

    private fun FlexiAddMeOnTheMap() {
        XMark(XMyLoc(), "Me")
    }

    ////////   XFucntions : Just One Layer Above i.e Google-Agnostic
    private fun XMyLoc(): LatLng {
        val curr = LatLng(33.625033,73.098998)
        return curr
    }
    
    private fun XShake(pos: LatLng, mag: Float = 2.0f): LatLng {
        var npos: LatLng
        var rx: Double = (Random.nextDouble() - mag * Random.nextDouble()) / (mag* 10.0)
        var ry: Double = (Random.nextDouble() - mag * Random.nextDouble()) / (mag* 10.0)

        npos = LatLng( pos.latitude + rx, pos.longitude + ry)
        return npos
    }
    private fun XMark(sydney: LatLng, aTitle: String) {
        mMap.addMarker(MarkerOptions().position(sydney).title(aTitle))
    }

    private fun XShow(aLoc: LatLng, z: Float = 13.0f) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aLoc,z))
    }

    /////
    class f2 { //Agnostic Pos
       public var x: Double = 0.0
       public var y: Double = 0.0
    }
    //

}
