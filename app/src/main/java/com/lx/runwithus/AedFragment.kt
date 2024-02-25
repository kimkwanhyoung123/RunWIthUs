package com.lx.runwithus

import CalendarFragment
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentRunningListener
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.api.BasicClient
import com.lx.runwithus.databinding.FragmentAedBinding
import com.lx.runwithus.json.AEDResponse
import com.permissionx.guolindev.PermissionX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AedFragment : Fragment() {

    var _binding: FragmentAedBinding? = null
    val binding get() = _binding!!

    var lat:Double? = null
    var lon:Double? = null

    var listener: OnRunningClickListener? = null
    var locationClient: FusedLocationProviderClient? = null

    lateinit var runningMap: GoogleMap

    var myMarker: Marker? = null
    var memberMarker: Marker? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnRunningClickListener) {
            listener = activity
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAedBinding.inflate(inflater, container, false)

        initPermission()
        initMap()

        binding.goBackDetailed.setOnClickListener {
            emergencyOff()
            getReady(false)
            val intent = Intent(context, CrewActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return binding.root
    }


    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }

    override fun onDetach() {

        super.onDetach()

        listener = null

    }

    fun getReady(index:Boolean) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("userId", AppData.id).whereEqualTo("crewId", AppData.crewId).get().addOnSuccessListener {
                data ->
            for (datum in data) {

                if (index) {
                } else {
                    updateNotReady(datum.id)
                }
            }
        }
    }
    fun updateNotReady(id: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").document(id).update("ready", false).addOnSuccessListener {
            AppData.ready = false
        }
    }

    fun emergencyOff() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).update("emergency", false).addOnSuccessListener {
            println("긴급 해제")
        }
    }

    fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.runningMap) as? SupportMapFragment
        mapFragment?.getMapAsync {
            // 지도가 초기화되어 보일 수 있는 시점
            runningMap = it
            requestLocation()
            getAEDList()
        }
    }


    fun requestLocation() {
        // 위치 클라이언트 만들기
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        try {

            // 내 현재 위치 요청하기
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 60 * 1000

            val locationCallback = object: LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for((index, location) in result.locations.withIndex()) {
                        showCurrentLocation(location)
                    }
                }
            }

            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        } catch (e:SecurityException) {
            e.printStackTrace()
        }


    }


    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        lat = location.latitude
        lon = location.longitude

        runningMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint,18.0f))

        showMarker(curPoint)

    }



    fun showMarker(curPoint:LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("내위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin1))

            myMarker = runningMap.addMarker(it)
            myMarker?.tag = "1001"
        }
    }





    fun showToast(message:String) {
        Toast.makeText(requireContext(),message , Toast.LENGTH_LONG).show()
    }


    fun initPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grandtedList, deniedList ->
                if(allGranted) {
                    showToast("모든 위험권한 부여됨")
                } else {
                    showToast("위험권한 중에서 부여되지 않은 권한 있음")
                }
            }
    }

    fun getAEDList() {

        BasicClient.api.getAEDList(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })

        BasicClient.api.getAEDList2(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })

        BasicClient.api.getAEDList3(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })

        BasicClient.api.getAEDList4(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })

        BasicClient.api.getAEDList5(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })

        BasicClient.api.getAEDList6(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })

        BasicClient.api.getAEDList7(
            requestCode = "1001"
        ).enqueue(object: Callback<AEDResponse> {
            override fun onResponse(call: Call<AEDResponse>, response: Response<AEDResponse>) {
                //println("onResponse 호출됨 : ${response.body().toString()}")

                val theAEDList = response.body()
                println("성공 : ${theAEDList!!.tbEmgcAEDInfo.listTotalCount}")
                showList(theAEDList)
            }

            override fun onFailure(call: Call<AEDResponse>, t: Throwable) {
                println("onFailure 호출됨")
            }

        })
    }

    fun showList(theAEDList: AEDResponse?) {

        theAEDList?.apply {
            for (theAED in this.tbEmgcAEDInfo.row) {
                var theAEDlat = theAED.wgs84lat.toDouble()
                var theAEDlon = theAED.wgs84lon.toDouble()
                if (lat!! - 0.002 <= theAEDlat && lat!! + 0.002 >= theAEDlat && lon!! - 0.01 <= theAEDlon && lon!! + 0.01 >= theAEDlon) {
                    val theAEDPoint = LatLng(theAEDlat, theAEDlon)
                    showMarker2(theAEDPoint)
                }

            }
        }
    }

    fun showMarker2(curPoint:LatLng) {

        MarkerOptions().also {
            it.position(curPoint)
            it.title("AED 위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.aed))

            memberMarker = runningMap.addMarker(it)
        }
    }



}