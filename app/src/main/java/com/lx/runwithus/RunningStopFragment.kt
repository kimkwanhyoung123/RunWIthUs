package com.lx.runwithus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lx.fragment.OnFragmentRunningListener
import com.lx.runwithus.databinding.FragmentRunningStopBinding


class RunningStopFragment : Fragment() {
    lateinit var _binding: FragmentRunningStopBinding
    val binding get() = _binding
    var listener : OnFragmentRunningListener? = null
    var timer = Handler(Looper.getMainLooper())
    var timer2 = Handler(Looper.getMainLooper())
    var timer3 = Handler(Looper.getMainLooper())


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentRunningListener) {
            listener = activity
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRunningStopBinding.inflate(inflater, container, false)



        syncro()
        resumeSyncro()

        if (AppData.grade != "크루 리더") {
            binding.goingBack.visibility = View.INVISIBLE
            binding.goingEnd.visibility = View.INVISIBLE
        }



        if (AppData.distance == "null m") {
            binding.resultDistance.text = "0.0 m"
        } else {
            binding.resultDistance.text = AppData.distance
        }
        if (AppData.kcal == "null") {
            binding.resultKcal.text = "0"
        } else {
            binding.resultKcal.text = AppData.kcal
        }
        if (AppData.velocity == "null") {
            binding.resultVelocity.text = "0"
        } else {
            binding.resultVelocity.text = AppData.velocity
        }

        binding.goingEnd.setOnClickListener {
            timer.removeCallbacksAndMessages(null)
            timer2.removeCallbacksAndMessages(null)
            timer3.removeCallbacksAndMessages(null)
            getReady(false)
            addRun()
            AppData.distance = null
            AppData.kcal = null
            AppData.velocity = null
            AppData.backDistance = null
            AppData.time = null
            allresume()
            val mainIntent = Intent(context, CrewActivity::class.java)
            //mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(mainIntent)
        }

        binding.goingBack.setOnClickListener {
            allresume()
            AppData.backDistance = binding.resultDistance.text?.replace("[^0-9, ^.]".toRegex(), "")?.toDouble()

            listener?.onFragmentChanged(RunningFragmentType.RUNNINGCOUNT)
        }

        loadPicture()



        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        timer.removeCallbacksAndMessages(null)
        timer2.removeCallbacksAndMessages(null)
        timer3.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    fun syncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("crewId", AppData.crewId).whereEqualTo("grade", "크루 리더").get().addOnSuccessListener {
                data ->
            for (datum in data) {

                val ready = datum.get("ready") as Boolean
                if (!ready && datum.get("userId") != AppData.id) {
                    AppData.distance = null
                    AppData.kcal = null
                    AppData.velocity = null
                    AppData.backDistance = null
                    AppData.time = null
                    getReady(false)
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                    val mainIntent = Intent(context, CrewActivity::class.java)
                    //mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(mainIntent)

                }

                timer3.postDelayed(::syncro, 1000)
                if (!ready && datum.get("userId") != AppData.id) {
                    timer.removeCallbacksAndMessages(null)
                    timer2.removeCallbacksAndMessages(null)
                    timer3.removeCallbacksAndMessages(null)
                }
            }
        }
    }

    fun resumeSyncro() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get().addOnSuccessListener {
                data ->
            var stop:Boolean? = null
            if (data.get("stop") != null) {
                stop = data.get("stop") as Boolean
                if(stop) {
                } else {
                    AppData.backDistance = binding.resultDistance.text?.replace("[^0-9, ^.]".toRegex(), "")?.toDouble()

                    listener?.onFragmentChanged(RunningFragmentType.RUNNINGCOUNT)
                }
            }


            timer2.postDelayed(::resumeSyncro, 3000)
            if (data.get("stop") as Boolean) {
            } else {
                timer.removeCallbacksAndMessages(null)
                timer2.removeCallbacksAndMessages(null)
                timer3.removeCallbacksAndMessages(null)
            }

        }
    }

    fun loadPicture() {
        var storage = Firebase.storage("gs://fair-kingdom-401602.appspot.com").getReference("runningCapture")
        if (AppData.resultPicture != null) {
            storage.child(AppData.resultPicture!!).downloadUrl.addOnFailureListener {
                println(it)
                println("실패다잇")
                Glide.with(binding.imageView5).load(R.drawable.image_resize11).into(binding.imageView5)
            }.addOnSuccessListener {
                    image ->
                println("성공이다잇")
                Glide.with(binding.imageView5).load(image).into(binding.imageView5)
            }
        } else {
            println("앱데이터 문제다잇")
        }

        timer.postDelayed(::loadPicture, 1000)

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

    fun addRun() {
        val databaseR = Firebase.firestore
        var count = 0
        databaseR.collection("records").whereEqualTo("crewId", AppData.crewId).whereEqualTo("ready", true).get().addOnSuccessListener {
                data ->
            for (datum in data) {
                count++
            }
        }
        val runRecord = hashMapOf(
            "distance" to AppData.distance,
            "photo" to AppData.resultPicture,
            "date" to Timestamp.now(),
            "crewId" to AppData.crewId,
            "pop" to count,
            "velocity" to AppData.velocity
        )

        if (AppData.grade == "크루 리더") {
            databaseR.collection("run_records").add(runRecord).addOnSuccessListener {

            }
        }

    }

    fun allresume() {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).update("stop", false).addOnSuccessListener {
        }
    }



}