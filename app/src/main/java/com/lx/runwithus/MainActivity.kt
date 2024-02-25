package com.lx.runwithus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lx.runwithus.AppData.Companion.crewId
import com.lx.runwithus.databinding.ActivityMainBinding
import layout.ItemSpacingDecoration
import com.lx.runwithus.RecommendCrewAdapter as RecommendCrewAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var myCrewAdapter: MyCrewAdapter? = null
    lateinit var recommendCrewAdapter: RecommendCrewAdapter
    var imageCount = 0
    var imageCount2 = 0
    var timer = Handler(Looper.getMainLooper())
    var index = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
        initMyList()
        initRecommendCrewList()
        underLineSpan()
        alarmConfirm()

    }


    fun alarmConfirm() {
        val databaseR = Firebase.firestore
        databaseR.collection("users").document(AppData.id.toString()).get().addOnSuccessListener {
            data ->
            var city = data.get("city").toString()
            var gu = data.get("gu").toString()
            var photo = data.get("photo").toString()
            if(photo?.toInt() == 1) {
                Glide.with(binding.mainMyImage).load(R.drawable.face1).into(binding.mainMyImage)
            } else if(photo?.toInt() == 2) {
                Glide.with(binding.mainMyImage).load(R.drawable.face2).into(binding.mainMyImage)
            } else if(photo?.toInt() == 3) {
                Glide.with(binding.mainMyImage).load(R.drawable.face3).into(binding.mainMyImage)
            } else if(photo?.toInt() == 4) {
                Glide.with(binding.mainMyImage).load(R.drawable.face4).into(binding.mainMyImage)
            } else if(photo?.toInt() == 5) {
                Glide.with(binding.mainMyImage).load(R.drawable.face5).into(binding.mainMyImage)
            } else if(photo?.toInt() == 6) {
                Glide.with(binding.mainMyImage).load(R.drawable.face6).into(binding.mainMyImage)
            } else if(photo?.toInt() == 7) {
                Glide.with(binding.mainMyImage).load(R.drawable.face7).into(binding.mainMyImage)
            } else if(photo?.toInt() == 8) {
                Glide.with(binding.mainMyImage).load(R.drawable.face8).into(binding.mainMyImage)
            } else if(photo?.toInt() == 9) {
                Glide.with(binding.mainMyImage).load(R.drawable.face9).into(binding.mainMyImage)
            } else if(photo?.toInt() == 10) {
                Glide.with(binding.mainMyImage).load(R.drawable.face10).into(binding.mainMyImage)
            } else if(photo?.toInt() == 11) {
                Glide.with(binding.mainMyImage).load(R.drawable.face11).into(binding.mainMyImage)
            } else if(photo?.toInt() == 12) {
                Glide.with(binding.mainMyImage).load(R.drawable.face12).into(binding.mainMyImage)
            } else if(photo?.toInt() == 13) {
                Glide.with(binding.mainMyImage).load(R.drawable.face13).into(binding.mainMyImage)
            } else if(photo?.toInt() == 14) {
                Glide.with(binding.mainMyImage).load(R.drawable.face14).into(binding.mainMyImage)
            } else if(photo?.toInt() == 15) {
                Glide.with(binding.mainMyImage).load(R.drawable.face15).into(binding.mainMyImage)
            } else if(photo?.toInt() == 16) {
                Glide.with(binding.mainMyImage).load(R.drawable.face16).into(binding.mainMyImage)
            } else if(photo?.toInt() == 17) {
                Glide.with(binding.mainMyImage).load(R.drawable.face17).into(binding.mainMyImage)
            } else if(photo?.toInt() == 18) {
                Glide.with(binding.mainMyImage).load(R.drawable.face18).into(binding.mainMyImage)
            } else if(photo?.toInt() == 19) {
                Glide.with(binding.mainMyImage).load(R.drawable.face19).into(binding.mainMyImage)
            } else if(photo?.toInt() == 20) {
                Glide.with(binding.mainMyImage).load(R.drawable.face20).into(binding.mainMyImage)
            } else if(photo?.toInt() == 21) {
                Glide.with(binding.mainMyImage).load(R.drawable.face21).into(binding.mainMyImage)
            } else if(photo?.toInt() == 22) {
                Glide.with(binding.mainMyImage).load(R.drawable.face22).into(binding.mainMyImage)
            } else if(photo?.toInt() == 23) {
                Glide.with(binding.mainMyImage).load(R.drawable.face23).into(binding.mainMyImage)
            } else if(photo?.toInt() == 24) {
                Glide.with(binding.mainMyImage).load(R.drawable.face24).into(binding.mainMyImage)
            } else if(photo?.toInt() == 25) {
                Glide.with(binding.mainMyImage).load(R.drawable.face25).into(binding.mainMyImage)
            } else if(photo?.toInt() == 26) {
                Glide.with(binding.mainMyImage).load(R.drawable.crewimage0).into(binding.mainMyImage)
            } else {

            }
            indexAlarm(city, gu)
        }
    }

    fun indexAlarm(city:String, gu:String) {
        binding.reddot.visibility = View.INVISIBLE
        val databaseR = Firebase.firestore
        databaseR.collection("alarms").whereEqualTo("city", city).whereEqualTo("gu", gu).get().addOnSuccessListener {
            data ->
            for (datum in data) {
                binding.reddot.visibility = View.VISIBLE
            }

        }
    }


    fun setClickListener() {
        binding.mypageButton.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.addCrewButton.setOnClickListener {
            changeCrewItemFragment("addCrew")
        }
        binding.searchCrewButton.setOnClickListener {
            changeCrewItemFragment("searchCrew")
        }
        binding.allCrewListButton.setOnClickListener {
            changeCrewItemFragment("allCrewList")
        }
        binding.alarmButton.setOnClickListener{
            changeCrewItemFragment("alarmList")
        }
    }




    fun initMyList() {
        myCrewAdapter = MyCrewAdapter()
        binding.myCrewList.adapter = myCrewAdapter
        binding.myCrewList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 아이템 간격 설정 (픽셀 단위)
        if (index) {
            val spacingInPixels = 16 // 원하는 간격 크기 설정
            binding.myCrewList.addItemDecoration(ItemSpacingDecoration(spacingInPixels))
        }

        index = false



        // 리사이클러뷰 아이템 클릭 리스너 설정
        myCrewAdapter?.listener = object : OnMyCrewClickListener {
            override fun onItemClcik(
                holder: MyCrewAdapter.ViewHolder?,
                view: View?,
                position: Int
            ) {
                val item = myCrewAdapter!!.items[position]
                // 클릭된 아이템에 대한 처리 추가
                crewId = item.id.toString()
                val intent = Intent(applicationContext, CrewActivity::class.java)
                // 여기에 필요한 데이터를 인텐트에 추가하면 됩니다.
                intent.putExtra("crewName", item.name)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                AppData.where = "MainActivity"
                startActivity(intent)

            }
        }
        getMyCrew()

    }

    fun getMyCrew() {

        imageCount2 = 0

        val databaseR = Firebase.firestore
        databaseR.collection("crews").whereArrayContains("member",AppData.id.toString()).get().addOnSuccessListener {
                documents ->
            for (document in documents) {
                imageCount2++
                //myCrewAdapter.items.add(MyCrew("크루1", "서울특별시", "강남구", 50, "", "", "", "@drawable/greybackground.png"))
                myCrewAdapter?.items?.add(MyCrew(name = document.get("name").toString(), city = document.get("city").toString(), gu = document.get("gu").toString(), id = document.id, crewPhoto = document.get("photo").toString().toInt()))
                myCrewAdapter?.notifyDataSetChanged()
            }
        }.addOnFailureListener{
                e ->
            Log.d("myCrew_failed", "실패", e)
            Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
        }

    }


    fun initRecommendCrewList() {

        imageCount = 0

        recommendCrewAdapter = RecommendCrewAdapter()
        binding.recommendCrewList.adapter = recommendCrewAdapter
        binding.recommendCrewList.layoutManager = GridLayoutManager(this, 2)

        recommendCrewAdapter.listener = object: OnRecommendCrewClickListener {
            override fun onItemClcik(holder: RecommendCrewAdapter.ViewHolder?, view: View?, position: Int) {
                // 각 아이템마다 다른 반응 이끌어내기
                val item = recommendCrewAdapter.items.get(position)
                crewId = item.id.toString()
                val intent = Intent(applicationContext, CrewActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                // 여기에 필요한 데이터를 인텐트에 추가하면 됩니다.
                intent.putExtra("crewName", item.name)
                AppData.where = "MainActivity"
                startActivity(intent)
            }
        }

        recommendCrew()

        // 샘플데이터 추가
        //recommendCrewAdapter.items.add(RecommendCrew("크루1", "서울특별시", "강남구", 50, "", "", "", "@drawable/greybackground.png"))
    }

    fun recommendCrew() {

        val databaseR = Firebase.firestore

        databaseR.collection("users").document(AppData.id.toString()).get().addOnSuccessListener {
                data ->
            var city = data.get("city").toString()
            var gu = data.get("gu").toString()
            var power = data.get("power").toString()
            var cycle = data.get("cycle").toString()


            getRecommendCrew(city,gu,power,cycle)


        }


    }

    fun getRecommendCrew(city:String, gu:String, power:String, cycle:String) {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").whereEqualTo("city", city).whereEqualTo("gu", gu).whereEqualTo("power", power).whereEqualTo("cycle", cycle).limit(6)
            .get().addOnSuccessListener {
                    documents ->
                var count:Long = 6
                for (document in documents) {
                    imageCount++
                    count--
                    var memberList = document.get("member") as ArrayList<String>
                    var memberPop = memberList.size
                    //recommendCrewAdapter.items.add(RecommendCrew("크루1", "서울특별시", "강남구", 50, "", "", "", "@drawable/greybackground.png"))
                    recommendCrewAdapter.items.add(RecommendCrew(name = document.get("name").toString(), city = document.get("city").toString(), gu = document.get("gu").toString(), id = document.id, power = document.get("power").toString(), memberPop = memberPop, cycle = document.get("cycle").toString(), crewPhoto = document.get("photo").toString().toInt()))
                    recommendCrewAdapter.notifyDataSetChanged()
                }
                if (count > 0) {
                    relieveRecommendCrew1(city, gu, power, count, cycle)
                }
            }
    }

    fun relieveRecommendCrew1(city:String, gu:String, power:String, count:Long, cycle:String) {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").whereEqualTo("city", city).whereEqualTo("gu", gu)
            .whereEqualTo("power", power).limit(count)
            .get().addOnSuccessListener { documents ->
                var count2 = count
                for (document in documents) {
                    if (document.get("cycle").toString() != cycle) {

                        imageCount++
                        count2--
                        var memberList = document.get("member") as ArrayList<String>
                        var memberPop = memberList.size
                        recommendCrewAdapter.items.add(
                            RecommendCrew(
                                name = document.get("name").toString(),
                                city = document.get("city").toString(),
                                gu = document.get("gu").toString(),
                                id = document.id,
                                power = document.get("power").toString(),
                                cycle = document.get("cycle").toString(),
                                memberPop = memberPop,
                                crewPhoto = document.get("photo").toString().toInt()
                            )
                        )

                        recommendCrewAdapter.notifyDataSetChanged()
                    }
                }
                if (count2 > 0) {
                    relieveRecommendCrew2(city, gu, count2, power, cycle)
                }
            }
    }

    fun relieveRecommendCrew2(city:String, gu:String, count2:Long, power:String, cycle: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").whereEqualTo("city", city).whereEqualTo("gu", gu)
            .limit(count2)
            .get().addOnSuccessListener { documents ->
                var count3 = count2
                for (document in documents) {
                    if (document.get("cycle").toString() != cycle && document.get("power").toString() != power) {

                        imageCount++
                        count3--
                        var memberList = document.get("member") as ArrayList<String>
                        var memberPop = memberList.size
                        recommendCrewAdapter.items.add(
                            RecommendCrew(
                                name = document.get("name").toString(),
                                city = document.get("city").toString(),
                                gu = document.get("gu").toString(),
                                id = document.id,
                                power = document.get("power").toString(),
                                cycle = document.get("cycle").toString(),
                                memberPop = memberPop,
                                crewPhoto = document.get("photo").toString().toInt()
                            )
                        )
                        recommendCrewAdapter.notifyDataSetChanged()
                    }
                }
                if (count3 > 0) {
                    relieveRecommendCrew3(city, count3, gu, power, cycle)
                }
            }
    }

    fun relieveRecommendCrew3(city:String, count3:Long, gu:String, power:String, cycle: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").whereEqualTo("city", city).limit(count3)
            .get().addOnSuccessListener { documents ->
                var count4 = count3
                for (document in documents) {
                    if (document.get("cycle").toString() != cycle && document.get("power")
                            .toString() != power
                        && document.get("gu").toString() != gu
                    ) {
                        imageCount++
                        count4--
                        var memberList = document.get("member") as ArrayList<String>
                        var memberPop = memberList.size
                        recommendCrewAdapter.items.add(
                            RecommendCrew(
                                name = document.get("name").toString(),
                                city = document.get("city").toString(),
                                gu = document.get("gu").toString(),
                                id = document.id,
                                power = document.get("power").toString(),
                                cycle = document.get("cycle").toString(),
                                memberPop = memberPop,
                                crewPhoto = document.get("photo").toString().toInt()
                            )
                        )
                        recommendCrewAdapter.notifyDataSetChanged()
                    }
                }
                if (count4 > 0) {
                    relieveRecommendCrew4(count4, city, gu, power, cycle)
                }
            }
    }

    fun relieveRecommendCrew4(count4:Long, city:String, gu:String, power:String, cycle: String) {
        val databaseR = Firebase.firestore
        databaseR.collection("crews").limit(count4)
            .get().addOnSuccessListener { documents ->
                for (document in documents) {
                    imageCount++
                    if (document.get("cycle").toString() != cycle &&
                        document.get("power").toString() != power &&
                        document.get("gu").toString() != gu &&
                        document.get("city").toString() != city
                    ) {
                        var memberList = document.get("member") as ArrayList<String>
                        var memberPop = memberList.size
                        recommendCrewAdapter.items.add(
                            RecommendCrew(
                                name = document.get("name").toString(),
                                city = document.get("city").toString(),
                                gu = document.get("gu").toString(),
                                id = document.id,
                                power = document.get("power").toString(),
                                cycle = document.get("cycle").toString(),
                                memberPop = memberPop,
                                crewPhoto = document.get("photo").toString().toInt()
                            )
                        )
                        recommendCrewAdapter.notifyDataSetChanged()
                    }
                }
            }
    }



    fun changeCrewItemFragment(message: String) {
        val intent = Intent(this, CrewItemActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("BUTTON_TYPE", message)
        startActivity(intent)
    }

    fun underLineSpan() {
        val content1 = SpannableString(binding.textView5.text)
        content1.setSpan(UnderlineSpan(), 0, content1.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.textView5.text = content1

        val content2 = SpannableString(binding.textView6.text)
        content2.setSpan(UnderlineSpan(), 0, content2.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.textView6.text = content2
    }

    override fun onBackPressed() {

    }


}