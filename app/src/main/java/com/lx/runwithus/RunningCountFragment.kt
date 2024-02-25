package com.lx.runwithus

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.Manifest
import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentContainerView
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
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lx.fragment.OnFragmentRunningListener
import com.lx.runwithus.databinding.FragmentRunningCountBinding
import com.permissionx.guolindev.PermissionX
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.Timer
import java.util.TimerTask
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


class RunningCountFragment : Fragment(), SensorEventListener {

    var locationClient: FusedLocationProviderClient? = null

    var locationRequest: LocationRequest? = null

    lateinit var runMap: GoogleMap


    var index: Boolean? = null
    var index2 = false

    var mediaPlayer: MediaPlayer? = null
    var myMarker: Marker? = null
    var formattedTotalDistance: String? = null
    var caloriesBurned: Double? = null
    var formattedVelocity: String? = null

    var alertedDialog: AlertDialog? = null
    var count = 1
    var last_x: Float = 0f
    var last_y: Float = 0f
    var last_z: Float = 0f
    var lastTime: Long = 0

    var index3: Boolean? = null

    var runningActivity: RunningActivity? = null

    private lateinit var chronometer: Chronometer

    private var previousLocation: Location? = null

    var isDrawingEnabled = false

    var totalDistance = 0.1

    var isRunning = false

    private var startTime: Long = 0L // 스탑워치 시작 시간
    private var elapsedTime: Long = 0L // 스탑워치 경과 시간

    private var _binding: FragmentRunningCountBinding? = null
    val binding get() = _binding!!
    var timer = Handler(Looper.getMainLooper())
    var timer2 = Handler(Looper.getMainLooper())
    var timer3 = Handler(Looper.getMainLooper())
    var timer4 = Handler(Looper.getMainLooper())
    var timer5 = Handler(Looper.getMainLooper())

    lateinit var sensorManager: SensorManager
    lateinit var vibrator: Vibrator
    var sensorAccel: Sensor? = null

    var listener: OnFragmentRunningListener? = null

    // 프로그래스 바
    private lateinit var progressBar: ProgressBar
    private var maxProgress = 100 // 원하는 최대 프로그래스 값
    private var updateInterval = 1000 // 업데이트 간격 (1초)
    private var progressIncrement = maxProgress.toFloat() / 60 // 1분(60초) 동안 증가할 프로그래스 값

    private val handler = Handler()

    val mainLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        runningActivity = activity as RunningActivity

        if (activity is OnFragmentRunningListener) {
            listener = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRunningCountBinding.inflate(inflater, container, false)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.siren)

        handler.postDelayed(velocityRunnable, 10000)
        index2 = false


        if (AppData.grade != "크루 리더") {
            binding.stopButton.visibility = View.INVISIBLE
        }

        if (AppData.backDistance != null) {
            totalDistance = AppData.backDistance!!
            binding.distance.text = totalDistance.toString() + " m"
        }





        sensorManager = context?.getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        sensorManager.registerListener(this, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)

        progressBar = binding.progressBar
        syncro()
        startCount()
        startStopwatch()
        emergencySyncro()
        accidentSyncro()
        stopSyncro()

        initPermission()

        initMap()

        sirenReload()

