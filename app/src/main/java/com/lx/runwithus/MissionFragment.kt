package com.lx.runwithus

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.runwithus.databinding.FragmentMissionBinding


class MissionFragment : Fragment() {

    var _binding: FragmentMissionBinding? = null
    val binding get() = _binding!!

    lateinit var missionListAdapter: MissionListAdapter

    lateinit var missionInItemAdapter: MissionInItemAdapter

    val missionInItemList = mutableListOf<Mission_in_item>()

    var listener: OnMissionListClickListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnMissionListClickListener) {
            listener = activity
        }

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMissionBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        missionListAdapter = MissionListAdapter()
        binding.missionJoin.adapter = missionListAdapter

        missionInItemAdapter = MissionInItemAdapter(missionInItemList)
        binding.missionIn.adapter = missionInItemAdapter

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

        binding.missionJoin.layoutManager = LinearLayoutManager(requireContext())
        missionListAdapter.listener = object : OnMissionListClickListener {
            override fun onItemClick(
                holder: MissionListAdapter.ViewHolder?,
                view: View?,
                position: Int
            ) {
                showPopup(position)
            }

        }
        missionListAdapter.items.add(
            Mission_list(
                "11월 주간 챌린지",
                "이번 주 15km를 달려보세요",
                R.drawable.challenge15,
                "7일 남음"
            )
        )

        missionListAdapter.items.add(
            Mission_list(
                "11월 주간 챌린지",
                "이번 주 10km를 달려보세요",
                R.drawable.challenge10,
                "7일 남음"
            )
        )

        missionListAdapter.items.add(
            Mission_list(
                "11월 주간 챌린지",
                "이번 주 5km를 달려보세요",
                R.drawable.challenge5,
                "7일 남음"
            )
        )

        missionListAdapter.items.add(
            Mission_list(
                "11월 월간 챌린지",
                "이번 달 100km를 달려보세요",
                R.drawable.challenge100,
                "12일 남음"
            )
        )

        missionListAdapter.items.add(
            Mission_list(
                "11월 월간 챌린지",
                "이번 달 25km를 달려보세요",
                R.drawable.challenge25,
                "12일 남음"
            )
        )

        missionListAdapter.items.add(
            Mission_list(
                "11월 월간 챌린지",
                "이번 달 30km를 달려보세요",
                R.drawable.challenge30,
                "12일 남음"
            )
        )

        missionListAdapter.notifyDataSetChanged()

    }

    private fun showPopup(position: Int) {
        println(AppData.grade)
        if (AppData.grade == "크루 리더") {
            val mission = missionListAdapter.items[position]

            // 사용자 정의 레이아웃을 인플레이트합니다.
            val customLayout = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_layout, null)

            // 사용자 정의 레이아웃에서 ImageView를 찾습니다.
            val imageView: ImageView = customLayout.findViewById(R.id.dialogImageView)

            // ImageView에 이미지 리소스를 설정합니다.
            mission.missionImg?.let { imageView.setImageResource(it) }

            // AlertDialog.Builder 생성 시에 스타일을 적용합니다.
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(customLayout)
            builder.setTitle(mission.title)
            builder.setMessage(mission.info)

            // '등록하기' 버튼의 동작을 설정합니다.
            builder.setPositiveButton("등록하기") { dialog, _ ->

                val newItem = Mission_in_item(
                    mission.missionImg
                )
                missionInItemList.add(newItem)
                missionInItemAdapter.notifyDataSetChanged()

                dialog.dismiss()
            }

            val alertDialog: AlertDialog = builder.create()

            // AlertDialog의 배경을 설정합니다.
            alertDialog.window?.setBackgroundDrawableResource(R.drawable.custom_dialog_background)

            alertDialog.show()

        } else if (AppData.grade == "크루 멤버"){
            Toast.makeText(context, "챌린지는 크루 리더만 등록할 수 있습니다", Toast.LENGTH_SHORT).show()
        }

    }


}