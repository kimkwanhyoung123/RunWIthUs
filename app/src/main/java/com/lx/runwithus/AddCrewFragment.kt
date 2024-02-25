package com.lx.runwithus

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.databinding.FragmentAddCrewBinding


class AddCrewFragment : Fragment() {

    lateinit var binding: FragmentAddCrewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddCrewBinding.inflate(inflater, container, false)

        Glide.with(binding.imageButton).load(com.lx.runwithus.R.drawable.crewimage0).into(binding.imageButton)

        val data = ArrayList<String>()
        data.add("운동 주기")
        data.add("주 1회")
        data.add("주 2회")
        data.add("주 3회")
        data.add("주 4회")
        data.add("주 5회")
        data.add("주 6회")
        data.add("주 7회")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, data)
        //binding.cycle.adapter = adapter
        // 스피너 초기화
        val spinner = binding.crewRunCycle
        spinner.adapter = adapter

        val data2 = ArrayList<String>()
        data2.add("운동 강도")
        data2.add("입문")
        data2.add("초급 (페이스 9 ~ 10분)")
        data2.add("중급 (페이스 7 ~ 8분)")
        data2.add("고급 (페이스 5 ~ 6분)")

        val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, data2)
        //binding.cycle.adapter = adapter
        // 스피너 초기화
        val spinner2 = binding.crewRunPower
        spinner2.adapter = adapter2

        val data3 = ArrayList<String>()
        data3.add("운동하고자 하는 위치(시)")
        data3.add("서울특별시")
        data3.add("부산광역시")
        data3.add("대구광역시")
        data3.add("인천광역시")
        data3.add("광주광역시")
        data3.add("대전광역시")
        data3.add("울산광역시")
        data3.add("세종특별자치시")
        data3.add("경기도")
        data3.add("강원특별자치도")
        data3.add("충청북도")
        data3.add("충청남도")
        data3.add("전라북도")
        data3.add("전라남도")
        data3.add("경상북도")
        data3.add("경상남도")
        data3.add("제주특별자치도")



        val adapter3 = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, data3)
        //binding.cycle.adapter = adapter
        // 스피너 초기화
        val spinner3 = binding.crewLocationCity
        spinner3.adapter = adapter3

        val data4 = ArrayList<String>()
        data4.add("운동하고자 하는 위치(구)")
        data4.add("종로구")
        data4.add("중구")
        data4.add("용산구")
        data4.add("성동구")
        data4.add("광진구")
        data4.add("동대문구")
        data4.add("중랑구")
        data4.add("성북구")
        data4.add("강북구")
        data4.add("도봉구")
        data4.add("노원구")
        data4.add("은평구")
        data4.add("서대문구")
        data4.add("마포구")
        data4.add("양천구")
        data4.add("강서구")
        data4.add("구로구")
        data4.add("금천구")
        data4.add("영등포구")
        data4.add("동작구")
        data4.add("관악구")
        data4.add("서초구")
        data4.add("강남구")
        data4.add("송파구")
        data4.add("강동구")

        val adapter4 = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, data4)
        //binding.cycle.adapter = adapter
        // 스피너 초기화
        val spinner4 = binding.crewLocationGu
        spinner4.adapter = adapter4


        spinner.setSelection(0, false)
        spinner2.setSelection(0, false)
        spinner3.setSelection(0, false)
        spinner4.setSelection(0, false)

        binding.createCrew.setOnClickListener{
            var crewName = binding.crewName.text.toString().trim()
            var crewExplain = binding.crewExplain.text.toString().trim()
            var crewLocationCity = spinner3.selectedItem.toString()
            var crewLocationGu = spinner4.selectedItem.toString()
            var crewPower = spinner2.selectedItem.toString()
            var crewCycle = spinner.selectedItem.toString()
            if (crewName.isNullOrEmpty() || crewExplain.isNullOrEmpty() || crewLocationCity == "운동하고자 하는 위치(시)" || crewLocationGu == "운동하고자 하는 위치(구)" ||
                crewPower == "운동 강도" || crewCycle == "운동 주기") {
                Toast.makeText(context,"입력하지 않은 곳이 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                addCrewToDatabase(crewName, crewLocationCity, crewLocationGu, crewExplain, AppData.id!!, crewPower, crewCycle)
                val intent = Intent(context, MainActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }


        }

        return binding.root
    }

    fun addCrewToDatabase(name: String, locationCity:String, locationGu:String, explain:String, id: String, power:String, cycle:String) {
        val crew = hashMapOf(
            "name" to name,
            "city" to locationCity,
            "gu" to locationGu,
            "explanation" to explain,
            "member" to mutableListOf(id),
            "total_distance" to 0.0,
            "total_time" to 0,
            "power" to power,
            "cycle" to cycle,
            "emergency" to false,
            "accident" to false,
            "stop" to false
        )

        val databaseR = Firebase.firestore
        databaseR.collection("crews").add(crew).addOnSuccessListener {
                documentReference ->
            Log.d(tag, "성공 : ${documentReference.id}")
            addRecord(documentReference.id.toString(), AppData.id!!, "크루 리더")
        }.addOnFailureListener{
                e ->
            Log.d(tag, "실패", e)
        }

    }

    fun addRecord(crewId:String, userId: String, leader: String) {
        val databaseR = Firebase.firestore
        val record = hashMapOf(
            "crewId" to crewId,
            "userId" to userId,
            "record" to 0,
            "grade" to leader,
            "point" to GeoPoint(38.0,127.0),
            "ready" to false,
            "photo" to "31",
        )
        databaseR.collection("records").add(record).addOnSuccessListener {

        }
    }

}