        return binding.root
    }

    private val velocityRunnable: Runnable = object : Runnable {
        override fun run() {
            velocity()
            handler.postDelayed(this, 10000)
        }
    }

    override fun onDestroyView() {
        locationRequest = null
        timer.removeCallbacksAndMessages(null)
        timer2.removeCallbacksAndMessages(null)
        timer3.removeCallbacksAndMessages(null)
        timer4.removeCallbacksAndMessages(null)
        timer5.removeCallbacksAndMessages(null)
        handler.removeCallbacks(velocityRunnable)
        super.onDestroyView()
    }

    fun syncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("crewId", AppData.crewId).whereEqualTo("grade", "크루 리더").get().addOnSuccessListener {
                data ->
            for (datum in data) {

                val ready = datum?.get("ready") as Boolean
                if (!ready && datum?.get("userId") != AppData.id) {
                    AppData.distance = null
                    AppData.kcal = null
                    AppData.velocity = null
                    AppData.backDistance = null
                    AppData.time = null
                    getReady(false)
                    mediaPlayer?.stop()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                    sensorManager.unregisterListener(this, sensorAccel)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    timer4.removeCallbacksAndMessages(null)
                    timer5.removeCallbacksAndMessages(null)
                    val mainIntent = Intent(context, CrewActivity::class.java)
                    //mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(mainIntent)

                }

                timer.postDelayed(::syncro, 1000)
                if (!ready && datum?.get("userId") != AppData.id) {
                    mediaPlayer?.stop()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                    sensorManager.unregisterListener(this, sensorAccel)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    timer4.removeCallbacksAndMessages(null)
                    timer5.removeCallbacksAndMessages(null)
                }
            }
        }
    }

    fun emergencySyncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get()
            .addOnSuccessListener { data ->
                if (data?.get("emergency") as Boolean && index == null) {
                    accidentOff()
                    sensorManager.unregisterListener(this, sensorAccel)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    timer4.removeCallbacksAndMessages(null)
                    timer5.removeCallbacksAndMessages(null)
                    vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            1000,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                    mediaPlayer?.stop()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                    listener?.onFragmentChanged(RunningFragmentType.AED)
                }

                timer2.postDelayed(::emergencySyncro, 2000)
                if (data?.get("emergency") as Boolean) {
                    mediaPlayer?.stop()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                    sensorManager.unregisterListener(this, sensorAccel)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    timer4.removeCallbacksAndMessages(null)
                    timer5.removeCallbacksAndMessages(null)
                }

            }
    }

    fun stopSyncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get()
            .addOnSuccessListener { data ->
                if (data?.get("stop") as Boolean && AppData.grade != "크루 리더") {
                    if (isDrawingEnabled) {
                        isDrawingEnabled = false
                    }
                    captureMapAndSave()
                    mediaPlayer?.stop()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                    sensorManager.unregisterListener(this, sensorAccel)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    timer4.removeCallbacksAndMessages(null)
                    timer5.removeCallbacksAndMessages(null)
                    AppData.distance = "${formattedTotalDistance} m"
                    AppData.kcal = "${caloriesBurned}"
                    AppData.velocity = "${formattedVelocity}"
                    AppData.time = binding.chronometer.base

                    listener?.onFragmentChanged(RunningFragmentType.RUNNINGHOME)
                }

                timer5.postDelayed(::stopSyncro, 2000)
                if (data?.get("stop") as Boolean) {
                    mediaPlayer?.stop()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                    sensorManager.unregisterListener(this, sensorAccel)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    timer4.removeCallbacksAndMessages(null)
                    timer5.removeCallbacksAndMessages(null)

                }

            }
    }


    fun getReady(index: Boolean) {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("userId", AppData.id)
            .whereEqualTo("crewId", AppData.crewId).get().addOnSuccessListener { data ->
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

    fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.runMap) as? SupportMapFragment
        mapFragment?.getMapAsync {
            // 지도가 초기화되어 보일 수 있는 시점
            runMap = it
            isDrawingEnabled = true
            requestLocation()

            binding.stopButton.setOnClickListener {
                mediaPlayer?.stop()
                alertedDialog?.dismiss()
                alertedDialog = null
                index3 = true
                if (isDrawingEnabled) {
                    isDrawingEnabled = false
                }
                captureMapAndSave()
                AppData.distance = "${formattedTotalDistance}"
                AppData.kcal = "${caloriesBurned}"
                AppData.velocity = "${formattedVelocity}"
                AppData.time = binding.chronometer.base

                allStop()

                listener?.onFragmentChanged(RunningFragmentType.RUNNINGHOME)

//                if (isDrawingEnabled) {
//                    isDrawingEnabled = false
//                    showToast("러닝이 일시중지됩니다.")
//                    stopStopwatch()
//                } else {
//                    isDrawingEnabled = true
//                    startStopwatch()
//                }
            }

//            binding.stopButton.setOnLongClickListener {
//
//
//                true
//            }


        }
    }

    fun allStop() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).update("stop", true)
            .addOnSuccessListener {
            }
    }

    fun requestLocation() {
        // 위치 클라이언트 만들기
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        try {
            // 내 현재 위치 요청하기
            locationRequest = LocationRequest.create()
            locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest?.interval = 1000

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for (location in result.locations) {
                        if (isDrawingEnabled) {
                            previousLocation?.let { drawLineBetweenLocations(it, location) }
                        }


                        showCurrentLocation(location)
                        previousLocation = location  // 현재 위치를 이전 위치로 저장
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

    fun drawLineBetweenLocations(previousLocation: Location, currentLocation: Location) {
        val previousLatLng = LatLng(previousLocation.latitude, previousLocation.longitude)
        val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)

        // 선을 그릴 PolylineOptions 설정
        val lineOptions = PolylineOptions().add(previousLatLng).add(currentLatLng).width(15f).color(
            Color.GREEN
        ).geodesic(true)

        // Polyline 그리기
        runMap.addPolyline(lineOptions)

        totalDistance = addLocation(previousLocation, currentLocation, totalDistance)

    }

    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        runMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))

        showMarker(curPoint)
    }

    fun showMarker(curPoint: LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("내위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin1))

            myMarker = runMap.addMarker(it)
            myMarker?.tag = "1001"
        }
    }

    fun initPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grandtedList, deniedList ->
                if (allGranted) {
                    showToast("모든 위험권한 부여됨")
                } else {
                    showToast("위험권한 중에서 부여되지 않은 권한 있음")
                }
            }
    }

    fun startStopwatch() {
        if (!isRunning) {
            binding.stopButton.setImageResource(R.drawable.baseline_pause_circle_24)
            if (elapsedTime == 0L && AppData.time == null) {
                binding.chronometer.base = SystemClock.elapsedRealtime()

            } else {
                binding.chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
            }

            if (AppData.time != null) {
                binding.chronometer.base = AppData.time!!
            }
            binding.chronometer.start()

            isRunning = true
            startTime = SystemClock.elapsedRealtime()
        }
    }


    fun stopStopwatch() {
        if (isRunning) {
            binding.chronometer.stop()
            isRunning = false
            elapsedTime += (SystemClock.elapsedRealtime() - startTime)
            binding.stopButton.setImageResource(R.drawable.baseline_play_circle_24)
        }
    }

