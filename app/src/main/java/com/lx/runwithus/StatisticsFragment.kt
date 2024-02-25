package com.lx.runwithus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.fragment.OnFragmentRunningListener
import com.lx.fragment.OnFragmentSignUpListener
import com.lx.runwithus.databinding.FragmentDetailedBinding
import com.lx.runwithus.databinding.FragmentRunningBinding
import com.lx.runwithus.databinding.FragmentStatisticsBinding
import java.util.concurrent.TimeUnit


class StatisticsFragment : Fragment() {

    val databaseR = Firebase.firestore
    var memberList: ArrayList<String>? = null

    lateinit var statisticsRecordAdapter: StatisticsRecordAdapter

    lateinit var _binding: FragmentStatisticsBinding
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



        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)




        initRecordList()

        initRankList()

        initRunningRecordList()



        binding.rankingListButton.setOnClickListener {
            var intent = Intent(requireActivity(), RankingActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()


        listener = null
    }



    fun initRecordList() {
        databaseR.collection("crews").document(AppData.crewId.toString()).get().addOnSuccessListener {
                document ->
            try {
                if (document.get("member") != null) {
                    memberList = document.get("member") as ArrayList<String>
                }

                binding.totalMember.text = memberList?.size.toString() + "명"
                var totalDistance = document.get("total_distance")
                if (totalDistance != null) {
                    binding.totalDistance.text = totalDistance.toString() + " km"
                } else {
                    binding.totalDistance.text = "0 km"
                }

                var totalTime = document.get("total_time")
                if (totalTime != null) {
                    binding.totalTime.text = totalTime.toString() + " 시간"
                } else {
                    binding.totalTime.text = "0 시간"
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

    fun initRankList() {

        databaseR.collection("records").orderBy("record", Query.Direction.DESCENDING).get().addOnSuccessListener {
                data ->
            var rank = 1

            for (datum in data) {

                if (datum.get("crewId").toString() == AppData.crewId) {

                    if (rank == 1) {
                        binding.firstRecordOutput?.text = datum.get("record").toString() + "km"

                    } else if (rank == 2) {
                        binding.secondRecordOutput?.text = datum.get("record").toString() + "km"
                    } else if (rank == 3) {
                        binding.thirdRecordOutput?.text = datum.get("record").toString() + "km"
                    }

                    if (rank < 4) {
                        findName(datum.get("userId").toString(), rank)
                    }

                    rank++
                }


            }
        }

    }

    fun findName(userId: String, rank: Int) {

        databaseR.collection("users").document(userId).get().addOnSuccessListener {
                data ->
            if(rank == 1) {
                binding.firstNameOutput?.text = data.get("nickname").toString()
            } else if(rank == 2) {
                binding.secondNameOutput?.text = data.get("nickname").toString()

            } else if (rank == 3) {
                binding.thirdNameOutput?.text = data.get("nickname").toString()
            }

            var picture = data.get("photo").toString()
            changePicture(rank, picture)
        }

    }

    fun changePicture(rank:Int, photo:String) {

       var picture = binding.thirdPicture
        if (rank == 1) {
             picture = binding.firstPicture
        } else if(rank == 2) {
             picture = binding.secondPicture
        } else if(rank == 3) {
             picture = binding.thirdPicture
        } else {

        }

        if (photo?.toInt() == 1) {
            Glide.with(picture).load(R.drawable.face1).into(picture)
        } else if(photo?.toInt() == 2) {
            Glide.with(picture).load(R.drawable.face2).into(picture)
        } else if(photo?.toInt() == 3) {
            Glide.with(picture).load(R.drawable.face3).into(picture)
        } else if(photo?.toInt() == 4) {
            Glide.with(picture).load(R.drawable.face4).into(picture)
        } else if(photo?.toInt() == 5) {
            Glide.with(picture).load(R.drawable.face5).into(picture)
        } else if(photo?.toInt() == 6) {
            Glide.with(picture).load(R.drawable.face6).into(picture)
        } else if(photo?.toInt() == 7) {
            Glide.with(picture).load(R.drawable.face7).into(picture)
        } else if(photo?.toInt() == 8) {
            Glide.with(picture).load(R.drawable.face8).into(picture)
        } else if(photo?.toInt() == 9) {
            Glide.with(picture).load(R.drawable.face9).into(picture)
        } else if(photo?.toInt() == 10) {
            Glide.with(picture).load(R.drawable.face10).into(picture)
        } else if(photo?.toInt() == 11) {
            Glide.with(picture).load(R.drawable.face11).into(picture)
        } else if(photo?.toInt() == 12) {
            Glide.with(picture).load(R.drawable.face12).into(picture)
        } else if(photo?.toInt() == 13) {
            Glide.with(picture).load(R.drawable.face13).into(picture)
        } else if(photo?.toInt() == 14) {
            Glide.with(picture).load(R.drawable.face14).into(picture)
        } else if(photo?.toInt() == 15) {
            Glide.with(picture).load(R.drawable.face15).into(picture)
        } else if(photo?.toInt() == 16) {
            Glide.with(picture).load(R.drawable.face16).into(picture)
        } else if(photo?.toInt() == 17) {
            Glide.with(picture).load(R.drawable.face17).into(picture)
        } else if(photo?.toInt() == 18) {
            Glide.with(picture).load(R.drawable.face18).into(picture)
        } else if(photo?.toInt() == 19) {
            Glide.with(picture).load(R.drawable.face19).into(picture)
        } else if(photo?.toInt() == 20) {
            Glide.with(picture).load(R.drawable.face20).into(picture)
        } else if(photo?.toInt() == 21) {
            Glide.with(picture).load(R.drawable.face21).into(picture)
        } else if(photo?.toInt() == 22) {
            Glide.with(picture).load(R.drawable.face22).into(picture)
        } else if(photo?.toInt() == 23) {
            Glide.with(picture).load(R.drawable.face23).into(picture)
        } else if(photo?.toInt() == 24) {
            Glide.with(picture).load(R.drawable.face24).into(picture)
        } else if(photo?.toInt() == 25) {
            Glide.with(picture).load(R.drawable.face25).into(picture)
        } else if(photo?.toInt() == 26) {
            Glide.with(picture).load(R.drawable.crewimage0).into(picture)
        } else {

        }

    }

    fun initRunningRecordList() {
        binding.recordList.layoutManager = GridLayoutManager(requireContext(), 1)

        statisticsRecordAdapter = StatisticsRecordAdapter()
        binding.recordList.adapter = statisticsRecordAdapter

        statisticsRecordAdapter.listener = object: OnStatisticsRecordClickListener {

            override fun onItemClcik(holder: StatisticsRecordAdapter.ViewHolder, view: View?, position: Int) {
                val item = statisticsRecordAdapter.items.get(position)
            }
        }

        showRunRecordList()

    }

    fun showRunRecordList() {
        val databaseR = Firebase.firestore
        databaseR.collection("run_records").whereEqualTo("crewId", AppData.crewId).get().addOnSuccessListener {
                data ->
            for (datum in data) {
                val time = datum.get("date") as Timestamp
                var hour = time.toDate().hours
                var noon:String? = null
                if (hour >= 24) {
                    hour = hour - 24
                }

                if (hour > 12) {

                    noon = "오후 "
                } else if (hour == 12) {
                    noon = "오후 "
                } else {
                    noon = "오전 "
                }
                val timeString = (time.toDate().year+1900).toString() + ". " + (time.toDate().month+1).toString() + ". " + (time.toDate().date).toString() + "."
                val startTime = hour.toString() + " : " +  time.toDate().minutes
                var pace:String? = null
                if (datum.get("velocity") != null) {
                   pace = datum.get("velocity").toString()
                }


                statisticsRecordAdapter.items.add(StatisticsRecord(distance = datum.get("distance").toString(), time = timeString,pop = datum.get("pop").toString(),photo = datum.get("photo").toString(), startTime = startTime, pace = pace))
                statisticsRecordAdapter.items.sortByDescending { it.time }
                statisticsRecordAdapter.notifyDataSetChanged()
            }

        }
    }

}