package com.lx.runwithus

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentRunningListener
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.FragmentDetailedBinding
import com.lx.runwithus.databinding.FragmentStatisticsBinding


class DetailedFragment : Fragment() {

    lateinit var _binding: FragmentDetailedBinding
    val binding get() = _binding

    var listener: OnFragmentSignUpListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentSignUpListener) {
            listener = activity
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedBinding.inflate(inflater, container, false)

        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get().addOnSuccessListener {
            data ->
            var image = data?.get("photo")?.toString()
            if (image == null) {
                image = "1"
            }
            if(image.toInt() == 1) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage1).into(binding.detailedCrewImage)
            } else if(image.toInt() == 2) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage2).into(binding.detailedCrewImage)
            } else if(image.toInt()== 3) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage3).into(binding.detailedCrewImage)
            } else if(image.toInt()== 4) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage4).into(binding.detailedCrewImage)
            } else if(image.toInt()== 5) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage5).into(binding.detailedCrewImage)
            } else if(image.toInt()== 6) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage6).into(binding.detailedCrewImage)
            } else if(image.toInt()== 7) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage7).into(binding.detailedCrewImage)
            } else if(image.toInt()== 8) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage8).into(binding.detailedCrewImage)
            } else if(image.toInt()== 9) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage9).into(binding.detailedCrewImage)
            } else if(image.toInt()== 10) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage10).into(binding.detailedCrewImage)
            } else if(image.toInt()== 11) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage11).into(binding.detailedCrewImage)
            } else if(image.toInt()== 12) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage12).into(binding.detailedCrewImage)
            } else if(image.toInt()== 13) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage13).into(binding.detailedCrewImage)
            } else if(image.toInt()== 14) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage14).into(binding.detailedCrewImage)
            } else if(image.toInt()== 15) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage15).into(binding.detailedCrewImage)
            } else if(image.toInt()== 16) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage16).into(binding.detailedCrewImage)
            } else if(image.toInt()== 17) {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage17).into(binding.detailedCrewImage)
            }  else {
                Glide.with(binding.detailedCrewImage).load(R.drawable.crewimage0).into(binding.detailedCrewImage)
            }

        }



        initCrewIndex()

        initCrewGrade()

        binding.statisticsPageButton.paintFlags = (binding.statisticsPageButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
        binding.statisticsPageButton.setTextColor(Color.parseColor("#008f8c"))

        listener?.onFragmentChanged(FragmentType.STATISTICS)

        binding.statisticsPageButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.STATISTICS)

            // 텍스트에 밑줄 및 색상 추가
            binding.statisticsPageButton.paintFlags = (binding.statisticsPageButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
            binding.statisticsPageButton.setTextColor(Color.parseColor("#008f8c"))

            // missionPageButton에서 밑줄 제거
            binding.missionPageButton.paintFlags = (binding.missionPageButton.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv())
            binding.missionPageButton.setTextColor(Color.BLACK) // 기본 색상으로 설정
        }

        binding.missionPageButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.MISSION)

            // 텍스트에 밑줄 및 색상 추가
            binding.missionPageButton.paintFlags = (binding.missionPageButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
            binding.missionPageButton.setTextColor(Color.parseColor("#008f8c"))

            // statisticsPageButton에서 밑줄 제거
            binding.statisticsPageButton.paintFlags = (binding.statisticsPageButton.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv())
            binding.statisticsPageButton.setTextColor(Color.BLACK) // 기본 색상으로 설정
        }

        binding.signupCrewButton.setOnClickListener {
            signupCrew()
            val intent = Intent(context, MainActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(context, "가입되었습니다", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    fun initCrewGrade() {
        val databaseR = Firebase.firestore
        databaseR.collection("run_records").whereEqualTo("crewId", AppData.crewId).count().get(AggregateSource.SERVER).addOnCompleteListener {
            task ->
            val count = task.result.count
            if(count < 10) {
                binding.gradeImage.setImageResource(R.drawable.grade_sliver)
            } else if (count < 20) {
                binding.gradeImage.setImageResource(R.drawable.grade_gold)
            } else {
                binding.gradeImage.setImageResource(R.drawable.grade_master)
            }
        }
    }

    fun signupCrew() {

        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get().addOnSuccessListener {
                document ->
            var code = 0
            var list : ArrayList<String>? = null
            if (document.get("member") != null) {
                list = document.get("member") as ArrayList<String>
            }

            for (s in list!!) {
                if (s == AppData.id) {
                    println("이미 같은 아이디가 있습니다")
                    code = 1
                    break
                }

            }
            if (code == 0) {
                list.add(AppData.id.toString())
                updateMember(document.id, list)
                addRecord(document.id, AppData.id!!,"크루 멤버")
                println(list)
                println("완료")
            }




        }.addOnFailureListener{
                exception -> Log.w(tag, "실패", exception)
        }
    }

    fun updateMember(id:String, list : ArrayList<String>) {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(id).update("member", list).addOnSuccessListener {
            println("성공")
        }.addOnFailureListener {
            println("실패")
        }
    }

    //크루 속 자신의 기록 생성
    fun addRecord(crewId:String, userId: String, leader: String) {
        val databaseR = Firebase.firestore
        val record = hashMapOf(
            "crewId" to crewId,
            "userId" to userId,
            "record" to 0,
            "grade" to leader,
            "point" to GeoPoint(38.0,127.0),
            "ready" to false,
            "photo" to "31"
        )
        databaseR.collection("records").add(record).addOnSuccessListener {

        }
    }


    fun initCrewIndex() {
        indexMember()
        val databaseR = Firebase.firestore
        databaseR.collection("crews").document(AppData.crewId.toString()).get().addOnSuccessListener {
                data ->
            AppData.crewName = data.get("name")?.toString()
            try {
                binding.detailedCrewName.text = data.get("name")?.toString()
                binding.detailedCrewCity.text = " " + data.get("city")?.toString() + " " + data.get("gu")?.toString()
                var explain = data.get("explanation")
                if (explain != null) {
                    binding.detailedCrewContent.text = explain.toString()
                }
            } catch(e:Exception) {
                e.printStackTrace()
            }




        }.addOnFailureListener{
                e ->
            Log.d(tag, "실패", e)
            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
        }

    }

    fun indexMember() {
        val databaseR = Firebase.firestore
        databaseR.collection("records").whereEqualTo("userId", AppData.id)
            .whereEqualTo("crewId", AppData.crewId).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    AppData.grade = document.get("grade")?.toString()
                    if (document.get("grade")?.toString() == "크루 리더") {
                        binding.signupCrewButton.visibility = View.INVISIBLE
                    } else if (document.get("grade")?.toString() == "크루 멤버") {
                        binding.signupCrewButton.visibility = View.INVISIBLE
                    } else {

                    }
                }



            }.addOnFailureListener { e ->
                Log.d(tag, "실패", e)
                Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
            }

    }

}