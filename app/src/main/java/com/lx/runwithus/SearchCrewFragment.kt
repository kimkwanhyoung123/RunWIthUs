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
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.FragmentAddCrewBinding
import com.lx.runwithus.databinding.FragmentAllCrewBinding
import com.lx.runwithus.databinding.FragmentDetailedBinding
import com.lx.runwithus.databinding.FragmentSearchCrewBinding
import layout.ItemSpacingDecoration


class SearchCrewFragment : Fragment() {

    var _binding: FragmentSearchCrewBinding? = null
    val binding get() = _binding!!

    var memberlist: ArrayList<String>? = null

    lateinit var searchCrewAdapter: SearchCrewAdapter

    var listener: OnSearchCrewClickListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnSearchCrewClickListener) {
            listener = activity
        }

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSearchCrewBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        searchCrewAdapter = SearchCrewAdapter()

        binding.searchCrewList.adapter = searchCrewAdapter
        binding.searchCrewList.layoutManager = LinearLayoutManager(requireContext())

        // 리사이클러뷰 아이템 클릭 리스너 설정
        searchCrewAdapter.listener = object : OnSearchCrewClickListener {

            override fun onItemClcik(
                holder: SearchCrewAdapter.ViewHolder?,
                view: View?,
                position: Int
            ) {
                val item = searchCrewAdapter.items.get(position)
                AppData.crewId = item.id.toString()
                val intent = Intent(context, CrewActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
        }

        //searchCrewAdapter.items.add(SearchCrew("크루2", "서울특별시", "강남구", 50, "", "", "", "@drawable/greybackground.png"))


        binding.searchCrew.addTextChangedListener {
            memberlist?.clear()
            searchCrewList(binding.searchCrew.text.toString())
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

    fun searchCrewList(text: String) {
        val databaseR = Firebase.firestore
        var searchNum = 0
        databaseR.collection("crews").get().addOnSuccessListener {
                documents ->
            for (document in documents) {
                val name = document.get("name").toString()

                searchNum++

                if (name.startsWith(text)) {
                    var memberlist = document.get("member") as ArrayList<String>
                    searchCrewAdapter.items.add(SearchCrew(name = document.get("name").toString(), city = document.get("city").toString(),gu = document.get("gu").toString(), memberPop = memberlist.size, id = document.id, crewPhoto = document.get("photo").toString()))
                    searchCrewAdapter.notifyDataSetChanged()
                }

            }

        }.addOnFailureListener{
                e ->
            Log.d(tag, "실패", e)
            Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
        }





    }

}