//    fun resetStopwatch() {
//        binding.chronometer.base = SystemClock.elapsedRealtime()
//        stopStopwatch()
//    }

    // 일시정지 후 돌아왔을 때 기존 게이지바 유지
    fun startCount() {
        // Runnable을 사용하여 프로그래스 바를 주기적으로 업데이트
        val runnable = object : Runnable {
            override fun run() {
                val appData = AppData.getInstance()

                if (appData.progress < maxProgress) {
                    appData.progress += progressIncrement.toInt()
                    progressBar.progress = appData.progress
                    handler.postDelayed(this, updateInterval.toLong())
                }
            }
        }
        handler.post(runnable)
    }

    fun calculateDistance(previousLocation: Location, currentLocation: Location): Double {
        val lat1 = Math.toRadians(previousLocation.latitude)
        val lon1 = Math.toRadians(previousLocation.longitude)
        val lat2 = Math.toRadians(currentLocation.latitude)
        val lon2 = Math.toRadians(currentLocation.longitude)

        val dlon = lon2 - lon1
        val dlat = lat2 - lat1

        val a = sin(dlat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dlon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        // Radius of the Earth in meters
        val radius = 6371000.0

        // Calculate the distance
        val distance = radius * c

        return distance
    }

    fun addLocation(
        previousLocation: Location?,
        currentLocation: Location?,
        totalDistance: Double
    ): Double {
        if (previousLocation != null && currentLocation != null) {
            val distance = calculateDistance(previousLocation, currentLocation)
            val newTotalDistance = totalDistance + distance
            formattedTotalDistance = String.format("%.1f", newTotalDistance)
            binding.distance.text = "${formattedTotalDistance}"

            caloriesBurned = kcal(newTotalDistance)
            binding.kcal.text = "${caloriesBurned}"
            return newTotalDistance
        }
        return totalDistance
    }

    fun kcal(distanceInMeters: Double): Double {

        val averageStepsPerMeter = 1.0 / 7.0

        val userWeight = 70.0

        val steps = distanceInMeters * averageStepsPerMeter

        val calories = String.format("%.1f", steps * userWeight * 0.04).toDouble()

        return calories

    }

    fun velocity() {
        val currentTime = SystemClock.elapsedRealtime()
        val elapsedTimeInSeconds = (currentTime - startTime) / 1000.0 // 밀리초를 초로 변환

        if (elapsedTimeInSeconds > 0) {
            val currentVelocity = totalDistance / elapsedTimeInSeconds
            val pace = 1000 / currentVelocity
            val paceMinute = (pace / 60).toInt()
            val paceSecond = (pace - (paceMinute * 60)).toInt()
            formattedVelocity = "$paceMinute\'$paceSecond\" "

            binding.velocity.text = "${formattedVelocity}"

            if(paceMinute >= 100) {
                binding.velocity.text = "100+"
            }

        }
    }

    fun captureMapAndSave() {
        var imageName = "map_capture_${System.currentTimeMillis()}"
        AppData.resultPicture = imageName + ".png"
        if (previousLocation != null) {
            runMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        previousLocation!!.latitude,
                        previousLocation!!.longitude
                    ), 15.5f
                )
            )
            myMarker?.remove()
            runMap.snapshot {
                it?.let {
                    saveImageToGallery(it, imageName, "Map Capture")
                }
            }
        } else {
            showToast("너무 빠르게 누르셨습니다")
        }
    }

    fun saveImageToGallery(bitmap: Bitmap, title: String, description: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, title)
            put(MediaStore.Images.Media.DESCRIPTION, description)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        }

        val resolver = requireContext().contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)


        uri?.let {
            val outputStream = resolver.openOutputStream(it)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }





            outputStream?.close()


            val inputStream = resolver.openInputStream(it)

            var storageR = Firebase.storage("gs://fair-kingdom-401602.appspot.com")
                .getReference("runningCapture")

            val mediaName = storageR.child("${title}.png")


            mediaName.putStream(inputStream!!).addOnFailureListener {
                println(it)
                println("실패")
            }.addOnSuccessListener {
                println("성공")

            }


            // 갤러리 미디어 스캔
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = uri

            requireContext().sendBroadcast(mediaScanIntent)

            showToast("이미지가 갤러리에 저장되었습니다.")
        } ?: showToast("이미지 저장 중 오류가 발생했습니다.")
    }

    @SuppressLint("MissingInflatedId")
    override fun onSensorChanged(event: SensorEvent) {


        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val axisX: Float = event.values[0]
            val axisY: Float = event.values[1]
            val axisZ: Float = event.values[2]

            var curTime = System.currentTimeMillis()

            if ((curTime - lastTime) > 100) {

                var diffTime = curTime - lastTime
                lastTime = curTime

                var collision = Math.sqrt(
                    Math.pow((axisZ - last_z).toDouble(), 2.0) * 100
                            + Math.pow((axisX - last_x).toDouble(), 2.0) * 10
                            + Math.pow((axisY - last_y).toDouble(), 2.0) * 10
                ) / diffTime * 10000

                //println(axisX)
                //println(axisY)
                //println(axisZ)
                if (collision > 20000 && count == 1) {
                    count++
                    index = true

                    accidentOn()
                    getAccident()
                }
                last_x = axisX
                last_y = axisY
                last_z = axisZ
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun emergencyOn() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).update("emergency", true)
            .addOnSuccessListener {
                println("긴급 발동!")
            }
    }

    fun getAccident() {
        val mDialogView = LayoutInflater.from(runningActivity).inflate(R.layout.fall_dialog, null)
        val mBuilder = AlertDialog.Builder(runningActivity)
            .setView(mDialogView)

        if (alertedDialog == null) {
            mediaPlayer?.start()
            alertedDialog = mBuilder.show()
            alertedDialog?.setCanceledOnTouchOutside(false)
            alertedDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val okButton = mDialogView.findViewById<ImageView>(R.id.yesButton)
            okButton.setOnClickListener {
                AppData.distance = null
                AppData.kcal = null
                AppData.velocity = null
                AppData.backDistance = null
                AppData.time = null
                mediaPlayer?.stop()
                alertedDialog?.dismiss()
                alertedDialog = null
                sensorManager.unregisterListener(this, sensorAccel)
                emergencyOn()
                listener?.onFragmentChanged(RunningFragmentType.AED)


            }

            val noButton = mDialogView.findViewById<ImageView>(R.id.noButton)
            noButton.setOnClickListener {
                accidentOff()
                mediaPlayer?.pause()
                alertedDialog?.dismiss()
                alertedDialog = null

            }

        }
    }

    fun accidentOn() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).update("accident", true)
            .addOnSuccessListener {


            }
    }

    fun accidentOff() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).update("accident", false)
            .addOnSuccessListener {

            }
    }

    fun accidentSyncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get()
            .addOnSuccessListener { data ->

                if (data?.get("accident") as Boolean && index == null) {
                    index2 = true
                    vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            1000,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                    getAccident()

                } else if (index2) {
                    mediaPlayer?.pause()
                    alertedDialog?.dismiss()
                    alertedDialog = null
                } else {

                }

                timer3.postDelayed(::accidentSyncro, 1000)
            }
    }

    fun sirenReload() {
        index = null
        index2 = false
        count = 1
        timer4.postDelayed(::sirenReload, 3000)
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


}