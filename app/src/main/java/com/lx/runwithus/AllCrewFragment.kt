package com.lx.runwithus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.FragmentAddCrewBinding
import com.lx.runwithus.databinding.FragmentAllCrewBinding
import com.lx.runwithus.databinding.FragmentDetailedBinding
import layout.ItemSpacingDecoration


class AllCrewFragment : Fragment() {

    var _binding: FragmentAllCrewBinding? = null
    val binding get() = _binding!!

    lateinit var allCrewAdapter: AllCrewAdapter

    var listener: OnAllCrewClickListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnAllCrewClickListener) {
            listener = activity
        }

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentAllCrewBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        allCrewAdapter = AllCrewAdapter()
        binding.allCrewList.adapter = allCrewAdapter

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

        binding.allCrewList.layoutManager = LinearLayoutManager(requireContext())

        // 리사이클러뷰 아이템 클릭 리스너 설정
        allCrewAdapter.listener = object : OnAllCrewClickListener {
            override fun onItemClcik(
                holder: AllCrewAdapter.ViewHolder?,
                view: View?,
                position: Int
            ) {
                val item = allCrewAdapter.items.get(position)
                AppData.crewId = item.id.toString()
                val intent = Intent(context, CrewActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                AppData.where = "AllCrewFragment"
                startActivity(intent)

            }
        }

        getAllCrew()



        // 샘플데이터 추가
        //allCrewAdapter.items.add(Crew("크루1", "서울특별시", "강남구", 50, "", "", "", "@drawable/greybackground.png"))

    }

    fun getAllCrew() {
        val databaseR = Firebase.firestore
        var imageNum = 0
        databaseR.collection("crews").get().addOnSuccessListener {
                documents ->
            for (document in documents) {
                println("크루 불러오기")
                var memberlist = document.get("member") as ArrayList<String>
                imageNum++

                allCrewAdapter.items.add(Crew(name = document.get("name").toString(), city = document.get("city").toString(),gu = document.get("gu").toString(), memberPop = memberlist.size, id = document.id, crewPhoto = document.get("photo").toString()))
                allCrewAdapter.notifyDataSetChanged()
            }


        }.addOnFailureListener{
                e ->
            Log.d(tag, "실패", e)
            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
        }
    }



}