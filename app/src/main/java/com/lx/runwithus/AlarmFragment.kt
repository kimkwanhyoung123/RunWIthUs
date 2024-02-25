package com.lx.runwithus

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.FragmentAlarmBinding
import com.lx.runwithus.databinding.FragmentMissionBinding


class AlarmFragment : Fragment() {

    var _binding: FragmentAlarmBinding? = null
    val binding get() = _binding!!

    lateinit var alarmAdapter: AlarmAdapter

    var listener: OnAlarmClickListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnAlarmClickListener) {
            listener = activity
        }

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        alarmAdapter = AlarmAdapter()
        binding.alarmList.adapter = alarmAdapter

        initList()


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

    fun initList() {

        binding.alarmList.layoutManager = LinearLayoutManager(requireContext())
        alarmAdapter.listener = object : OnAlarmClickListener {
            override fun onItemClick(
                holder: AlarmAdapter.ViewHolder?,
                view: View?,
                position: Int
            ) {
            }

        }

        getAlarmList()

    }

    fun getAlarmList() {
        val databaseR = Firebase.firestore
        databaseR.collection("users").document(AppData.id.toString()).get().addOnSuccessListener {
                data ->
            var city = data.get("city").toString()
            var gu = data.get("gu").toString()
            indexAlarm(city, gu)
        }
    }

    fun indexAlarm(city:String, gu:String) {

        var num = 0

        val databaseR = Firebase.firestore
        databaseR.collection("alarms").whereEqualTo("city", city).whereEqualTo("gu", gu).get().addOnSuccessListener {
                data ->
            for (datum in data) {
                num++
                var title = datum.get("crewName").toString() + " 에서 일정을 등록하였습니다."
                var content = "날짜 : " + datum.get("dateString").toString()
                alarmAdapter.items.add(Alarm(title,content,num, datum.get("crewName").toString()))
                alarmAdapter.notifyDataSetChanged()
            }

        }
    }
}