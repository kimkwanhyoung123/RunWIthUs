package com.lx.runwithus

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentRunningListener
import com.lx.runwithus.AppData
import com.lx.runwithus.R
import com.lx.runwithus.databinding.FragmentRunningBinding
import com.permissionx.guolindev.PermissionX
import java.lang.reflect.Member
import java.util.Timer
import java.util.TimerTask


class RunningFragment : Fragment() {

    var index = true
    var count = 0
    var count2 = 0
    var count3 = 0
    var timer = Handler(Looper.getMainLooper())
    var timer2 = Handler(Looper.getMainLooper())
    lateinit var _binding: FragmentRunningBinding
    val binding get() = _binding
    var locationRequest: LocationRequest? = null


    var indexCode = false

    lateinit var runningReadyAdapter: RunningReadyAdapter

    var listener: OnFragmentRunningListener? = null
    var locationClient: FusedLocationProviderClient? = null

    lateinit var runningMap: GoogleMap

    var myMarker: Marker? = null
    var memberMarker: Marker? = null

    var memberList: ArrayList<String>? = null


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)


        if (activity is OnFragmentRunningListener) {
            listener = activity
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRunningBinding.inflate(inflater, container, false)
        index = true





        binding.crewMemberOutput.text = AppData.crewName


        //initReadyList()

        readyandstart()

        refreshList()



        initPermission()

        initMap()

        syncro()

        clickStretching()




        return binding.root


    }


    override fun onDetach() {

        locationRequest = null
        timer.removeCallbacksAndMessages(null)
        timer2.removeCallbacksAndMessages(null)
        super.onDetach()

        listener = null

    }

    fun refreshList() {
        initReadyList()
        timer2.postDelayed(::refreshList, 15000)
    }

    fun clickStretching() {
        binding.stretchingDialogButton.setOnClickListener {
            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.stretching_dialog, null)
            val btnClose = dialogView.findViewById<CardView>(R.id.btnClose)

            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogView)

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    fun readyandstart() {


        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("crewId", AppData.crewId)
            .whereEqualTo("userId", AppData.id).get().addOnSuccessListener { data ->

                for (datum in data) {
                    var ready = datum.get("ready") as Boolean
                    var grade = datum.get("grade").toString()
                    AppData.grade = grade
                    AppData.ready = ready
                    indexCode = ready

                    if (grade == "크루 리더") {
                        if (ready) {
                            getReady(false)
                        }
                    } else if (grade == "크루 멤버") {

                        if (ready) {

                            binding.readyButtonText.text = "준비 취소"
                        } else {
                            binding.readyButtonText.text = "준비"
                        }

                    }
                    binding.readyButton.setOnClickListener {

                        count3 = 0

                        if (grade == "크루 리더") {
                            getReady(true)
                            val mainIntent = Intent(context, RunningActivity::class.java)
                            //mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(mainIntent)

                        } else if (grade == "크루 멤버") {
                            if (indexCode) {
                                getReady(false)
                                binding.readyButtonText.text = "준비"
                                indexCode = false


                            } else {
                                getReady(true)
                                binding.readyButtonText.text = "준비 취소"
                                indexCode = true
                            }

                        } else {

                        }
                    }
                }
            }
    }


    fun getReady(index: Boolean) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("userId", AppData.id)
            .whereEqualTo("crewId", AppData.crewId).get().addOnSuccessListener { data ->
            for (datum in data) {

                if (index) {
                    updateReady(datum.id)
                    updateLocation(datum.id)
                } else {
                    updateNotReady(datum.id)
                }
            }

        }
        try {
            //memberList?.clear()
            initReadyList()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun updateReady(id: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").document(id).update("ready", true).addOnSuccessListener {
            AppData.ready = true
        }
    }


    fun updateNotReady(id: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").document(id).update("ready", false).addOnSuccessListener {
            AppData.ready = false
        }


    }

    fun syncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("crewId", AppData.crewId)
            .whereEqualTo("grade", "크루 리더").get().addOnSuccessListener { data ->
            for (datum in data) {
                if (datum.get("ready") as Boolean && datum.get("userId") != AppData.id && AppData.ready && index) {
                    index = false
                    locationRequest = null
                    val mainIntent = Intent(context, RunningActivity::class.java)
                    //mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(mainIntent)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)

                }

                timer.postDelayed(::syncro, 1000)
            }
        }
    }

    fun initReadyList() {
        count = 0
        count3 = 0

        // 리싸이클러뷰의 모양 설정 (리스트 모양, 격자 모양, 좌우 스크롤, 상하 스크롤 등을 결졍)
        binding.readyList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 리싸이클러뷰의 어댑터 설정
        runningReadyAdapter = RunningReadyAdapter()
        binding.readyList.adapter = runningReadyAdapter

        //리스너 등록하기
        runningReadyAdapter.listener = object : OnRunningClickListener {
            override fun onItemClcik(
                holder: RunningReadyAdapter.ViewHolder,
                view: View?,
                position: Int
            ) {
                val item = runningReadyAdapter.items.get(position)
//                showToast("아이템 선택됨 : ${item.name}, ${item.age}, ${item.photo}")   //아이템을 선택하면 실행되도록 한 기능이기 때문에 이 안에서 수정과 관련된 기능을 넣으면 될 듯...?
            }
        }

        getMemberList()

    }

    fun getMemberList() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get()
            .addOnSuccessListener { document ->
                count2 = 0
                if (document.get("member") != null) {
                    memberList = document.get("member") as ArrayList<String>
                    //runningReadyAdapter.items?.clear()
                    for (member in memberList!!) {
                        count2++
                        addMember(member)
                    }
                }

                binding.totalCount.text = count2.toString()

            }.addOnFailureListener { e ->
            Log.d(tag, "실패", e)
            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
        }

    }

    fun addMember(member: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("userId", member)
            .whereEqualTo("crewId", AppData.crewId).get().addOnSuccessListener { data ->


                for (datum in data) {
                    if (datum.get("ready") as Boolean) {
                        count++
                    }
                    count3++
                        runningReadyAdapter.items.add(
                            Member(
                                member,
                                datum.get("photo").toString(),
                                datum.get("record").toString(),
                                datum.get("grade").toString(),
                                datum.get("ready") as Boolean
                            )
                        )

                }
                runningReadyAdapter.items.sortBy { it.name }
                runningReadyAdapter.notifyDataSetChanged()

                binding.readyList.startLayoutAnimation()
                binding.count.text = count.toString()


            }
    }

    fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.runningMap) as? SupportMapFragment
        mapFragment?.getMapAsync {
            // 지도가 초기화되어 보일 수 있는 시점
            runningMap = it
            requestLocation()
        }
    }


    fun requestLocation() {
        // 위치 클라이언트 만들기
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        try {

            // 내 현재 위치 요청하기
            locationRequest = LocationRequest.create()
            locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest?.interval = 15 * 1000

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {
                        showCurrentLocation(location)
                        memberMarker?.remove()
                        showReadyMemberLocation()

                    }
                }
            }

            locationClient?.requestLocationUpdates(
                locationRequest!!,
                locationCallback,
                Looper.myLooper()
            )

        } catch (e: SecurityException) {
            e.printStackTrace()
        }


    }

    fun showReadyMemberLocation() {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("crewId", AppData.crewId)
            .whereEqualTo("ready", true).get().addOnSuccessListener { data ->
            for (datum in data) {

                var point = datum.get("point") as GeoPoint
                var latLngPoint = LatLng(point.latitude, point.longitude)
                if (datum.get("userId").toString() != AppData.id) {
                    showMarker2(latLngPoint)
                }

            }
        }
    }

    fun showMarker2(curPoint: LatLng) {

        MarkerOptions().also {
            it.position(curPoint)
            it.title("멤버 위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin2))

            memberMarker = runningMap.addMarker(it)
        }
    }


    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)

        runningMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18.5f))

        showMarker(curPoint)

    }

    fun updateLocation(id: String) {
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        try {

            // 내 현재 위치 요청하기
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 30 * 1000

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {
                        val databaseR = Firebase.firestore
                        databaseR.collection("records").document(id)
                            .update("point", GeoPoint(location.latitude, location.longitude))
                            .addOnSuccessListener {

                            }
                    }
                }
            }

            locationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )

        } catch (e: SecurityException) {
            e.printStackTrace()
        }


    }


    fun showMarker(curPoint: LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("내위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin1))

            myMarker = runningMap.addMarker(it)
            myMarker?.tag = "1001"
        }
    }


    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


    fun initPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grandtedList, deniedList ->
                if (allGranted) {
                    //showToast("모든 위험권한 부여됨")
                } else {
                    //showToast("위험권한 중에서 부여되지 않은 권한 있음")
                }
            }
    }


}
