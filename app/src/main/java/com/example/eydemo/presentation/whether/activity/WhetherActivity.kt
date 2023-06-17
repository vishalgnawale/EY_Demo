package com.example.eydemo.presentation.whether.activity

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eydemo.R
import com.example.eydemo.data.modal.Main
import com.example.eydemo.data.modal.Weather
import com.example.eydemo.data.modal.Wind
import com.example.eydemo.databinding.ActivityWhetherBinding
import com.example.eydemo.presentation.location.LocationUtils
import com.example.eydemo.presentation.location.LocationViewModal
import com.example.eydemo.presentation.location.LocationViewModalFactory
import com.example.eydemo.presentation.whether.viewmodal.WhetherViewModal
import com.example.eydemo.presentation.whether.viewmodal.WhetherViewModalFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class WhetherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWhetherBinding
    @Inject
    lateinit var factory: WhetherViewModalFactory
    private lateinit var viewModal: WhetherViewModal

    @Inject
    lateinit var locationFactory:LocationViewModalFactory
    private lateinit var locationViewModal: LocationViewModal

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences
    private var isGPSEnabled =false
    private lateinit var geocoder: Geocoder
    private var latitude:Double=0.0
    private var longitude:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_whether)
        viewModal=ViewModelProvider(this,factory).get(WhetherViewModal::class.java)
        locationViewModal=ViewModelProvider(this,locationFactory).get(LocationViewModal::class.java)
        searchLocationEvent()
        startCloudAnimation()
        getGPSOn()
        if(!isPermissionsGranted()){
            requestPermission()
        }
        val city=getCityName()
        if(city != null&& !TextUtils.isEmpty(city)){
            displayWhetherData(city)
        }
    }


    private fun startCloudAnimation(){

        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 5000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width: Int = binding.imageView.getWidth()
            val translationX = width * progress
            binding.imageView.setTranslationX(translationX)
        }
        animator.start()

    }

    private fun showInputMethod(view: View) {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            imm.showSoftInput(view, 0)
        }
    }

    private fun searchLocationEvent(){

        binding.searchView.setOnQueryTextFocusChangeListener(object :OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(hasFocus){
                    if (v != null) {
                        showInputMethod(v)
                    }
                }
            }

        })
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    displayWhetherData(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun displayWhetherData(city :String){
        saveInSharedPref(city)
        val responseLiveData=viewModal.getWhetherData(city)
        responseLiveData.observe(this, Observer {
            if(it!=null){
                setWhetherData(it.weather)
                setWhetherMainData(it.main)
                setWindData(it.wind)
                binding.txtCityName.text=it.name
                Log.d(APPConstants.TAG, "displayWhetherData: "+it.name)
            }else{
                Toast.makeText(this,"No Data Available", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun setWhetherMainData(main: Main){
        binding.txtTemp.text=main.temp.toString()
        binding.txtFeelsLike.text=main.feelsLike.toString()
        binding.txtTempMax.text=main.tempMax.toString()
        binding.txtTempMin.text=main.tempMin.toString()
        binding.txtPressure.text=main.pressure.toString()
        binding.txtHumidity.text=main.humidity.toString()

    }
    private fun setWhetherData(weatherList: List<Weather>){
        for (item in weatherList){
            setWhetherData(item)
        }
    }

    private fun setWhetherData(weather: Weather){
        binding.txtMain.text=weather.main
        val posterURL = "https://openweathermap.org/img/wn/"+weather.icon+"@2x.png"
        Glide.with(binding.whetherImage)
            .load(posterURL)
            .error(R.drawable.ic_baseline_wb_sunny_24)
            .into(binding.whetherImage)
    }

    private fun setWindData(wind: Wind){
        binding.txtWindSpeed.text=wind.speed.toString()
        binding.txtDeg.text=wind.deg.toString()
    }

    private fun getGPSOn(){
        LocationUtils(this).setGPSOn(object : LocationUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })
    }

    private fun invokeLocationAction() {
        when {
            isPermissionsGranted() -> startLocationUpdate()
            else -> requestPermission()
        }
    }

  /*  override fun onStart() {
        super.onStart()
        if (isPermissionsGranted()) {
            startLocationUpdate()
        }
    }*/

    private fun requestPermission() = ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
        APPConstants.LOCATION_REQUEST)

    private fun startLocationUpdate() {
        locationViewModal.getLocationData().observe(this, androidx.lifecycle.Observer {
            Log.d(APPConstants.TAG, "getAddressList: ${it.latitude} ${it.longitude}")
            getAddressList(it.latitude,it.longitude)
            latitude=it.latitude
            longitude=it.longitude
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            APPConstants.LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }

    }



    private fun getAddressList(latitude:Double, longitude:Double){
        geocoder= Geocoder(this, Locale.getDefault())
        val addressList=geocoder.getFromLocation(latitude,longitude,1)
        if(addressList!=null){
            val builder = StringBuilder()
            builder.append(addressList[0]?.adminArea)
                .append(", ")
                .append(addressList[0]?.getAddressLine(0))
                .append(",")
                .append(addressList[0]?.locality)
                .append(",")
                .append(addressList[0]?.featureName)
                .append(",")
                .append( addressList[0]?.postalCode)
                .append(".")
            Log.d(APPConstants.TAG, "getAddressList: $builder")

            displayWhetherData(addressList[0].locality)
        }
    }
    private fun saveInSharedPref(city: String){
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("city",city)
        editor.apply()
        editor.commit()
    }

    private fun getCityName(): String? {
        val sharedNameValue = sharedPreferences.getString("city","")
        return sharedNameValue
    }
}

object APPConstants {

    const val LOCATION_REQUEST = 200
    const val GPS_REQUEST = 201
    const val TAG="Weather Data"
}