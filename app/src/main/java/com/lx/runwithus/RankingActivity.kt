package com.lx.runwithus

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lx.runwithus.api.BasicClient.Companion.userId
import com.lx.runwithus.databinding.ActivityRankingBinding

class RankingActivity : AppCompatActivity() {

    var memberDialog: AlertDialog? = null

    val databaseR =  Firebase.firestore
    lateinit var binding: ActivityRankingBinding

    lateinit var rankAdapter: RankAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackButton.setOnClickListener {
            var intent = Intent(this, CrewActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        initRank3()
        initMyRank()
        initRankList()

    }

    fun initRankList() {

        rankAdapter = RankAdapter()
        binding.allRankingList.adapter = rankAdapter

        binding.allRankingList.layoutManager = LinearLayoutManager(this)

        rankAdapter.listener = object : OnRankCrewClickListener {
            override fun onItemClcik(holder: RankAdapter.ViewHolder?, view: View?, position: Int) {
                val item = rankAdapter.items.get(position)
                memberPopup(item.name.toString(), item.rank.toString(), item.grade.toString())
            }
        }

        getRankList()

    }

    fun memberPopup(name:String, rank:String, grade:String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.member_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        if (memberDialog == null) {
            memberDialog = mBuilder.show()
            memberDialog?.setCanceledOnTouchOutside(false)
            memberDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val dialogName = mDialogView.findViewById<TextView>(R.id.memberNameDialog)

                val databaseR = Firebase.firestore
                databaseR.collection("users").document(name).get().addOnSuccessListener {
                        data ->
                    dialogName.text = "이름 : " + data.get("nickname").toString()
                    var photo = data.get("photo").toString()
                    val dialogImage = mDialogView.findViewById<AppCompatImageView>(R.id.memberImageDialog)
                    if(photo?.toInt() == 1) {
                        Glide.with(dialogImage).load(R.drawable.face1).into(dialogImage)
                    } else if(photo?.toInt() == 2) {
                        Glide.with(dialogImage).load(R.drawable.face2).into(dialogImage)
                    } else if(photo?.toInt() == 3) {
                        Glide.with(dialogImage).load(R.drawable.face3).into(dialogImage)
                    } else if(photo?.toInt() == 4) {
                        Glide.with(dialogImage).load(R.drawable.face4).into(dialogImage)
                    } else if(photo?.toInt() == 5) {
                        Glide.with(dialogImage).load(R.drawable.face5).into(dialogImage)
                    } else if(photo?.toInt() == 6) {
                        Glide.with(dialogImage).load(R.drawable.face6).into(dialogImage)
                    } else if(photo?.toInt() == 7) {
                        Glide.with(dialogImage).load(R.drawable.face7).into(dialogImage)
                    } else if(photo?.toInt() == 8) {
                        Glide.with(dialogImage).load(R.drawable.face8).into(dialogImage)
                    } else if(photo?.toInt() == 9) {
                        Glide.with(dialogImage).load(R.drawable.face9).into(dialogImage)
                    } else if(photo?.toInt() == 10) {
                        Glide.with(dialogImage).load(R.drawable.face10).into(dialogImage)
                    } else if(photo?.toInt() == 11) {
                        Glide.with(dialogImage).load(R.drawable.face11).into(dialogImage)
                    } else if(photo?.toInt() == 12) {
                        Glide.with(dialogImage).load(R.drawable.face12).into(dialogImage)
                    } else if(photo?.toInt() == 13) {
                        Glide.with(dialogImage).load(R.drawable.face13).into(dialogImage)
                    } else if(photo?.toInt() == 14) {
                        Glide.with(dialogImage).load(R.drawable.face14).into(dialogImage)
                    } else if(photo?.toInt() == 15) {
                        Glide.with(dialogImage).load(R.drawable.face15).into(dialogImage)
                    } else if(photo?.toInt() == 16) {
                        Glide.with(dialogImage).load(R.drawable.face16).into(dialogImage)
                    } else if(photo?.toInt() == 17) {
                        Glide.with(dialogImage).load(R.drawable.face17).into(dialogImage)
                    } else if(photo?.toInt() == 18) {
                        Glide.with(dialogImage).load(R.drawable.face18).into(dialogImage)
                    } else if(photo?.toInt() == 19) {
                        Glide.with(dialogImage).load(R.drawable.face19).into(dialogImage)
                    } else if(photo?.toInt() == 20) {
                        Glide.with(dialogImage).load(R.drawable.face20).into(dialogImage)
                    } else if(photo?.toInt() == 21) {
                        Glide.with(dialogImage).load(R.drawable.face21).into(dialogImage)
                    } else if(photo?.toInt() == 22) {
                        Glide.with(dialogImage).load(R.drawable.face22).into(dialogImage)
                    } else if(photo?.toInt() == 23) {
                        Glide.with(dialogImage).load(R.drawable.face23).into(dialogImage)
                    } else if(photo?.toInt() == 24) {
                        Glide.with(dialogImage).load(R.drawable.face24).into(dialogImage)
                    } else if(photo?.toInt() == 25) {
                        Glide.with(dialogImage).load(R.drawable.face25).into(dialogImage)
                    } else if(photo?.toInt() == 26) {
                        Glide.with(dialogImage).load(R.drawable.crewimage0).into(dialogImage)
                    } else {

                    }
                }




            val dialogGrade = mDialogView.findViewById<TextView>(R.id.memberGradeDialog)
            dialogGrade.text = "현재 등급 : " + grade


            val okButton = mDialogView.findViewById<Button>(R.id.changeGradeButton)
            okButton.setOnClickListener {
                memberDialog?.dismiss()
                memberDialog = null


            }

            val noButton = mDialogView.findViewById<Button>(R.id.cancelMemberDialogButton)
            noButton.setOnClickListener {
                memberDialog?.dismiss()
                memberDialog = null

            }
        }


    }

    fun getRankList() {

        databaseR.collection("records").orderBy("record", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { data ->
                var ranking = 1
                for (datum in data) {
                    if (datum.get("crewId").toString() == AppData.crewId) {
                        rankAdapter.items.add(
                            RankCrew(
                                datum.get("userId").toString(),
                                "",
                                ranking,
                                "",
                                datum.get("record").toString().toDouble(),
                                datum.get("grade").toString()
                            )
                        )
                        ranking++
                        rankAdapter.notifyDataSetChanged()
                    }


                }
            }
    }

    fun initRank3() {

        databaseR.collection("records").orderBy("record", Query.Direction.DESCENDING).get().addOnSuccessListener {
                data ->
            var rank = 1

            for (datum in data) {

                if (datum.get("crewId").toString() == AppData.crewId) {

                    if (rank == 1) {
                        binding.firstRankerDistance?.text = datum.get("record").toString() + "km"

                    } else if (rank == 2) {
                        binding.secondRankerDistance?.text = datum.get("record").toString() + "km"
                    } else if (rank == 3) {
                        binding.thirdRankerDistance?.text = datum.get("record").toString() + "km"
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
                binding.firstRankerName?.text = data.get("nickname").toString()
            } else if(rank == 2) {
                binding.secondRankerName?.text = data.get("nickname").toString()
            } else if (rank == 3) {
                binding.thirdRankerName?.text = data.get("nickname").toString()
            }

            var photo = data.get("photo").toString()
            changePicture(rank, photo)
        }

    }

    fun changePicture(rank:Int, photo:String) {

        var picture = binding.thirdRankerImage
        if (rank == 1) {
            picture = binding.firstRankerImage
        } else if(rank == 2) {
            picture = binding.secondRankerImage
        } else if(rank == 3) {
            picture = binding.thirdRankerImage
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

    fun initMyRank() {

        databaseR.collection("records").orderBy("record", Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                    data ->

                var ranking = 1


                for (datum in data) {
                    if (datum.get("crewId").toString() == AppData.crewId) {
                        if (datum.get("userId").toString() == AppData.id) {
                            binding.myDistance.text = datum.get("record").toString() + " km"
                            binding.myRank.text = ranking.toString()

                            findName2(datum.get("userId").toString())
                        }
                        ranking++
                    }


                }


            }
    }



    fun findName2(userId: String) {

        databaseR.collection("users").document(userId).get().addOnSuccessListener {
                data ->
            binding.myName.text = data.get("nickname").toString()
            var photo = data.get("photo").toString()

            if (photo?.toInt() == 1) {
                Glide.with(binding.myImage).load(R.drawable.face1).into(binding.myImage)
            } else if(photo?.toInt() == 2) {
                Glide.with(binding.myImage).load(R.drawable.face2).into(binding.myImage)
            } else if(photo?.toInt() == 3) {
                Glide.with(binding.myImage).load(R.drawable.face3).into(binding.myImage)
            } else if(photo?.toInt() == 4) {
                Glide.with(binding.myImage).load(R.drawable.face4).into(binding.myImage)
            } else if(photo?.toInt() == 5) {
                Glide.with(binding.myImage).load(R.drawable.face5).into(binding.myImage)
            } else if(photo?.toInt() == 6) {
                Glide.with(binding.myImage).load(R.drawable.face6).into(binding.myImage)
            } else if(photo?.toInt() == 7) {
                Glide.with(binding.myImage).load(R.drawable.face7).into(binding.myImage)
            } else if(photo?.toInt() == 8) {
                Glide.with(binding.myImage).load(R.drawable.face8).into(binding.myImage)
            } else if(photo?.toInt() == 9) {
                Glide.with(binding.myImage).load(R.drawable.face9).into(binding.myImage)
            } else if(photo?.toInt() == 10) {
                Glide.with(binding.myImage).load(R.drawable.face10).into(binding.myImage)
            } else if(photo?.toInt() == 11) {
                Glide.with(binding.myImage).load(R.drawable.face11).into(binding.myImage)
            } else if(photo?.toInt() == 12) {
                Glide.with(binding.myImage).load(R.drawable.face12).into(binding.myImage)
            } else if(photo?.toInt() == 13) {
                Glide.with(binding.myImage).load(R.drawable.face13).into(binding.myImage)
            } else if(photo?.toInt() == 14) {
                Glide.with(binding.myImage).load(R.drawable.face14).into(binding.myImage)
            } else if(photo?.toInt() == 15) {
                Glide.with(binding.myImage).load(R.drawable.face15).into(binding.myImage)
            } else if(photo?.toInt() == 16) {
                Glide.with(binding.myImage).load(R.drawable.face16).into(binding.myImage)
            } else if(photo?.toInt() == 17) {
                Glide.with(binding.myImage).load(R.drawable.face17).into(binding.myImage)
            } else if(photo?.toInt() == 18) {
                Glide.with(binding.myImage).load(R.drawable.face18).into(binding.myImage)
            } else if(photo?.toInt() == 19) {
                Glide.with(binding.myImage).load(R.drawable.face19).into(binding.myImage)
            } else if(photo?.toInt() == 20) {
                Glide.with(binding.myImage).load(R.drawable.face20).into(binding.myImage)
            } else if(photo?.toInt() == 21) {
                Glide.with(binding.myImage).load(R.drawable.face21).into(binding.myImage)
            } else if(photo?.toInt() == 22) {
                Glide.with(binding.myImage).load(R.drawable.face22).into(binding.myImage)
            } else if(photo?.toInt() == 23) {
                Glide.with(binding.myImage).load(R.drawable.face23).into(binding.myImage)
            } else if(photo?.toInt() == 24) {
                Glide.with(binding.myImage).load(R.drawable.face24).into(binding.myImage)
            } else if(photo?.toInt() == 25) {
                Glide.with(binding.myImage).load(R.drawable.face25).into(binding.myImage)
            } else if(photo?.toInt() == 26) {
                Glide.with(binding.myImage).load(R.drawable.crewimage0).into(binding.myImage)
            } else {

            }
        }

    }

}